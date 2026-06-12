import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmptyStateComponent } from '../../../shared/components/empty-state/empty-state.component';
import { ServicesService } from '../../../core/services/services.service';
import { Service, Category, MOCK_CATEGORIES } from '../../../shared/models/models';
import { MoneyPipe } from '../../../shared/pipes/money.pipe';
import { DurationPipe } from '../../../shared/pipes/duration.pipe';

@Component({
  selector: 'be-admin-services',
  standalone: true,
  imports: [CommonModule, FormsModule, EmptyStateComponent, MoneyPipe, DurationPipe],
  template: `
    <div class="page">
      <!-- Header -->
      <div class="header">
        <h1 class="header__title">Services</h1>
        <button class="header__add" (click)="openForm()">+ Add service</button>
      </div>

      <div class="layout">
        <!-- Table -->
        <div class="card table-card">
          <table class="table" *ngIf="services.length > 0; else empty">
            <thead>
              <tr><th>Service</th><th>Category</th><th>Duration</th><th>Price</th><th>Rating</th><th>Status</th><th>Actions</th></tr>
            </thead>
            <tbody>
              <tr *ngFor="let s of services">
                <td>
                  <div class="svc-cell">
                    <div class="svc-thumb">💪</div>
                    <div>
                      <p class="svc-name">{{ s.name }}</p>
                      <p class="svc-coach">{{ s.coachName }}</p>
                    </div>
                  </div>
                </td>
                <td><span class="cat-badge">{{ s.categoryName }}</span></td>
                <td>{{ s.durationMin | duration }}</td>
                <td class="td-price">{{ s.priceCents | money }}</td>
                <td>{{ s.rating ? '⭐ ' + s.rating : '—' }}</td>
                <td>
                  <span class="status-dot" [class.status-dot--active]="s.active">
                    {{ s.active ? 'Active' : 'Inactive' }}
                  </span>
                </td>
                <td>
                  <div class="row-actions">
                    <button class="ra ra--edit" (click)="editService(s)">Edit</button>
                    <button class="ra ra--del" (click)="deleteService(s)">Remove</button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
          <ng-template #empty>
            <be-empty-state icon="🏋️" title="No services yet" message="Add your first service using the button above." />
          </ng-template>
        </div>

        <!-- Form panel -->
        <div class="card form-card" *ngIf="showForm">
          <h3 class="form-title">{{ editingId ? 'Edit service' : 'Add service' }}</h3>

          <div class="field">
            <label class="field__label">Name *</label>
            <input class="field__input" [(ngModel)]="form.name" placeholder="e.g. Power Strength" />
          </div>
          <div class="field">
            <label class="field__label">Description</label>
            <textarea class="field__textarea" [(ngModel)]="form.description" rows="3"></textarea>
          </div>
          <div class="fields-2">
            <div class="field">
              <label class="field__label">Price (cents) *</label>
              <input class="field__input" type="number" [(ngModel)]="form.priceCents" placeholder="6500" />
            </div>
            <div class="field">
              <label class="field__label">Duration (min) *</label>
              <input class="field__input" type="number" [(ngModel)]="form.durationMin" placeholder="60" />
            </div>
          </div>
          <div class="field">
            <label class="field__label">Category</label>
            <select class="field__select" [(ngModel)]="form.categoryId">
              <option value="">Select…</option>
              <option *ngFor="let c of categories" [value]="c.id">{{ c.name }}</option>
            </select>
          </div>
          <div class="field">
            <label class="field__label">Coach name</label>
            <input class="field__input" [(ngModel)]="form.coachName" placeholder="Marcus Reid" />
          </div>
          <div class="field">
            <label class="field__label">Image URL</label>
            <input class="field__input" [(ngModel)]="form.imageUrl" placeholder="https://…" />
          </div>

          <div class="form-actions">
            <button class="btn-cancel" (click)="showForm = false">Cancel</button>
            <button class="btn-save" [disabled]="saving" (click)="save()">
              {{ saving ? 'Saving…' : (editingId ? 'Update' : 'Add service') }}
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Confirm delete dialog -->
    <div class="overlay" *ngIf="confirmDeleteId" (click)="confirmDeleteId = null">
      <div class="dialog" (click)="$event.stopPropagation()">
        <h3 class="dialog__title">Remove service?</h3>
        <p class="dialog__desc">This will deactivate the service and hide it from bookings.</p>
        <div class="dialog__actions">
          <button class="btn-cancel" (click)="confirmDeleteId = null">Cancel</button>
          <button class="btn-danger" (click)="confirmDelete()">Remove</button>
        </div>
      </div>
    </div>
  `,
  styles: [`
    @use 'tokens' as *;
    .page { padding: 32px; }
    .header { display: flex; align-items: center; justify-content: space-between; margin-bottom: 24px; }
    .header__title { font-size: 26px; font-weight: 700; color: $ink-800; }
    .header__add {
      background: $primary; color: #fff; border: none; cursor: pointer;
      padding: 10px 18px; border-radius: $radius-md; font-size: 14px; font-weight: 700;
      box-shadow: $shadow-btn-sm; &:hover { background: $primary-700; }
    }

    .layout { display: grid; grid-template-columns: 1fr 340px; gap: 20px; align-items: start; }
    .card { background: $surface-0; border: 1px solid $border-100; border-radius: $radius-xl; overflow: hidden; }
    .table-card { min-height: 200px; }
    .table { width: 100%; border-collapse: collapse; }
    th { text-align: left; font-size: 12px; font-weight: 700; color: $muted-500; text-transform: uppercase; letter-spacing: .06em; padding: 14px 16px; border-bottom: 1px solid $border-200; }
    td { padding: 13px 16px; font-size: 13.5px; color: $ink-700; border-bottom: 1px solid $border-100; vertical-align: middle; }
    tr:last-child td { border-bottom: none; }
    tr:hover td { background: $surface-50; }
    .svc-cell { display: flex; align-items: center; gap: 12px; }
    .svc-thumb { width: 40px; height: 40px; background: $primary-soft; border-radius: $radius-lg; display: flex; align-items: center; justify-content: center; font-size: 18px; flex-shrink: 0; }
    .svc-name { font-weight: 700; font-size: 14px; color: $ink-800; }
    .svc-coach { font-size: 12.5px; color: $muted-500; margin-top: 2px; }
    .cat-badge { background: $primary-soft; color: $primary; font-size: 12px; font-weight: 700; padding: 3px 10px; border-radius: $radius-pill; }
    .td-price { font-family: $font-display; font-weight: 700; }
    .status-dot { font-size: 12.5px; font-weight: 700; color: $muted-500; }
    .status-dot--active { color: $success-text; }
    .row-actions { display: flex; gap: 6px; }
    .ra { padding: 5px 10px; border-radius: $radius-sm; border: none; cursor: pointer; font-size: 12px; font-weight: 700; }
    .ra--edit { background: $primary-soft; color: $primary; &:hover { background: $primary; color: #fff; } }
    .ra--del  { background: $danger-bg;    color: $danger;  &:hover { background: $danger;  color: #fff; } }
    .form-card { padding: 24px; position: sticky; top: 24px; }
    .form-title { font-size: 17px; font-weight: 800; color: $ink-800; margin-bottom: 20px; }
    .field { margin-bottom: 14px; }
    .fields-2 { display: grid; grid-template-columns: 1fr 1fr; gap: 12px; }
    .field__label { display: block; font-size: 12.5px; font-weight: 700; color: $ink-700; margin-bottom: 5px; }
    .field__input, .field__textarea, .field__select {
      width: 100%; border: 1px solid $border-200; border-radius: $radius-md; padding: 9px 12px;
      font-family: $font-ui; font-size: 14px; color: $ink-700; outline: none; background: #fff; resize: vertical;
      &:focus { border-color: $primary; box-shadow: 0 0 0 3px rgba(91,75,227,.1); }
    }
    .form-actions { display: flex; gap: 10px; margin-top: 20px; }
    .btn-cancel { flex: 1; padding: 10px; border: 1px solid $border-200; border-radius: $radius-md; background: #fff; cursor: pointer; font-size: 14px; font-weight: 600; color: $ink-700; &:hover { background: $surface-200; } }
    .btn-save { flex: 2; padding: 10px; border: none; border-radius: $radius-md; background: $primary; color: #fff; cursor: pointer; font-size: 14px; font-weight: 700; box-shadow: $shadow-btn-sm; &:hover:not(:disabled) { background: $primary-700; } &:disabled { opacity: .6; cursor: not-allowed; } }

    .overlay { position: fixed; inset: 0; background: rgba(0,0,0,.4); display: flex; align-items: center; justify-content: center; z-index: 999; }
    .dialog { background: #fff; border-radius: $radius-xl; padding: 28px; max-width: 360px; width: 90%; }
    .dialog__title { font-size: 18px; font-weight: 800; color: $ink-800; margin-bottom: 10px; }
    .dialog__desc { font-size: 14.5px; color: $text-600; margin-bottom: 24px; }
    .dialog__actions { display: flex; gap: 10px; }
    .btn-danger { flex: 1; padding: 11px; border: none; border-radius: $radius-md; background: $danger; color: #fff; cursor: pointer; font-size: 14px; font-weight: 700; &:hover { background: $danger-text; } }
  `],
})
export class AdminServicesComponent implements OnInit {
  services: Service[] = [];
  categories: Category[] = MOCK_CATEGORIES;
  showForm = false;
  editingId: string | null = null;
  saving = false;
  confirmDeleteId: string | null = null;

  form: Partial<Service> = this.emptyForm();

  private svc = inject(ServicesService);

  ngOnInit() {
    this.svc.getServices().subscribe(s => this.services = s);
  }

  emptyForm(): Partial<Service> {
    return { name: '', description: '', priceCents: 0, durationMin: 60, categoryId: '', coachName: '', imageUrl: '' };
  }

  openForm() { this.editingId = null; this.form = this.emptyForm(); this.showForm = true; }

  editService(s: Service) {
    this.editingId = s.id;
    this.form = { ...s };
    this.showForm = true;
  }

  deleteService(s: Service) { this.confirmDeleteId = s.id; }

  confirmDelete() {
    if (!this.confirmDeleteId) return;
    this.svc.deleteService(this.confirmDeleteId).subscribe(() => {
      this.services = this.services.filter(s => s.id !== this.confirmDeleteId);
      this.confirmDeleteId = null;
    });
  }

  save() {
    if (!this.form.name || !this.form.priceCents || !this.form.durationMin) return;
    this.saving = true;
    const catName = this.categories.find(c => c.id === this.form.categoryId)?.name;
    const data = { ...this.form, categoryName: catName };

    const obs = this.editingId
      ? this.svc.updateService(this.editingId, data)
      : this.svc.createService(data);

    obs.subscribe(s => {
      if (this.editingId) {
        const idx = this.services.findIndex(x => x.id === this.editingId);
        if (idx !== -1) this.services[idx] = s;
      } else {
        this.services.unshift(s);
      }
      this.saving = false;
      this.showForm = false;
    });
  }
}

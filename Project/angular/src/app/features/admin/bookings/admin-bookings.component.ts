import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { StatusBadgeComponent } from '../../../shared/components/status-badge/status-badge.component';
import { EmptyStateComponent } from '../../../shared/components/empty-state/empty-state.component';
import { BookingsService } from '../../../core/services/bookings.service';
import { Booking, BookingStatus } from '../../../shared/models/models';
import { MoneyPipe } from '../../../shared/pipes/money.pipe';

@Component({
  selector: 'be-admin-bookings',
  standalone: true,
  imports: [CommonModule, FormsModule, StatusBadgeComponent, EmptyStateComponent, MoneyPipe],
  template: `
    <div class="page">
      <!-- Header -->
      <div class="header">
        <h1 class="header__title">Bookings</h1>
        <div class="header__controls">
          <input class="search" [(ngModel)]="search" (ngModelChange)="filter()" placeholder="Search ref or customer…" />
          <select class="select" [(ngModel)]="statusFilter" (ngModelChange)="filter()">
            <option value="">All statuses</option>
            <option value="pending">Pending</option>
            <option value="confirmed">Confirmed</option>
            <option value="completed">Completed</option>
            <option value="cancelled">Cancelled</option>
          </select>
        </div>
      </div>

      <!-- Status tabs -->
      <div class="tabs">
        <button *ngFor="let tab of tabs" class="tab" [class.tab--active]="statusFilter === tab.value"
          (click)="statusFilter = tab.value; filter()">
          {{ tab.label }}
          <span class="tab__count">{{ tabCount(tab.value) }}</span>
        </button>
      </div>

      <!-- Table -->
      <div class="card" *ngIf="filtered.length > 0; else empty">
        <table class="table">
          <thead>
            <tr>
              <th>Ref</th><th>Customer</th><th>Service</th><th>Coach</th>
              <th>Date & time</th><th>Amount</th><th>Status</th><th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let b of filtered">
              <td class="td-ref">{{ b.ref }}</td>
              <td>
                <div class="td-customer">
                  <span class="td-customer__name">{{ b.fullName }}</span>
                  <span class="td-customer__phone">{{ b.phone }}</span>
                </div>
              </td>
              <td>{{ b.serviceName }}</td>
              <td>{{ b.coachName }}</td>
              <td>{{ b.startsAt | date:'MMM d · h:mm a' }}</td>
              <td class="td-price">{{ b.priceCents | money }}</td>
              <td><be-status-badge [status]="b.status" /></td>
              <td>
                <div class="actions">
                  <button class="action action--confirm" *ngIf="b.status === 'pending'"
                    (click)="updateStatus(b, 'confirmed')">Confirm</button>
                  <button class="action action--cancel" *ngIf="b.status !== 'cancelled' && b.status !== 'completed'"
                    (click)="updateStatus(b, 'cancelled')">Cancel</button>
                  <button class="action action--complete" *ngIf="b.status === 'confirmed'"
                    (click)="updateStatus(b, 'completed')">Complete</button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
        <div class="pagination">
          Showing {{ filtered.length }} of {{ all.length }} bookings
        </div>
      </div>

      <ng-template #empty>
        <be-empty-state icon="📅" title="No bookings found" message="Try adjusting your search or status filter." />
      </ng-template>
    </div>
  `,
  styles: [`
    @use 'tokens' as *;
    .page { padding: 32px; }
    .header { display: flex; align-items: center; justify-content: space-between; margin-bottom: 24px; }
    .header__title { font-size: 26px; font-weight: 700; color: $ink-800; }
    .header__controls { display: flex; gap: 12px; }

    .search, .select {
      border: 1px solid $border-200; border-radius: $radius-md; padding: 9px 14px;
      font-family: $font-ui; font-size: 14px; color: $ink-700; outline: none; background: #fff;
      &:focus { border-color: $primary; }
    }
    .search { width: 240px; }

    .tabs { display: flex; gap: 4px; margin-bottom: 20px; }
    .tab {
      display: flex; align-items: center; gap: 6px; padding: 8px 16px; border-radius: $radius-lg;
      border: none; background: transparent; cursor: pointer; font-size: 14px; font-weight: 600; color: $muted-500;
      &:hover { background: $surface-200; }
    }
    .tab--active { background: $primary-soft; color: $primary; }
    .tab__count { background: $surface-200; color: $muted-500; font-size: 11px; font-weight: 700; padding: 1px 6px; border-radius: $radius-pill; }
    .tab--active .tab__count { background: $primary; color: #fff; }

    .card { background: $surface-0; border: 1px solid $border-100; border-radius: $radius-xl; overflow: hidden; }
    .table { width: 100%; border-collapse: collapse; }
    th { text-align: left; font-size: 12px; font-weight: 700; color: $muted-500; text-transform: uppercase; letter-spacing: .06em; padding: 14px 16px; border-bottom: 1px solid $border-200; }
    td { padding: 13px 16px; font-size: 13.5px; color: $ink-700; border-bottom: 1px solid $border-100; }
    tr:last-child td { border-bottom: none; }
    tr:hover td { background: $surface-50; }
    .td-ref { font-family: $font-display; font-weight: 700; color: $primary; }
    .td-price { font-family: $font-display; font-weight: 700; }
    .td-customer { display: flex; flex-direction: column; gap: 1px; }
    .td-customer__name { font-weight: 700; }
    .td-customer__phone { font-size: 12px; color: $muted-500; }

    .actions { display: flex; gap: 6px; }
    .action {
      padding: 5px 10px; border-radius: $radius-sm; border: none; cursor: pointer;
      font-size: 12px; font-weight: 700; transition: all $dur-fast;
    }
    .action--confirm  { background: $success-bg; color: $success-text; &:hover { background: $success; color: #fff; } }
    .action--cancel   { background: $danger-bg;  color: $danger-text;  &:hover { background: $danger;  color: #fff; } }
    .action--complete { background: $info-bg;    color: $info-text;    &:hover { background: $info;    color: #fff; } }

    .pagination { padding: 14px 16px; font-size: 13px; color: $muted-500; border-top: 1px solid $border-100; }
  `],
})
export class AdminBookingsComponent implements OnInit {
  all: Booking[] = [];
  filtered: Booking[] = [];
  search = '';
  statusFilter = '';

  tabs = [
    { label: 'All',       value: '' },
    { label: 'Pending',   value: 'pending' },
    { label: 'Confirmed', value: 'confirmed' },
    { label: 'Completed', value: 'completed' },
    { label: 'Cancelled', value: 'cancelled' },
  ];

  private svc = inject(BookingsService);

  ngOnInit() { this.svc.getBookings().subscribe(b => { this.all = b; this.filtered = b; }); }

  filter() {
    const q = this.search.toLowerCase();
    this.filtered = this.all.filter(b => {
      const matchSearch = !q || b.ref.toLowerCase().includes(q) || (b.fullName ?? '').toLowerCase().includes(q);
      const matchStatus = !this.statusFilter || b.status === this.statusFilter;
      return matchSearch && matchStatus;
    });
  }

  updateStatus(booking: Booking, status: BookingStatus) {
    this.svc.updateStatus(booking.id, status).subscribe(updated => {
      const idx = this.all.findIndex(b => b.id === updated.id);
      if (idx !== -1) this.all[idx] = updated;
      this.filter();
    });
  }

  tabCount(status: string): number {
    return status ? this.all.filter(b => b.status === status).length : this.all.length;
  }
}

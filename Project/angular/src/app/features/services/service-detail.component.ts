import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { NavbarComponent } from '../../shared/components/navbar/navbar.component';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { ServicesService } from '../../core/services/services.service';
import { Service, TimeSlot } from '../../shared/models/models';
import { MoneyPipe } from '../../shared/pipes/money.pipe';
import { DurationPipe } from '../../shared/pipes/duration.pipe';
import { APP_STRINGS } from '../../core/constants';

@Component({
  selector: 'be-service-detail',
  standalone: true,
  imports: [CommonModule, RouterModule, NavbarComponent, ButtonComponent, MoneyPipe, DurationPipe],
  template: `
    <be-navbar />

    <div class="page" *ngIf="service; else loading">
      <div class="container">
        <button class="back" (click)="goBack()">{{ appStrings.SERVICES.BACK_TO_SERVICES }}</button>

        <div class="layout">
          <!-- Left: details -->
          <div class="details">
            <div class="thumb">
              <div class="thumb__placeholder">💪</div>
              <span class="thumb__cat">{{ service.categoryName }}</span>
            </div>

            <div class="meta-row">
              <span class="meta-item">⏱ {{ service.durationMin | duration }}</span>
              <span class="meta-item">⭐ {{ service.rating }}</span>
              <span class="meta-item">👤 {{ service.coachName }}</span>
            </div>

            <h1 class="title">{{ service.name }}</h1>
            <p class="desc">{{ service.description }}</p>

            <div class="included" *ngIf="service.included?.length">
              <h3 class="included__title">{{ appStrings.SERVICES.WHATS_INCLUDED }}</h3>
              <ul class="included__list">
                <li *ngFor="let item of service.included" class="included__item">
                  <span class="included__check">✓</span> {{ item }}
                </li>
              </ul>
            </div>
          </div>

          <!-- Right: booking panel -->
          <div class="panel card">
            <h3 class="panel__title">{{ appStrings.SERVICES.BOOK_SESSION_PANEL_TITLE }}</h3>
            <div class="panel__price">{{ service.priceCents | money }}<span class="panel__per"> / session</span></div>
            <p class="panel__dur">⏱ {{ service.durationMin | duration }}</p>

            <!-- Date picker -->
            <div class="panel__section">
              <label class="panel__label">{{ appStrings.SERVICES.CHOOSE_DATE_LABEL }}</label>
              <div class="date-pills">
                <button *ngFor="let d of datePills; let i = index"
                  class="date-pill" [class.date-pill--active]="selectedDateIdx === i"
                  (click)="selectedDateIdx = i; loadSlots()">
                  <span class="date-pill__day">{{ d.weekday }}</span>
                  <span class="date-pill__num">{{ d.day }}</span>
                </button>
              </div>
            </div>

            <!-- Time slots -->
            <div class="panel__section">
              <label class="panel__label">{{ appStrings.SERVICES.SELECT_TIME_LABEL }}</label>
              <div class="slots" *ngIf="slots.length > 0; else noSlots">
                <button *ngFor="let slot of slots"
                  class="slot" [class.slot--active]="selectedSlot?.id === slot.id"
                  [class.slot--taken]="slot.isBooked" [disabled]="slot.isBooked"
                  (click)="selectedSlot = slot">
                  {{ slot.startsAt | date:'h:mm a' }}
                </button>
              </div>
              <ng-template #noSlots><p class="no-slots">{{ appStrings.SERVICES.NO_SLOTS }}</p></ng-template>
            </div>

            <button class="book-btn" [disabled]="!selectedSlot" (click)="bookNow()">
              {{ selectedSlot ? appStrings.COMMON.BOOK + ' for ' + (service.priceCents | money) : appStrings.SERVICES.SELECT_TIME }}
            </button>
          </div>
        </div>
      </div>
    </div>

    <ng-template #loading>
      <div class="loading-state"><p>{{ appStrings.COMMON.LOADING }}</p></div>
    </ng-template>
  `,
  styles: [`
    @use 'tokens' as *;
    .page { padding: 40px 0 80px; min-height: calc(100vh - 64px); }
    .back { background: none; border: none; cursor: pointer; color: $primary; font-size: 14px; font-weight: 600; margin-bottom: 32px; padding: 0; }
    .layout { display: grid; grid-template-columns: 1fr 380px; gap: 48px; align-items: start; }
    .thumb { position: relative; height: 320px; background: $primary-soft; border-radius: $radius-2xl; display: flex; align-items: center; justify-content: center; margin-bottom: 24px; }
    .thumb__placeholder { font-size: 64px; }
    .thumb__cat { position: absolute; top: 16px; left: 16px; background: rgba(255,255,255,.9); color: $primary; font-size: 12px; font-weight: 700; padding: 4px 12px; border-radius: $radius-pill; }
    .meta-row { display: flex; gap: 20px; margin-bottom: 16px; }
    .meta-item { font-size: 14px; color: $text-600; font-weight: 500; }
    .title { font-size: 32px; font-weight: 700; color: $ink-800; margin-bottom: 12px; }
    .desc { font-size: 15px; color: $text-600; line-height: 1.7; margin-bottom: 32px; }
    .included__title { font-size: 17px; font-weight: 800; color: $ink-800; margin-bottom: 12px; }
    .included__list { list-style: none; display: flex; flex-direction: column; gap: 10px; }
    .included__item { display: flex; gap: 10px; font-size: 14.5px; color: $ink-700; }
    .included__check { width: 22px; height: 22px; background: $success-bg; color: $success-text; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 11px; font-weight: 800; flex-shrink: 0; }

    .panel { padding: 28px; position: sticky; top: 88px; }
    .panel__title { font-size: 18px; font-weight: 800; color: $ink-800; margin-bottom: 8px; }
    .panel__price { font-family: $font-display; font-size: 32px; font-weight: 700; color: $primary; }
    .panel__per { font-size: 14px; color: $muted-500; font-family: $font-ui; }
    .panel__dur { font-size: 13.5px; color: $muted-500; margin-bottom: 24px; }
    .panel__section { margin-bottom: 20px; }
    .panel__label { display: block; font-size: 13.5px; font-weight: 700; color: $ink-700; margin-bottom: 10px; }
    .date-pills { display: flex; gap: 8px; flex-wrap: wrap; }
    .date-pill {
      display: flex; flex-direction: column; align-items: center; padding: 8px 12px; min-width: 48px;
      border: 1px solid $border-200; border-radius: $radius-lg; background: #fff; cursor: pointer; gap: 2px;
      transition: all $dur-fast;
    }
    .date-pill--active { background: $primary; border-color: $primary; color: #fff; }
    .date-pill__day { font-size: 11px; font-weight: 700; color: inherit; opacity: .7; }
    .date-pill__num { font-size: 16px; font-weight: 800; color: inherit; }
    .slots { display: grid; grid-template-columns: repeat(3, 1fr); gap: 8px; }
    .slot {
      padding: 9px 0; border: 1px solid $border-200; border-radius: $radius-md; background: #fff;
      font-size: 13.5px; font-weight: 700; color: $ink-700; cursor: pointer; transition: all $dur-fast;
    }
    .slot--active { background: $primary-soft; border-color: $primary; color: $primary; }
    .slot--taken { opacity: .4; cursor: not-allowed; }
    .no-slots { font-size: 13.5px; color: $muted-500; }
    .book-btn {
      width: 100%; height: 52px; background: $primary; color: #fff; border: none; cursor: pointer;
      border-radius: $radius-md; font-size: 15px; font-weight: 700; box-shadow: $shadow-btn;
      transition: background $dur-fast; margin-top: 8px;
      &:hover:not(:disabled) { background: $primary-700; }
      &:disabled { opacity: .55; cursor: not-allowed; }
    }
    .loading-state { text-align: center; padding: 80px; color: $muted-500; }
  `],
})
export class ServiceDetailComponent implements OnInit {
  readonly appStrings = APP_STRINGS;
  service: Service | undefined;
  slots: TimeSlot[] = [];
  selectedSlot: TimeSlot | null = null;
  selectedDateIdx = 0;
  datePills: { weekday: string; day: string; date: Date }[] = [];

  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private svc = inject(ServicesService);

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id')!;
    this.svc.getService(id).subscribe(s => { this.service = s; if (s) this.loadSlots(); });
    this.datePills = Array.from({ length: 7 }, (_, i) => {
      const d = new Date(Date.now() + i * 86400000);
      return {
        weekday: d.toLocaleDateString('en-US', { weekday: 'short' }).toUpperCase(),
        day: String(d.getDate()),
        date: d,
      };
    });
  }

  loadSlots() {
    if (!this.service) return;
    const date = this.datePills[this.selectedDateIdx].date.toISOString().split('T')[0];
    this.svc.getAvailability(this.service.id, date).subscribe(s => { this.slots = s; this.selectedSlot = null; });
  }

  goBack() { this.router.navigate(['/services']); }

  bookNow() {
    if (!this.selectedSlot || !this.service) return;
    this.router.navigate(['/book'], { queryParams: { serviceId: this.service.id, slotId: this.selectedSlot.id } });
  }
}

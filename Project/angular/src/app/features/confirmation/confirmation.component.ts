import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { BookingsService } from '../../core/services/bookings.service';
import { Booking } from '../../shared/models/models';
import { APP_STRINGS } from '../../core/constants';

@Component({
  selector: 'be-confirmation',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <!-- App nav -->
    <div style="display:flex;align-items:center;gap:24px;padding:16px 32px;border-bottom:1px solid #F1F1F4;background:#fff;">
      <div style="display:flex;align-items:center;gap:10px;">
        <div style="width:28px;height:28px;border-radius:8px;background:linear-gradient(145deg,#6B5BF0,#4A3BD0);display:flex;align-items:center;justify-content:center;"><svg width="14" height="14" viewBox="0 0 24 24" fill="none"><path d="M13 2L4 14h7l-1 8 10-13h-7l0-7z" fill="#C8F25C"/></svg></div>
        <a routerLink="/" style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:16px;color:#0F1115;text-decoration:none;">{{ appStrings.BRAND.NAME }}</a>
      </div>
    </div>

    <!-- Page -->
    <div style="padding:64px 40px;background:#FBFBFC;min-height:calc(100vh - 66px);display:flex;flex-direction:column;align-items:center;">

      <ng-container *ngIf="booking; else loading">
        <!-- Success icon -->
        <div style="width:76px;height:76px;border-radius:50%;background:#E7F8EE;display:flex;align-items:center;justify-content:center;margin-bottom:22px;" class="pop-in">
          <div style="width:52px;height:52px;border-radius:50%;background:#16A34A;display:flex;align-items:center;justify-content:center;">
            <svg width="26" height="26" viewBox="0 0 24 24" fill="none"><path d="M20 6L9 17l-5-5" stroke="#fff" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/></svg>
          </div>
        </div>

        <h2 style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:32px;letter-spacing:-0.02em;margin:0 0 8px;color:#0F1115;">{{ appStrings.CONFIRMATION.HEADING }}</h2>
        <p style="font-size:16px;color:#545A66;margin:0 0 32px;text-align:center;">
          {{ appStrings.CONFIRMATION.SUBTEXT_PREFIX }}
          <span style="color:#16181D;font-weight:600;">{{ booking.fullName ?? appStrings.CONFIRMATION.SUBTEXT_FALLBACK }}</span>
        </p>

        <!-- Detail card -->
        <div style="width:100%;max-width:460px;background:#fff;border:1px solid #ECECEF;border-radius:18px;padding:26px 28px;margin-bottom:28px;">
          <div style="display:flex;justify-content:space-between;align-items:center;padding-bottom:16px;border-bottom:1px dashed #E2E3E9;margin-bottom:16px;">
            <span style="font-size:13px;color:#8A909C;font-weight:600;">{{ appStrings.CONFIRMATION.LABEL_BOOKING_ID }}</span>
            <span style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:15px;color:#5B4BE3;">#{{ booking.ref }}</span>
          </div>
          <div style="display:flex;flex-direction:column;gap:14px;font-size:14.5px;">
            <div style="display:flex;justify-content:space-between;">
              <span style="color:#8A909C;">{{ appStrings.CONFIRMATION.LABEL_SERVICE }}</span>
              <span style="color:#16181D;font-weight:600;">{{ booking.serviceName }}</span>
            </div>
            <div style="display:flex;justify-content:space-between;">
              <span style="color:#8A909C;">{{ appStrings.CONFIRMATION.LABEL_DATE_TIME }}</span>
              <span style="color:#16181D;font-weight:600;">{{ booking.startsAt | date:"EEE, MMM d · h:mm a" }}</span>
            </div>
            <div style="display:flex;justify-content:space-between;">
              <span style="color:#8A909C;">{{ appStrings.CONFIRMATION.LABEL_COACH }}</span>
              <span style="color:#16181D;font-weight:600;">{{ booking.coachName }}</span>
            </div>
            <div style="display:flex;justify-content:space-between;">
              <span style="color:#8A909C;">{{ appStrings.CONFIRMATION.LABEL_CUSTOMER }}</span>
              <span style="color:#16181D;font-weight:600;">{{ booking.fullName }}</span>
            </div>
          </div>
        </div>

        <!-- Buttons -->
        <div style="display:flex;gap:14px;">
          <a routerLink="/" style="text-decoration:none;background:#fff;border:1px solid #DDDFE6;color:#16181D;font-weight:700;font-size:14.5px;padding:13px 26px;border-radius:12px;">{{ appStrings.CONFIRMATION.BACK_TO_HOME }}</a>
          <a routerLink="/admin/bookings" style="text-decoration:none;background:#5B4BE3;color:#fff;font-weight:700;font-size:14.5px;padding:13px 26px;border-radius:12px;box-shadow:0 8px 20px rgba(91,75,227,0.3);">{{ appStrings.CONFIRMATION.VIEW_BOOKINGS }}</a>
        </div>
      </ng-container>

      <ng-template #loading>
        <p style="color:#8A909C;font-size:15px;margin-top:80px;">{{ appStrings.CONFIRMATION.LOADING }}</p>
      </ng-template>
    </div>
  `,
  styles: [`
    .pop-in { animation: popIn .5s cubic-bezier(0.34,1.56,0.64,1) both; }
    @keyframes popIn { from { transform: scale(0.4); opacity: 0; } to { transform: scale(1); opacity: 1; } }
  `],
})
export class ConfirmationComponent implements OnInit {
  readonly appStrings = APP_STRINGS;
  booking: Booking | undefined;
  private route = inject(ActivatedRoute);
  private bookingService = inject(BookingsService);

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id')!;
    this.bookingService.getBooking(id).subscribe(b => this.booking = b);
  }
}

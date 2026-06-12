import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { StatCardComponent } from '../../../shared/components/stat-card/stat-card.component';
import { StatusBadgeComponent } from '../../../shared/components/status-badge/status-badge.component';
import { BookingsService } from '../../../core/services/bookings.service';
import { Booking, WeeklyAnalytic, MOCK_WEEKLY_ANALYTICS } from '../../../shared/models/models';
import { MoneyPipe } from '../../../shared/pipes/money.pipe';

@Component({
  selector: 'be-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, StatCardComponent, StatusBadgeComponent, MoneyPipe],
  template: `
    <div class="page">
      <!-- Topbar -->
      <div class="topbar">
        <div>
          <h1 class="topbar__title">Dashboard</h1>
          <p class="topbar__sub">{{ today | date:'EEEE, MMMM d, y' }}</p>
        </div>
        <a routerLink="/book" class="topbar__cta">+ New booking</a>
      </div>

      <!-- KPI cards -->
      <div class="kpi-grid">
        <be-stat-card icon="📅" label="Total bookings"   [value]="stats.totalBookings.toString()"                    iconBg="#EEEBFD" />
        <be-stat-card icon="💰" label="Revenue"          [value]="stats.totalRevenueCents | money"                   iconBg="#E7F8EE" />
        <be-stat-card icon="👥" label="Customers"        [value]="stats.totalCustomers.toString()"                   iconBg="#EAEFFC" />
        <be-stat-card icon="🏋️" label="Active services"  [value]="stats.activeServices.toString()"                   iconBg="#FEF3D9" />
      </div>

      <!-- Recent bookings + chart -->
      <div class="body-grid">
        <!-- Recent bookings table -->
        <div class="card table-card">
          <div class="card-header">
            <h3 class="card-header__title">Recent bookings</h3>
            <a routerLink="/admin/bookings" class="card-header__link">View all →</a>
          </div>
          <table class="table">
            <thead>
              <tr>
                <th>Ref</th><th>Customer</th><th>Service</th><th>Date</th><th>Amount</th><th>Status</th>
              </tr>
            </thead>
            <tbody>
              <tr *ngFor="let b of recentBookings">
                <td class="td-ref">{{ b.ref }}</td>
                <td>{{ b.fullName }}</td>
                <td>{{ b.serviceName }}</td>
                <td>{{ b.startsAt | date:'MMM d, h:mm a' }}</td>
                <td class="td-price">{{ b.priceCents | money }}</td>
                <td><be-status-badge [status]="b.status" /></td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- Bar chart -->
        <div class="card chart-card">
          <div class="card-header">
            <h3 class="card-header__title">Weekly bookings</h3>
          </div>
          <div class="chart">
            <div *ngFor="let d of analytics" class="chart__col">
              <div class="chart__bar-wrap">
                <div class="chart__bar" [style.height.%]="barHeight(d.bookings)">
                  <span class="chart__val">{{ d.bookings }}</span>
                </div>
              </div>
              <span class="chart__label">{{ d.day }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    @use 'tokens' as *;
    .page { padding: 32px; }
    .topbar { display: flex; align-items: center; justify-content: space-between; margin-bottom: 32px; }
    .topbar__title { font-size: 26px; font-weight: 700; color: $ink-800; }
    .topbar__sub { font-size: 13.5px; color: $muted-500; margin-top: 3px; }
    .topbar__cta {
      background: $primary; color: #fff; padding: 10px 18px; border-radius: $radius-md;
      font-size: 14px; font-weight: 700; text-decoration: none; box-shadow: $shadow-btn-sm;
      &:hover { background: $primary-700; text-decoration: none; }
    }

    .kpi-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 16px; margin-bottom: 24px; }

    .body-grid { display: grid; grid-template-columns: 2fr 1fr; gap: 20px; }

    .card { background: $surface-0; border: 1px solid $border-100; border-radius: $radius-xl; }
    .card-header { display: flex; align-items: center; justify-content: space-between; padding: 20px 20px 0; margin-bottom: 16px; }
    .card-header__title { font-size: 16px; font-weight: 800; color: $ink-800; }
    .card-header__link { font-size: 13px; font-weight: 700; color: $primary; text-decoration: none; &:hover { text-decoration: underline; } }

    .table { width: 100%; border-collapse: collapse; }
    th {
      text-align: left; font-size: 12px; font-weight: 700; color: $muted-500; text-transform: uppercase;
      letter-spacing: .06em; padding: 0 16px 12px; border-bottom: 1px solid $border-200;
    }
    td { padding: 12px 16px; font-size: 13.5px; color: $ink-700; border-bottom: 1px solid $border-100; }
    tr:last-child td { border-bottom: none; }
    .td-ref { font-family: $font-display; font-weight: 700; color: $primary; }
    .td-price { font-family: $font-display; font-weight: 700; }

    .chart-card { padding: 20px; }
    .chart { display: flex; align-items: flex-end; gap: 10px; height: 160px; padding: 16px 0 0; }
    .chart__col { display: flex; flex-direction: column; align-items: center; gap: 6px; flex: 1; height: 100%; }
    .chart__bar-wrap { flex: 1; display: flex; align-items: flex-end; width: 100%; }
    .chart__bar {
      background: $primary-soft; border-radius: 6px 6px 0 0; width: 100%;
      min-height: 4px; position: relative; transition: height .3s ease;
      display: flex; align-items: flex-start; justify-content: center;
    }
    .chart__val { font-size: 11px; font-weight: 700; color: $primary; margin-top: 4px; }
    .chart__label { font-size: 11px; color: $muted-500; font-weight: 600; }
  `],
})
export class DashboardComponent implements OnInit {
  stats = { totalBookings: 0, totalRevenueCents: 0, totalCustomers: 0, activeServices: 0 };
  recentBookings: Booking[] = [];
  analytics: WeeklyAnalytic[] = MOCK_WEEKLY_ANALYTICS;
  today = new Date();
  maxBookings = 1;

  private bookingService = inject(BookingsService);

  ngOnInit() {
    this.bookingService.getAdminStats().subscribe(s => this.stats = s);
    this.bookingService.getBookings().subscribe(b => {
      this.recentBookings = b.slice(0, 5);
    });
    this.maxBookings = Math.max(...this.analytics.map(d => d.bookings), 1);
  }

  barHeight(val: number): number {
    return (val / this.maxBookings) * 100;
  }
}

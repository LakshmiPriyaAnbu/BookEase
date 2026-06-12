import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { ServicesService } from '../../core/services/services.service';
import { ToastService } from '../../shared/services/toast.service';
import { Service, Category } from '../../shared/models/models';
import { APP_STRINGS } from '../../core/constants';

@Component({
  selector: 'be-services-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  template: `
    <!-- App nav — exact match to design: brand | search (flex-grow) | links (margin-left:auto) | avatar -->
    <div style="display:flex;align-items:center;padding:16px 32px;border-bottom:1px solid #F1F1F4;background:#fff;gap:24px;">
      <!-- Brand -->
      <div style="display:flex;align-items:center;gap:10px;flex-shrink:0;">
        <div style="width:28px;height:28px;border-radius:8px;background:linear-gradient(145deg,#6B5BF0,#4A3BD0);display:flex;align-items:center;justify-content:center;">
          <svg width="14" height="14" viewBox="0 0 24 24" fill="none"><path d="M13 2L4 14h7l-1 8 10-13h-7l0-7z" fill="#C8F25C"/></svg>
        </div>
        <a routerLink="/" style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:16px;color:#0F1115;text-decoration:none;">{{ appStrings.BRAND.NAME }}</a>
      </div>
      <!-- Search — grows to fill space, capped at 440px -->
      <div style="flex:1;max-width:440px;display:flex;align-items:center;gap:10px;height:42px;padding:0 14px;border:1px solid #E2E3E9;border-radius:11px;background:#F7F7FA;">
        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" style="flex-shrink:0;"><circle cx="11" cy="11" r="7" stroke="#8A909C" stroke-width="2"/><path d="M20 20l-3.5-3.5" stroke="#8A909C" stroke-width="2" stroke-linecap="round"/></svg>
        <input style="border:none;background:transparent;outline:none;font-family:inherit;font-size:14px;color:#16181D;width:100%;" [placeholder]="S.SERVICES.SEARCH_PLACEHOLDER" [(ngModel)]="search" (ngModelChange)="applyFilters()" />
      </div>
      <!-- Nav links — pushed to far right -->
      <div style="margin-left:auto;display:flex;align-items:center;gap:20px;font-size:14px;font-weight:600;color:#545A66;flex-shrink:0;">
        <span style="cursor:pointer;">{{ appStrings.NAV.MY_BOOKINGS }}</span>
        <span style="cursor:pointer;">{{ appStrings.NAV.HELP }}</span>
      </div>
      <!-- Avatar -->
      <div style="width:36px;height:36px;border-radius:50%;background:#EEEBFD;display:flex;align-items:center;justify-content:center;font-size:14px;font-weight:700;color:#5B4BE3;flex-shrink:0;cursor:pointer;">A</div>
    </div>

    <!-- Content -->
    <div style="padding:32px 32px 40px;background:#FBFBFC;min-height:calc(100vh - 66px);">
      <div style="display:flex;align-items:flex-end;justify-content:space-between;margin-bottom:22px;">
        <div>
          <h2 style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:30px;letter-spacing:-0.02em;margin:0 0 4px;color:#0F1115;">{{ appStrings.SERVICES.BROWSE_HEADING }}</h2>
          <div style="font-size:14px;color:#8A909C;">Showing {{ filtered.length }} of {{ all.length }} services</div>
        </div>
        <div style="display:flex;align-items:center;gap:8px;height:40px;padding:0 14px;border:1px solid #E2E3E9;border-radius:10px;background:#fff;font-size:13.5px;font-weight:600;color:#16181D;cursor:pointer;">
          {{ appStrings.COMMON.SORT_POPULAR }} <span style="color:#8A909C;margin-left:4px;">▾</span>
        </div>
      </div>

      <!-- Filter chips -->
      <div style="display:flex;flex-wrap:wrap;gap:9px;margin-bottom:26px;">
        <span
          *ngFor="let cat of allFilterNames"
          (click)="selectCat(cat)"
          [style]="selectedCat === cat
            ? 'background:#5B4BE3;color:#fff;font-weight:600;font-size:13.5px;padding:9px 17px;border-radius:999px;cursor:pointer;'
            : 'background:#F2F2F5;color:#545A66;font-weight:600;font-size:13.5px;padding:9px 17px;border-radius:999px;cursor:pointer;'">
          {{ cat }}
        </span>
      </div>

      <!-- Service grid -->
      <div style="display:grid;grid-template-columns:repeat(3,1fr);gap:20px;" *ngIf="filtered.length > 0">
        <div *ngFor="let s of filtered" class="svc-card" style="background:#fff;border:1px solid #ECECEF;border-radius:18px;overflow:hidden;cursor:pointer;" [routerLink]="['/services', s.id]">
          <div style="width:100%;height:140px;background:linear-gradient(135deg,#EEEBFD,#c8bef9);display:flex;align-items:center;justify-content:center;font-size:36px;">💪</div>
          <div style="padding:17px 19px;">
            <div style="display:flex;justify-content:space-between;gap:10px;">
              <span style="font-weight:700;font-size:16px;color:#16181D;">{{ s.name }}</span>
              <span style="font-family:'Space Grotesk',sans-serif;font-weight:700;color:#5B4BE3;">{{ s.priceCents / 100 | currency:'INR':'symbol':'1.0-0' }}</span>
            </div>
            <div style="font-size:13px;color:#8A909C;margin:7px 0 12px;display:flex;gap:12px;">
              <span>⏱ {{ s.durationMin }} {{ appStrings.COMMON.MIN_SUFFIX }}</span>
              <span>★ {{ s.rating }}</span>
            </div>
            <p style="font-size:13px;color:#8A909C;line-height:1.5;margin:0 0 14px;">{{ s.description | slice:0:60 }}…</p>
            <a [routerLink]="['/book']" [queryParams]="{serviceId: s.id}" style="display:block;text-align:center;text-decoration:none;background:#5B4BE3;color:#fff;font-weight:700;font-size:13.5px;padding:11px;border-radius:10px;" (click)="$event.stopPropagation()">{{ appStrings.COMMON.BOOK }}</a>
          </div>
        </div>
      </div>

      <!-- Empty state -->
      <div *ngIf="filtered.length === 0" style="text-align:center;padding:80px 24px;">
        <div style="font-size:48px;margin-bottom:16px;">🔍</div>
        <h3 style="font-size:20px;font-weight:800;color:#16181D;margin-bottom:8px;">{{ appStrings.SERVICES.EMPTY_TITLE }}</h3>
        <p style="font-size:14.5px;color:#8A909C;">{{ appStrings.SERVICES.EMPTY_MESSAGE }}</p>
      </div>
    </div>
  `,
  styles: [`.svc-card { transition: box-shadow .2s, transform .2s; } .svc-card:hover { box-shadow: 0 16px 36px rgba(16,17,22,0.1); transform: translateY(-3px); }`],
})
export class ServicesListComponent implements OnInit {
  readonly appStrings = APP_STRINGS;
  private svc = inject(ServicesService);
  private toastService = inject(ToastService);
  all: Service[] = [];
  filtered: Service[] = [];
  categories: Category[] = [];
  search = '';
  selectedCat = 'All';

  get categoryNames(): string[] { return this.categories.map(c => c.name); }
  get allFilterNames(): string[] { return ['All', ...this.categoryNames]; }

  ngOnInit() {
    this.svc.getCategories().subscribe(c => this.categories = c);
    this.svc.getServices().subscribe(s => { this.all = s; this.filtered = s; });
  }

  selectCat(cat: string): void {
    this.selectedCat = cat;
    this.applyFilters();
    if (cat !== 'All') {
      this.toastService.show(`Showing ${this.filtered.length} ${cat} services`, 'info', undefined, 2000);
    }
  }

  applyFilters() {
    const q = this.search.toLowerCase();
    const catId = this.categories.find(c => c.name === this.selectedCat)?.id;
    this.filtered = this.all.filter(s => {
      const matchSearch = !q || s.name.toLowerCase().includes(q) || (s.coachName ?? '').toLowerCase().includes(q);
      const matchCat = this.selectedCat === 'All' || s.categoryId === catId;
      return matchSearch && matchCat;
    });
  }
}

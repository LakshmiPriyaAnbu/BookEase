import { Component, signal, computed, inject, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { APP_STRINGS } from '../../core/constants';

@Component({
  selector: 'be-pricing',
  standalone: true,
  imports: [CommonModule, RouterModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <!-- Navbar -->
    <nav style="display:flex;align-items:center;justify-content:space-between;padding:20px 40px;border-bottom:1px solid #F1F1F4;background:#fff;">
      <div style="display:flex;align-items:center;gap:10px;">
        <div style="width:30px;height:30px;border-radius:9px;background:linear-gradient(145deg,#6B5BF0,#4A3BD0);display:flex;align-items:center;justify-content:center;">
          <svg width="15" height="15" viewBox="0 0 24 24" fill="none"><path d="M13 2L4 14h7l-1 8 10-13h-7l0-7z" fill="#C8F25C"/></svg>
        </div>
        <span style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:18px;letter-spacing:-0.02em;color:#0F1115;">{{ appStrings.BRAND.NAME }}</span>
      </div>
      <div style="display:flex;align-items:center;gap:30px;font-size:14.5px;font-weight:600;color:#545A66;">
        <a routerLink="/services-page" [style.color]="activeRoute() === '/services-page' ? '#5B4BE3' : '#545A66'" [style.fontWeight]="activeRoute() === '/services-page' ? '700' : '600'" style="text-decoration:none;">{{ appStrings.NAV.SERVICES }}</a>
        <a routerLink="/how-it-works" [style.color]="activeRoute() === '/how-it-works' ? '#5B4BE3' : '#545A66'" [style.fontWeight]="activeRoute() === '/how-it-works' ? '700' : '600'" style="text-decoration:none;">{{ appStrings.NAV.HOW_IT_WORKS }}</a>
        <a routerLink="/pricing" [style.color]="activeRoute() === '/pricing' ? '#5B4BE3' : '#545A66'" [style.fontWeight]="activeRoute() === '/pricing' ? '700' : '600'" style="text-decoration:none;">{{ appStrings.NAV.PRICING }}</a>
        <a routerLink="/contact" [style.color]="activeRoute() === '/contact' ? '#5B4BE3' : '#545A66'" [style.fontWeight]="activeRoute() === '/contact' ? '700' : '600'" style="text-decoration:none;">{{ appStrings.NAV.CONTACT }}</a>
      </div>
      <div style="display:flex;align-items:center;gap:14px;">
        <a routerLink="/login" style="font-size:14.5px;font-weight:700;color:#16181D;text-decoration:none;">{{ appStrings.NAV.LOG_IN }}</a>
        <a routerLink="/book" style="text-decoration:none;background:#5B4BE3;color:#fff;font-weight:700;font-size:14px;padding:11px 20px;border-radius:11px;box-shadow:0 4px 12px rgba(91,75,227,0.28);">{{ appStrings.NAV.BOOK_NOW }}</a>
      </div>
    </nav>

    <!-- Page Header -->
    <div style="padding:60px 40px 32px;text-align:center;background:#fff;">
      <div style="font-size:12.5px;font-weight:700;letter-spacing:0.1em;color:#5B4BE3;margin-bottom:12px;">{{ appStrings.PRICING.EYEBROW }}</div>
      <h1 style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:46px;letter-spacing:-0.03em;margin:0 0 16px;color:#0F1115;">{{ appStrings.PRICING.HEADING }}</h1>
      <p style="font-size:17px;color:#545A66;line-height:1.6;margin:0 auto 28px;max-width:50ch;">{{ appStrings.PRICING.SUBTEXT }}</p>
      <!-- Billing toggle -->
      <div style="display:inline-flex;align-items:center;background:#F2F2F5;border-radius:999px;padding:4px;">
        <button
          (click)="billingCycle.set('monthly')"
          [style.background]="billingCycle() === 'monthly' ? '#fff' : 'transparent'"
          [style.fontWeight]="billingCycle() === 'monthly' ? '700' : '600'"
          [style.color]="billingCycle() === 'monthly' ? '#16181D' : '#8A909C'"
          [style.boxShadow]="billingCycle() === 'monthly' ? '0 1px 3px rgba(0,0,0,0.08)' : 'none'"
          style="font-family:inherit;cursor:pointer;border:none;font-size:14px;padding:9px 20px;border-radius:999px;transition:all .15s;">
          {{ appStrings.PRICING.BILLING_MONTHLY }}
        </button>
        <button
          (click)="billingCycle.set('yearly')"
          [style.background]="billingCycle() === 'yearly' ? '#fff' : 'transparent'"
          [style.fontWeight]="billingCycle() === 'yearly' ? '700' : '600'"
          [style.color]="billingCycle() === 'yearly' ? '#16181D' : '#8A909C'"
          [style.boxShadow]="billingCycle() === 'yearly' ? '0 1px 3px rgba(0,0,0,0.08)' : 'none'"
          style="font-family:inherit;cursor:pointer;border:none;font-size:14px;padding:9px 20px;border-radius:999px;transition:all .15s;">
          {{ appStrings.PRICING.BILLING_YEARLY }}
        </button>
      </div>
    </div>

    <!-- Pricing Grid -->
    <div style="padding:16px 40px 56px;max-width:1000px;margin:0 auto;">
      <div style="display:grid;grid-template-columns:repeat(3,1fr);gap:22px;">

        <!-- Starter -->
        <div style="border:1px solid #ECECEF;border-radius:20px;padding:32px;display:flex;flex-direction:column;">
          <div style="font-weight:700;font-size:15px;color:#8A909C;margin-bottom:14px;">{{ appStrings.PRICING.PLAN_STARTER_NAME }}</div>
          <div style="font-family:'Space Grotesk',sans-serif;font-size:44px;font-weight:700;letter-spacing:-0.02em;color:#0F1115;margin-bottom:4px;">{{ appStrings.PRICING.PLAN_STARTER_PRICE }}</div>
          <div style="font-size:13.5px;color:#8A909C;margin-bottom:24px;">{{ appStrings.PRICING.PLAN_STARTER_BILLING }}</div>
          <div style="display:flex;flex-direction:column;gap:12px;font-size:14px;color:#545A66;flex:1;margin-bottom:26px;">
            <span *ngFor="let f of appStrings.PRICING.PLAN_STARTER_FEATURES">{{ f }}</span>
          </div>
          <button style="width:100%;font-family:inherit;cursor:pointer;background:#fff;border:1px solid #DDDFE6;color:#16181D;font-weight:700;font-size:14.5px;padding:13px;border-radius:12px;">{{ appStrings.PRICING.PLAN_STARTER_CTA }}</button>
        </div>

        <!-- Pro -->
        <div style="border:2px solid #5B4BE3;border-radius:20px;padding:42px 32px 32px;position:relative;box-shadow:0 20px 44px rgba(91,75,227,0.18);display:flex;flex-direction:column;">
          <span style="position:absolute;top:-13px;left:32px;background:#5B4BE3;color:#fff;font-size:12px;font-weight:700;padding:5px 12px;border-radius:999px;">{{ appStrings.PRICING.BADGE_POPULAR }}</span>
          <div style="font-weight:700;font-size:15px;color:#5B4BE3;margin-bottom:14px;">{{ appStrings.PRICING.PLAN_PRO_NAME }}</div>
          <div style="font-family:'Space Grotesk',sans-serif;font-size:44px;font-weight:700;letter-spacing:-0.02em;color:#0F1115;margin-bottom:4px;">
            {{ proPrice() }}<span style="font-size:17px;color:#8A909C;font-weight:600;">{{ appStrings.COMMON.PER_MONTH }}</span>
          </div>
          <div style="font-size:13.5px;color:#8A909C;margin-bottom:24px;">{{ appStrings.PRICING.PLAN_PRO_BILLING }}</div>
          <div style="display:flex;flex-direction:column;gap:12px;font-size:14px;color:#545A66;flex:1;margin-bottom:26px;">
            <span *ngFor="let f of appStrings.PRICING.PLAN_PRO_FEATURES">{{ f }}</span>
          </div>
          <button style="width:100%;font-family:inherit;cursor:pointer;border:none;background:#5B4BE3;color:#fff;font-weight:700;font-size:14.5px;padding:13px;border-radius:12px;box-shadow:0 8px 20px rgba(91,75,227,0.30);">{{ appStrings.PRICING.PLAN_PRO_CTA }}</button>
        </div>

        <!-- Elite -->
        <div style="border:1px solid #ECECEF;border-radius:20px;padding:32px;display:flex;flex-direction:column;">
          <div style="font-weight:700;font-size:15px;color:#8A909C;margin-bottom:14px;">{{ appStrings.PRICING.PLAN_ELITE_NAME }}</div>
          <div style="font-family:'Space Grotesk',sans-serif;font-size:44px;font-weight:700;letter-spacing:-0.02em;color:#0F1115;margin-bottom:4px;">
            {{ elitePrice() }}<span style="font-size:17px;color:#8A909C;font-weight:600;">{{ appStrings.COMMON.PER_MONTH }}</span>
          </div>
          <div style="font-size:13.5px;color:#8A909C;margin-bottom:24px;">{{ appStrings.PRICING.PLAN_ELITE_BILLING }}</div>
          <div style="display:flex;flex-direction:column;gap:12px;font-size:14px;color:#545A66;flex:1;margin-bottom:26px;">
            <span *ngFor="let f of appStrings.PRICING.PLAN_ELITE_FEATURES">{{ f }}</span>
          </div>
          <button style="width:100%;font-family:inherit;cursor:pointer;background:#fff;border:1px solid #DDDFE6;color:#16181D;font-weight:700;font-size:14.5px;padding:13px;border-radius:12px;">{{ appStrings.PRICING.PLAN_ELITE_CTA }}</button>
        </div>

      </div>
    </div>

    <!-- FAQ -->
    <div style="max-width:760px;margin:56px auto 0;padding:0 40px 80px;">
      <h2 style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:26px;color:#0F1115;margin:0 0 20px;">{{ appStrings.PRICING.FAQ_HEADING }}</h2>
      <div style="display:flex;flex-direction:column;gap:10px;">
        <div *ngFor="let faq of faqs"
          style="border:1px solid #ECECEF;border-radius:14px;padding:18px 22px;display:flex;justify-content:space-between;align-items:center;">
          <span style="font-size:15px;font-weight:600;color:#16181D;">{{ faq }}</span>
          <span style="font-size:20px;color:#B6BBC4;font-weight:400;line-height:1;">+</span>
        </div>
      </div>
    </div>
  `,
  styles: [`:host { display:block; background:#fff; }`],
})
export class PricingComponent {
  readonly appStrings = APP_STRINGS;
  private router = inject(Router);

  readonly activeRoute = computed(() => this.router.url.split('?')[0]);
  readonly billingCycle = signal<'monthly' | 'yearly'>('monthly');

  readonly proPrice = computed(() => this.billingCycle() === 'yearly' ? '$39' : '$49');
  readonly elitePrice = computed(() => this.billingCycle() === 'yearly' ? '$79' : '$99');

  readonly faqs = APP_STRINGS.PRICING.FAQS;
}

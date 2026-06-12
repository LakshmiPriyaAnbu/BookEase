import { Component, computed, inject, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { APP_STRINGS } from '../../core/constants';

@Component({
  selector: 'be-how-it-works',
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
    <div style="padding:60px 40px 44px;text-align:center;background:#fff;">
      <div style="font-size:12.5px;font-weight:700;letter-spacing:0.1em;color:#5B4BE3;margin-bottom:12px;">{{ appStrings.HOW_IT_WORKS.EYEBROW }}</div>
      <h1 style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:46px;letter-spacing:-0.03em;margin:0 0 16px;color:#0F1115;">{{ appStrings.HOW_IT_WORKS.HEADING }}</h1>
      <p style="font-size:17px;color:#545A66;line-height:1.6;margin:0 auto;max-width:52ch;">{{ appStrings.HOW_IT_WORKS.SUBTEXT }}</p>
    </div>

    <!-- Steps -->
    <div style="padding:0 40px 16px;display:flex;flex-direction:column;gap:20px;">
      <div *ngFor="let step of steps; let i = index"
        style="background:#F7F7FA;border:1px solid #ECECEF;border-radius:20px;padding:30px 34px;display:grid;grid-template-columns:64px 1fr 1fr;gap:28px;align-items:center;">
        <!-- Number -->
        <div style="width:56px;height:56px;border-radius:16px;background:#5B4BE3;color:#fff;font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:24px;display:flex;align-items:center;justify-content:center;box-shadow:0 8px 20px rgba(91,75,227,0.3);flex-shrink:0;">
          {{ i + 1 }}
        </div>
        <!-- Text -->
        <div>
          <h3 style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:24px;color:#0F1115;margin:0 0 10px;">{{ step.title }}</h3>
          <p style="font-size:15.5px;color:#545A66;line-height:1.6;margin:0;">{{ step.description }}</p>
        </div>
        <!-- Image placeholder -->
        <div style="height:150px;background:#E4E4E9;border-radius:14px;display:flex;align-items:center;justify-content:center;">
          <svg width="36" height="36" viewBox="0 0 24 24" fill="none">
            <rect x="3" y="3" width="18" height="18" rx="4" stroke="#B6BBC4" stroke-width="1.5"/>
            <circle cx="8.5" cy="8.5" r="1.5" stroke="#B6BBC4" stroke-width="1.5"/>
            <path d="M3 16l5-5 4 4 3-3 5 5" stroke="#B6BBC4" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </div>
      </div>
    </div>

    <!-- Guarantees -->
    <div style="padding:48px 40px 60px;">
      <div style="display:grid;grid-template-columns:repeat(3,1fr);gap:22px;">
        <!-- Instant confirmation -->
        <div style="text-align:center;padding:32px 24px;">
          <div style="width:48px;height:48px;border-radius:13px;background:#EEEBFD;display:flex;align-items:center;justify-content:center;margin:0 auto 16px;">
            <svg width="22" height="22" viewBox="0 0 24 24" fill="none">
              <path d="M13 2L4 14h7l-1 8 10-13h-7l0-7z" fill="#5B4BE3"/>
            </svg>
          </div>
          <div style="font-weight:700;font-size:16px;color:#16181D;margin-bottom:8px;">{{ appStrings.HOW_IT_WORKS.GUARANTEE_INSTANT_TITLE }}</div>
          <p style="font-size:14px;color:#8A909C;line-height:1.6;margin:0;">{{ appStrings.HOW_IT_WORKS.GUARANTEE_INSTANT_DESC }}</p>
        </div>
        <!-- Free rescheduling -->
        <div style="text-align:center;padding:32px 24px;">
          <div style="width:48px;height:48px;border-radius:13px;background:#EEEBFD;display:flex;align-items:center;justify-content:center;margin:0 auto 16px;">
            <svg width="22" height="22" viewBox="0 0 24 24" fill="none">
              <path d="M20 6L9 17l-5-5" stroke="#5B4BE3" stroke-width="2.2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
          <div style="font-weight:700;font-size:16px;color:#16181D;margin-bottom:8px;">{{ appStrings.HOW_IT_WORKS.GUARANTEE_RESCHEDULE_TITLE }}</div>
          <p style="font-size:14px;color:#8A909C;line-height:1.6;margin:0;">{{ appStrings.HOW_IT_WORKS.GUARANTEE_RESCHEDULE_DESC }}</p>
        </div>
        <!-- Secure payments -->
        <div style="text-align:center;padding:32px 24px;">
          <div style="width:48px;height:48px;border-radius:13px;background:#EEEBFD;display:flex;align-items:center;justify-content:center;margin:0 auto 16px;">
            <svg width="22" height="22" viewBox="0 0 24 24" fill="none">
              <rect x="2" y="7" width="20" height="13" rx="2" stroke="#5B4BE3" stroke-width="2"/>
              <path d="M2 11h20" stroke="#5B4BE3" stroke-width="2"/>
              <rect x="5" y="14" width="4" height="2" rx="1" fill="#5B4BE3"/>
            </svg>
          </div>
          <div style="font-weight:700;font-size:16px;color:#16181D;margin-bottom:8px;">{{ appStrings.HOW_IT_WORKS.GUARANTEE_PAYMENTS_TITLE }}</div>
          <p style="font-size:14px;color:#8A909C;line-height:1.6;margin:0;">{{ appStrings.HOW_IT_WORKS.GUARANTEE_PAYMENTS_DESC }}</p>
        </div>
      </div>
    </div>
  `,
  styles: [`:host { display:block; background:#fff; }`],
})
export class HowItWorksComponent {
  readonly appStrings = APP_STRINGS;
  private router = inject(Router);

  readonly activeRoute = computed(() => this.router.url.split('?')[0]);

  readonly steps = APP_STRINGS.HOW_IT_WORKS.STEPS;
}

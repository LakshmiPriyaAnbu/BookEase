import { Component, computed, inject, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';

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
        <span style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:18px;letter-spacing:-0.02em;color:#0F1115;">BookEase</span>
      </div>
      <div style="display:flex;align-items:center;gap:30px;font-size:14.5px;font-weight:600;color:#545A66;">
        <a routerLink="/services-page" [style.color]="activeRoute() === '/services-page' ? '#5B4BE3' : '#545A66'" [style.fontWeight]="activeRoute() === '/services-page' ? '700' : '600'" style="text-decoration:none;">Services</a>
        <a routerLink="/how-it-works" [style.color]="activeRoute() === '/how-it-works' ? '#5B4BE3' : '#545A66'" [style.fontWeight]="activeRoute() === '/how-it-works' ? '700' : '600'" style="text-decoration:none;">How it works</a>
        <a routerLink="/pricing" [style.color]="activeRoute() === '/pricing' ? '#5B4BE3' : '#545A66'" [style.fontWeight]="activeRoute() === '/pricing' ? '700' : '600'" style="text-decoration:none;">Pricing</a>
        <a routerLink="/contact" [style.color]="activeRoute() === '/contact' ? '#5B4BE3' : '#545A66'" [style.fontWeight]="activeRoute() === '/contact' ? '700' : '600'" style="text-decoration:none;">Contact</a>
      </div>
      <div style="display:flex;align-items:center;gap:14px;">
        <a routerLink="/login" style="font-size:14.5px;font-weight:700;color:#16181D;text-decoration:none;">Log in</a>
        <a routerLink="/book" style="text-decoration:none;background:#5B4BE3;color:#fff;font-weight:700;font-size:14px;padding:11px 20px;border-radius:11px;box-shadow:0 4px 12px rgba(91,75,227,0.28);">Book Now</a>
      </div>
    </nav>

    <!-- Page Header -->
    <div style="padding:60px 40px 44px;text-align:center;background:#fff;">
      <div style="font-size:12.5px;font-weight:700;letter-spacing:0.1em;color:#5B4BE3;margin-bottom:12px;">HOW IT WORKS</div>
      <h1 style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:46px;letter-spacing:-0.03em;margin:0 0 16px;color:#0F1115;">Booked in three simple steps</h1>
      <p style="font-size:17px;color:#545A66;line-height:1.6;margin:0 auto;max-width:52ch;">No phone calls, no waiting on replies. Go from browsing to a confirmed session in under a minute.</p>
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
          <div style="font-weight:700;font-size:16px;color:#16181D;margin-bottom:8px;">Instant confirmation</div>
          <p style="font-size:14px;color:#8A909C;line-height:1.6;margin:0;">Slots update live — what you see is what you get.</p>
        </div>
        <!-- Free rescheduling -->
        <div style="text-align:center;padding:32px 24px;">
          <div style="width:48px;height:48px;border-radius:13px;background:#EEEBFD;display:flex;align-items:center;justify-content:center;margin:0 auto 16px;">
            <svg width="22" height="22" viewBox="0 0 24 24" fill="none">
              <path d="M20 6L9 17l-5-5" stroke="#5B4BE3" stroke-width="2.2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
          <div style="font-weight:700;font-size:16px;color:#16181D;margin-bottom:8px;">Free rescheduling</div>
          <p style="font-size:14px;color:#8A909C;line-height:1.6;margin:0;">Plans change. Move or cancel up to 12 hours before.</p>
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
          <div style="font-weight:700;font-size:16px;color:#16181D;margin-bottom:8px;">Secure payments</div>
          <p style="font-size:14px;color:#8A909C;line-height:1.6;margin:0;">Pay safely with saved cards or Apple Pay.</p>
        </div>
      </div>
    </div>
  `,
  styles: [`:host { display:block; background:#fff; }`],
})
export class HowItWorksComponent {
  private router = inject(Router);

  readonly activeRoute = computed(() => this.router.url.split('?')[0]);

  readonly steps = [
    {
      title: 'Choose a service',
      description: 'Browse coaches and programs, filter by goal, schedule and budget. Every coach is verified and client-reviewed.',
    },
    {
      title: 'Pick a time slot',
      description: 'See real-time availability and lock the slot that fits your week. Reschedule free up to 12 hours before.',
    },
    {
      title: 'Confirm & go',
      description: 'Pay securely in-app and get instant confirmation plus a calendar invite. Just show up and train.',
    },
  ];
}

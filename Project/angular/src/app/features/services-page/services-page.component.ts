import { Component, signal, computed, inject, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';

interface ServiceCard {
  id: number;
  name: string;
  price: string;
  description: string;
  durationMin: number;
  rating: number;
  category: string;
}

@Component({
  selector: 'be-services-page',
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
    <div style="background:#F7F7FA;padding:60px 40px 40px;text-align:center;border-bottom:1px solid #F1F1F4;">
      <div style="display:inline-flex;align-items:center;gap:8px;background:#EEEBFD;color:#4A3BD0;font-weight:700;font-size:13px;padding:7px 14px;border-radius:999px;margin-bottom:20px;">
        24 services across 6 categories
      </div>
      <h1 style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:46px;letter-spacing:-0.03em;margin:0 0 16px;color:#0F1115;">Find the training that fits you</h1>
      <p style="font-size:17px;color:#545A66;line-height:1.6;margin:0 auto;max-width:54ch;">From 1:1 strength coaching to mobility and nutrition — every session is led by a verified coach and bookable in seconds.</p>
    </div>

    <!-- Category Chips -->
    <div style="padding:28px 40px 0;display:flex;flex-wrap:wrap;gap:10px;justify-content:center;">
      <button
        *ngFor="let cat of categories"
        (click)="selectCategory(cat)"
        [style.background]="activeCategory() === cat ? '#5B4BE3' : '#F2F2F5'"
        [style.color]="activeCategory() === cat ? '#fff' : '#545A66'"
        style="font-family:inherit;cursor:pointer;border:none;font-weight:600;font-size:14px;padding:10px 20px;border-radius:999px;">
        {{ cat }}
      </button>
    </div>

    <!-- Service Grid -->
    <div style="padding:32px 40px 60px;">
      <div style="display:grid;grid-template-columns:repeat(3,1fr);gap:22px;">
        <div
          *ngFor="let s of filteredServices()"
          style="border:1px solid #ECECEF;border-radius:18px;overflow:hidden;">
          <!-- Image placeholder -->
          <div style="width:100%;height:158px;background:#EEEBFD;display:flex;align-items:center;justify-content:center;">
            <svg width="40" height="40" viewBox="0 0 24 24" fill="none">
              <rect x="3" y="3" width="18" height="18" rx="4" stroke="#C8BFFA" stroke-width="1.5"/>
              <circle cx="8.5" cy="8.5" r="1.5" stroke="#C8BFFA" stroke-width="1.5"/>
              <path d="M3 16l5-5 4 4 3-3 5 5" stroke="#C8BFFA" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
          <!-- Card body -->
          <div style="padding:18px 20px;">
            <div style="display:flex;justify-content:space-between;align-items:flex-start;gap:10px;margin-bottom:8px;">
              <span style="font-weight:700;font-size:16.5px;color:#16181D;">{{ s.name }}</span>
              <span style="font-family:'Space Grotesk',sans-serif;font-weight:700;color:#5B4BE3;font-size:16px;white-space:nowrap;">{{ s.price }}</span>
            </div>
            <p style="font-size:13.5px;color:#8A909C;line-height:1.5;margin:0 0 10px;">{{ s.description }}</p>
            <div style="font-size:13px;color:#8A909C;font-weight:600;margin-bottom:14px;">
              &#x23F1; {{ s.durationMin }}min &nbsp;&middot;&nbsp; &#x2605; {{ s.rating }}
            </div>
            <a routerLink="/book" style="text-decoration:none;display:inline-block;background:#EEEBFD;color:#4A3BD0;font-weight:700;font-size:13.5px;padding:9px 18px;border-radius:10px;">Book</a>
          </div>
        </div>
      </div>
    </div>

    <!-- CTA Band -->
    <div style="background:linear-gradient(158deg,#4A3DAE,#2C2370);padding:48px 40px;display:flex;align-items:center;justify-content:space-between;flex-wrap:wrap;gap:24px;">
      <div>
        <h2 style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:30px;letter-spacing:-0.02em;color:#fff;margin:0 0 10px;">Not sure where to start?</h2>
        <p style="font-size:16px;color:#C7C2E8;margin:0;">Book a free fitness assessment and we'll match you to a coach.</p>
      </div>
      <a routerLink="/book" style="text-decoration:none;background:#C8F25C;color:#1B2906;font-weight:700;font-size:16px;padding:16px 30px;border-radius:13px;white-space:nowrap;">Book assessment</a>
    </div>
  `,
  styles: [`:host { display:block; background:#fff; }`],
})
export class ServicesPageComponent {
  private router = inject(Router);

  readonly activeRoute = computed(() => this.router.url.split('?')[0]);

  readonly categories = ['All', 'Strength', 'HIIT', 'Mobility', 'Recovery', 'Nutrition', 'Online'];
  readonly activeCategory = signal<string>('All');

  readonly allServices = signal<ServiceCard[]>([
    { id: 1, name: '1:1 Strength Coaching', price: '$75', description: 'Personalised strength program with a certified coach, tracked weekly.', durationMin: 60, rating: 4.9, category: 'Strength' },
    { id: 2, name: 'HIIT Blast Class', price: '$30', description: 'High-intensity interval training for maximum calorie burn in minimal time.', durationMin: 45, rating: 4.8, category: 'HIIT' },
    { id: 3, name: 'Mobility Flow', price: '$40', description: 'Targeted mobility drills to improve range of motion and reduce injury risk.', durationMin: 50, rating: 4.9, category: 'Mobility' },
    { id: 4, name: 'Recovery & Stretch', price: '$35', description: 'Guided foam rolling and deep stretching for post-training recovery.', durationMin: 40, rating: 4.7, category: 'Recovery' },
    { id: 5, name: 'Nutrition Consultation', price: '$60', description: 'One-hour session with a registered dietitian to build your meal strategy.', durationMin: 60, rating: 5.0, category: 'Nutrition' },
    { id: 6, name: 'Online Power Program', price: '$55', description: 'Remote coaching with video check-ins, perfect for busy schedules.', durationMin: 45, rating: 4.8, category: 'Online' },
  ]);

  readonly filteredServices = computed<ServiceCard[]>(() => {
    const cat = this.activeCategory();
    const all = this.allServices();
    return cat === 'All' ? all : all.filter(s => s.category === cat);
  });

  selectCategory(cat: string): void {
    this.activeCategory.set(cat);
  }
}

import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ServicesService } from '../../core/services/services.service';
import { Service } from '../../shared/models/models';
import { APP_STRINGS } from '../../core/constants';

@Component({
  selector: 'be-landing',
  standalone: true,
  imports: [CommonModule, RouterModule],
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
        <a routerLink="/services" style="color:#545A66;text-decoration:none;">{{ appStrings.NAV.SERVICES }}</a>
        <a routerLink="/how-it-works" style="color:#545A66;text-decoration:none;">{{ appStrings.NAV.HOW_IT_WORKS }}</a>
        <a routerLink="/pricing" style="color:#545A66;text-decoration:none;">{{ appStrings.NAV.PRICING }}</a>
        <a routerLink="/contact" style="color:#545A66;text-decoration:none;">{{ appStrings.NAV.CONTACT }}</a>
      </div>
      <div style="display:flex;align-items:center;gap:14px;">
        <a routerLink="/login" style="font-size:14.5px;font-weight:700;color:#16181D;text-decoration:none;">{{ appStrings.NAV.LOG_IN }}</a>
        <a routerLink="/book" style="font-family:inherit;text-decoration:none;background:#5B4BE3;color:#fff;font-weight:700;font-size:14px;padding:11px 20px;border-radius:11px;box-shadow:0 4px 12px rgba(91,75,227,0.28);">{{ appStrings.NAV.BOOK_NOW }}</a>
      </div>
    </nav>

    <!-- Hero -->
    <div style="display:grid;grid-template-columns:1.04fr 0.96fr;gap:56px;padding:72px 40px;align-items:center;background:#fff;">
      <div>
        <div style="display:inline-flex;align-items:center;gap:8px;background:#EEEBFD;color:#4A3BD0;font-weight:700;font-size:13px;padding:7px 14px;border-radius:999px;margin-bottom:24px;">
          <span style="width:6px;height:6px;border-radius:50%;background:#5B4BE3;"></span>
          {{ appStrings.HOME.HERO_BADGE }}
        </div>
        <h1 style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:58px;line-height:1.0;letter-spacing:-0.035em;margin:0 0 22px;color:#0F1115;">Train with the best.<br>Book in seconds.</h1>
        <p style="font-size:18.5px;line-height:1.6;color:#545A66;margin:0 0 32px;max-width:46ch;">{{ appStrings.HOME.HERO_SUBTITLE }}</p>
        <div style="display:flex;gap:14px;margin-bottom:36px;">
          <a routerLink="/book" style="text-decoration:none;display:inline-flex;align-items:center;gap:9px;background:#5B4BE3;color:#fff;font-weight:700;font-size:15.5px;padding:15px 28px;border-radius:13px;box-shadow:0 8px 22px rgba(91,75,227,0.32);">{{ appStrings.HOME.HERO_CTA_PRIMARY }} <span style="font-size:17px;">→</span></a>
          <a routerLink="/services" style="text-decoration:none;background:#fff;border:1px solid #DDDFE6;color:#16181D;font-weight:700;font-size:15.5px;padding:15px 28px;border-radius:13px;">{{ appStrings.HOME.HERO_CTA_SECONDARY }}</a>
        </div>
        <div style="display:flex;align-items:center;gap:14px;">
          <div style="display:flex;">
            <div style="width:38px;height:38px;border-radius:50%;background:#EEEBFD;border:2px solid #fff;display:flex;align-items:center;justify-content:center;font-size:13px;font-weight:700;color:#5B4BE3;">J</div>
            <div style="width:38px;height:38px;border-radius:50%;background:#FEF3D9;border:2px solid #fff;margin-left:-12px;display:flex;align-items:center;justify-content:center;font-size:13px;font-weight:700;color:#B45309;">M</div>
            <div style="width:38px;height:38px;border-radius:50%;background:#E7F8EE;border:2px solid #fff;margin-left:-12px;display:flex;align-items:center;justify-content:center;font-size:13px;font-weight:700;color:#15803D;">S</div>
          </div>
          <div>
            <div style="font-weight:700;font-size:14px;color:#16181D;">{{ appStrings.HOME.RATING_TEXT }}</div>
            <div style="font-size:13px;color:#8A909C;">{{ appStrings.HOME.RATING_SUBTEXT }}</div>
          </div>
        </div>
      </div>
      <div style="position:relative;">
        <div style="width:100%;height:460px;border-radius:22px;background:linear-gradient(135deg,#EEEBFD,#c8bef9);display:flex;align-items:center;justify-content:center;">
          <span style="font-size:80px;">🏋️</span>
        </div>
        <div style="position:absolute;bottom:22px;left:-24px;background:#fff;border:1px solid #ECECEF;border-radius:16px;padding:16px 18px;box-shadow:0 16px 40px rgba(16,17,22,0.16);display:flex;gap:13px;align-items:center;">
          <div style="width:46px;height:46px;border-radius:12px;background:#EEEBFD;display:flex;flex-direction:column;align-items:center;justify-content:center;">
            <span style="font-size:10px;font-weight:700;color:#5B4BE3;">JUN</span>
            <span style="font-family:'Space Grotesk',sans-serif;font-size:17px;font-weight:700;color:#4A3BD0;line-height:1;">14</span>
          </div>
          <div>
            <div style="font-weight:700;font-size:14px;color:#16181D;">{{ appStrings.HOME.SESSION_CONFIRMED }}</div>
            <div style="font-size:12.5px;color:#8A909C;">{{ appStrings.HOME.SESSION_COACH }}</div>
          </div>
        </div>
      </div>
    </div>

    <!-- Popular Services -->
    <div style="padding:8px 40px 72px;background:#fff;">
      <div style="display:flex;align-items:flex-end;justify-content:space-between;margin-bottom:28px;">
        <div>
          <div style="font-size:12.5px;font-weight:700;letter-spacing:0.1em;color:#5B4BE3;margin-bottom:8px;">{{ appStrings.HOME.SERVICES_EYEBROW }}</div>
          <h2 style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:34px;letter-spacing:-0.02em;margin:0;color:#0F1115;">{{ appStrings.HOME.SERVICES_HEADING }}</h2>
        </div>
        <a routerLink="/services" style="font-size:14.5px;font-weight:700;color:#5B4BE3;text-decoration:none;">{{ appStrings.COMMON.VIEW_ALL }}</a>
      </div>
      <div style="display:grid;grid-template-columns:repeat(3,1fr);gap:22px;">
        <div *ngFor="let s of services" class="svc-card" style="border:1px solid #ECECEF;border-radius:18px;overflow:hidden;cursor:pointer;" [routerLink]="['/services', s.id]">
          <div style="width:100%;height:150px;background:linear-gradient(135deg,#EEEBFD,#c8bef9);display:flex;align-items:center;justify-content:center;font-size:40px;">💪</div>
          <div style="padding:18px 20px;">
            <div style="display:flex;justify-content:space-between;align-items:start;gap:10px;">
              <span style="font-weight:700;font-size:16.5px;color:#16181D;">{{ s.name }}</span>
              <span style="font-family:'Space Grotesk',sans-serif;font-weight:700;color:#5B4BE3;font-size:16px;">{{ s.priceCents / 100 | currency:'INR':'symbol':'1.0-0' }}</span>
            </div>
            <p style="font-size:13.5px;color:#8A909C;line-height:1.5;margin:8px 0 14px;">{{ s.description | slice:0:70 }}…</p>
            <div style="display:flex;align-items:center;justify-content:space-between;">
              <span style="font-size:13px;color:#8A909C;font-weight:600;">⏱ {{ s.durationMin }} {{ appStrings.COMMON.MIN_SUFFIX }}</span>
              <a [routerLink]="['/book']" [queryParams]="{serviceId: s.id}" style="text-decoration:none;background:#EEEBFD;color:#4A3BD0;font-weight:700;font-size:13.5px;padding:9px 18px;border-radius:10px;">{{ appStrings.COMMON.BOOK }}</a>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- How it works -->
    <div style="background:#F7F7FA;padding:64px 40px;">
      <div style="text-align:center;margin-bottom:44px;">
        <div style="font-size:12.5px;font-weight:700;letter-spacing:0.1em;color:#5B4BE3;margin-bottom:8px;">{{ appStrings.HOME.HOW_EYEBROW }}</div>
        <h2 style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:34px;letter-spacing:-0.02em;margin:0;color:#0F1115;">{{ appStrings.HOME.HOW_HEADING }}</h2>
      </div>
      <div style="display:grid;grid-template-columns:repeat(3,1fr);gap:22px;">
        <div *ngFor="let s of steps; let i = index" style="background:#fff;border:1px solid #ECECEF;border-radius:18px;padding:28px;">
          <div style="width:42px;height:42px;border-radius:12px;background:#5B4BE3;color:#fff;font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:18px;display:flex;align-items:center;justify-content:center;margin-bottom:18px;">{{ i + 1 }}</div>
          <div style="font-weight:700;font-size:17px;margin-bottom:8px;color:#16181D;">{{ s.title }}</div>
          <p style="font-size:14.5px;color:#8A909C;line-height:1.6;margin:0;">{{ s.desc }}</p>
        </div>
      </div>
    </div>

    <!-- Why BookEase -->
    <div style="padding:64px 40px;background:#fff;">
      <div style="margin-bottom:36px;max-width:600px;">
        <div style="font-size:12.5px;font-weight:700;letter-spacing:0.1em;color:#5B4BE3;margin-bottom:8px;">{{ appStrings.HOME.WHY_EYEBROW }}</div>
        <h2 style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:34px;letter-spacing:-0.02em;margin:0;color:#0F1115;">{{ appStrings.HOME.WHY_HEADING }}</h2>
      </div>
      <div style="display:grid;grid-template-columns:repeat(4,1fr);gap:20px;">
        <div *ngFor="let f of features">
          <div style="width:44px;height:44px;border-radius:12px;background:#EEEBFD;display:flex;align-items:center;justify-content:center;margin-bottom:16px;font-size:20px;">{{ f.icon }}</div>
          <div style="font-weight:700;font-size:16px;margin-bottom:7px;color:#16181D;">{{ f.title }}</div>
          <p style="font-size:14px;color:#8A909C;line-height:1.6;margin:0;">{{ f.desc }}</p>
        </div>
      </div>
    </div>

    <!-- Testimonials -->
    <div style="background:#F7F7FA;padding:64px 40px;">
      <div style="text-align:center;margin-bottom:40px;">
        <div style="font-size:12.5px;font-weight:700;letter-spacing:0.1em;color:#5B4BE3;margin-bottom:8px;">{{ appStrings.HOME.TESTIMONIALS_EYEBROW }}</div>
        <h2 style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:34px;letter-spacing:-0.02em;margin:0;color:#0F1115;">{{ appStrings.HOME.TESTIMONIALS_HEADING }}</h2>
      </div>
      <div style="display:grid;grid-template-columns:repeat(2,1fr);gap:22px;max-width:940px;margin:0 auto;">
        <div style="background:#fff;border:1px solid #ECECEF;border-radius:18px;padding:30px;">
          <div style="color:#C8A93C;font-size:15px;margin-bottom:14px;">★★★★★</div>
          <p style="font-size:16.5px;line-height:1.6;color:#16181D;margin:0 0 22px;font-weight:500;">{{ appStrings.HOME.TESTIMONIAL_1_BODY }}</p>
          <div style="display:flex;align-items:center;gap:12px;">
            <div style="width:44px;height:44px;border-radius:50%;background:#EEEBFD;display:flex;align-items:center;justify-content:center;font-size:16px;font-weight:700;color:#5B4BE3;">J</div>
            <div><div style="font-weight:700;font-size:14.5px;color:#16181D;">{{ appStrings.HOME.TESTIMONIAL_1_NAME }}</div><div style="font-size:13px;color:#8A909C;">{{ appStrings.HOME.TESTIMONIAL_1_SINCE }}</div></div>
          </div>
        </div>
        <div style="background:#fff;border:1px solid #ECECEF;border-radius:18px;padding:30px;">
          <div style="color:#C8A93C;font-size:15px;margin-bottom:14px;">★★★★★</div>
          <p style="font-size:16.5px;line-height:1.6;color:#16181D;margin:0 0 22px;font-weight:500;">{{ appStrings.HOME.TESTIMONIAL_2_BODY }}</p>
          <div style="display:flex;align-items:center;gap:12px;">
            <div style="width:44px;height:44px;border-radius:50%;background:#E7F8EE;display:flex;align-items:center;justify-content:center;font-size:16px;font-weight:700;color:#15803D;">M</div>
            <div><div style="font-weight:700;font-size:14.5px;color:#16181D;">{{ appStrings.HOME.TESTIMONIAL_2_NAME }}</div><div style="font-size:13px;color:#8A909C;">{{ appStrings.HOME.TESTIMONIAL_2_ROLE }}</div></div>
          </div>
        </div>
      </div>
    </div>

    <!-- Pricing -->
    <div style="padding:64px 40px;background:#fff;">
      <div style="text-align:center;margin-bottom:44px;">
        <div style="font-size:12.5px;font-weight:700;letter-spacing:0.1em;color:#5B4BE3;margin-bottom:8px;">{{ appStrings.HOME.PACKAGES_EYEBROW }}</div>
        <h2 style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:34px;letter-spacing:-0.02em;margin:0;color:#0F1115;">{{ appStrings.HOME.PACKAGES_HEADING }}</h2>
      </div>
      <div style="display:grid;grid-template-columns:repeat(3,1fr);gap:22px;max-width:1000px;margin:0 auto;">
        <div style="border:1px solid #ECECEF;border-radius:20px;padding:32px;">
          <div style="font-weight:700;font-size:15px;color:#8A909C;margin-bottom:14px;">{{ appStrings.PRICING.PLAN_STARTER_NAME }}</div>
          <div style="font-family:'Space Grotesk',sans-serif;font-size:44px;font-weight:700;letter-spacing:-0.02em;color:#0F1115;">{{ appStrings.PRICING.PLAN_STARTER_PRICE }}</div>
          <div style="font-size:13.5px;color:#8A909C;margin:4px 0 24px;">{{ appStrings.PRICING.PLAN_STARTER_BILLING }}</div>
          <div style="display:flex;flex-direction:column;gap:12px;font-size:14px;color:#545A66;margin-bottom:0;">
            <span *ngFor="let f of appStrings.PRICING.PLAN_STARTER_FEATURES">{{ f }}</span>
          </div>
          <button style="width:100%;margin-top:26px;font-family:inherit;cursor:pointer;background:#fff;border:1px solid #DDDFE6;color:#16181D;font-weight:700;font-size:14.5px;padding:13px;border-radius:12px;">{{ appStrings.PRICING.PLAN_STARTER_CTA }}</button>
        </div>
        <div style="border:2px solid #5B4BE3;border-radius:20px;padding:42px 32px 32px;position:relative;box-shadow:0 20px 44px rgba(91,75,227,0.18);">
          <span style="position:absolute;top:-13px;left:32px;background:#5B4BE3;color:#fff;font-size:12px;font-weight:700;padding:5px 12px;border-radius:999px;">{{ appStrings.PRICING.BADGE_POPULAR }}</span>
          <div style="font-weight:700;font-size:15px;color:#5B4BE3;margin-bottom:14px;">{{ appStrings.PRICING.PLAN_PRO_NAME }}</div>
          <div style="font-family:'Space Grotesk',sans-serif;font-size:44px;font-weight:700;letter-spacing:-0.02em;color:#0F1115;">₹49<span style="font-size:17px;color:#8A909C;font-weight:600;">{{ appStrings.COMMON.PER_MONTH }}</span></div>
          <div style="font-size:13.5px;color:#8A909C;margin:4px 0 24px;">{{ appStrings.PRICING.PLAN_PRO_BILLING }}</div>
          <div style="display:flex;flex-direction:column;gap:12px;font-size:14px;color:#545A66;">
            <span *ngFor="let f of appStrings.PRICING.PLAN_PRO_FEATURES">{{ f }}</span>
          </div>
          <button style="width:100%;margin-top:26px;font-family:inherit;cursor:pointer;border:none;background:#5B4BE3;color:#fff;font-weight:700;font-size:14.5px;padding:13px;border-radius:12px;box-shadow:0 8px 20px rgba(91,75,227,0.3);">{{ appStrings.PRICING.PLAN_PRO_CTA }}</button>
        </div>
        <div style="border:1px solid #ECECEF;border-radius:20px;padding:32px;">
          <div style="font-weight:700;font-size:15px;color:#8A909C;margin-bottom:14px;">{{ appStrings.PRICING.PLAN_ELITE_NAME }}</div>
          <div style="font-family:'Space Grotesk',sans-serif;font-size:44px;font-weight:700;letter-spacing:-0.02em;color:#0F1115;">₹99<span style="font-size:17px;color:#8A909C;font-weight:600;">{{ appStrings.COMMON.PER_MONTH }}</span></div>
          <div style="font-size:13.5px;color:#8A909C;margin:4px 0 24px;">{{ appStrings.PRICING.PLAN_ELITE_BILLING }}</div>
          <div style="display:flex;flex-direction:column;gap:12px;font-size:14px;color:#545A66;">
            <span *ngFor="let f of appStrings.PRICING.PLAN_ELITE_FEATURES">{{ f }}</span>
          </div>
          <button style="width:100%;margin-top:26px;font-family:inherit;cursor:pointer;background:#fff;border:1px solid #DDDFE6;color:#16181D;font-weight:700;font-size:14.5px;padding:13px;border-radius:12px;">{{ appStrings.PRICING.PLAN_ELITE_CTA }}</button>
        </div>
      </div>
    </div>

    <!-- Footer CTA -->
    <div style="background:linear-gradient(158deg,#4A3DAE,#2C2370);padding:64px 40px;color:#fff;">
      <div style="display:grid;grid-template-columns:1fr auto;gap:40px;align-items:center;max-width:1000px;margin:0 auto 56px;">
        <div>
          <h2 style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:38px;letter-spacing:-0.025em;margin:0 0 10px;">{{ appStrings.HOME.CTA_HEADING }}</h2>
          <p style="font-size:17px;color:#C7C2E8;margin:0;">{{ appStrings.HOME.CTA_SUBTEXT }}</p>
        </div>
        <a routerLink="/services" style="text-decoration:none;background:#C8F25C;color:#1B2906;font-weight:700;font-size:16px;padding:16px 32px;border-radius:13px;white-space:nowrap;">{{ appStrings.HOME.CTA_BUTTON }}</a>
      </div>
      <div style="border-top:1px solid rgba(255,255,255,0.14);padding-top:32px;display:flex;justify-content:space-between;align-items:center;max-width:1000px;margin:0 auto;flex-wrap:wrap;gap:16px;">
        <div style="display:flex;align-items:center;gap:10px;">
          <div style="width:26px;height:26px;border-radius:8px;background:rgba(255,255,255,0.12);display:flex;align-items:center;justify-content:center;"><svg width="13" height="13" viewBox="0 0 24 24" fill="none"><path d="M13 2L4 14h7l-1 8 10-13h-7l0-7z" fill="#C8F25C"/></svg></div>
          <span style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:16px;color:#fff;">{{ appStrings.BRAND.NAME }}</span>
        </div>
        <div style="display:flex;gap:28px;font-size:14px;color:#C7C2E8;font-weight:500;">
          <span *ngFor="let link of appStrings.BRAND.FOOTER_LINKS">{{ link }}</span>
        </div>
        <div style="font-size:13px;color:#9890C9;">{{ appStrings.BRAND.FOOTER_COPYRIGHT }}</div>
      </div>
    </div>
  `,
  styles: [`
    :host { display: block; background: #fff; }
    .svc-card { transition: box-shadow .2s, transform .2s; }
    .svc-card:hover { box-shadow: 0 16px 36px rgba(16,17,22,0.1); transform: translateY(-3px); }
  `],
})
export class LandingComponent implements OnInit {
  readonly appStrings = APP_STRINGS;

  services: Service[] = [];
  private svc = inject(ServicesService);

  readonly steps = APP_STRINGS.HOME.STEPS;
  readonly features = APP_STRINGS.HOME.FEATURES;

  ngOnInit() {
    this.svc.getServices().subscribe(s => this.services = s.slice(0, 3));
  }
}

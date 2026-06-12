import { Component, computed, inject, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { ReactiveFormsModule, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { ToastService } from '../../shared/services/toast.service';

@Component({
  selector: 'be-contact',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
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

    <!-- Two-column layout -->
    <div style="display:grid;grid-template-columns:1fr 1fr;min-height:calc(100vh - 69px);">

      <!-- Left: Form -->
      <div style="padding:56px 44px;">
        <div style="font-size:12.5px;font-weight:700;letter-spacing:0.1em;color:#5B4BE3;margin-bottom:12px;">GET IN TOUCH</div>
        <h1 style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:38px;letter-spacing:-0.025em;margin:0 0 14px;color:#0F1115;">We'd love to hear from you</h1>
        <p style="font-size:15.5px;color:#545A66;line-height:1.6;margin:0 0 32px;">Questions about a service, your booking, or partnering as a coach? Send us a note and we'll reply within a day.</p>

        <form [formGroup]="contactForm" (ngSubmit)="onSubmit()" style="display:flex;flex-direction:column;gap:18px;">
          <!-- Name row -->
          <div style="display:grid;grid-template-columns:1fr 1fr;gap:14px;">
            <div>
              <label style="display:block;font-size:13.5px;font-weight:600;color:#16181D;margin-bottom:6px;">First name</label>
              <input
                formControlName="firstName"
                type="text"
                [style.border]="fieldInvalid('firstName') ? '1px solid #E11D48' : '1px solid #DDDFE6'"
                style="width:100%;height:48px;padding:0 14px;border-radius:11px;font-family:inherit;font-size:14.5px;font-weight:500;color:#16181D;box-sizing:border-box;outline:none;" />
              <div *ngIf="fieldInvalid('firstName')" style="color:#BE123C;font-size:12px;margin-top:5px;font-weight:500;">
                <span *ngIf="ctrl('firstName').errors?.['required']">This field is required.</span>
                <span *ngIf="ctrl('firstName').errors?.['minlength']">Must be at least 2 characters.</span>
              </div>
            </div>
            <div>
              <label style="display:block;font-size:13.5px;font-weight:600;color:#16181D;margin-bottom:6px;">Last name</label>
              <input
                formControlName="lastName"
                type="text"
                [style.border]="fieldInvalid('lastName') ? '1px solid #E11D48' : '1px solid #DDDFE6'"
                style="width:100%;height:48px;padding:0 14px;border-radius:11px;font-family:inherit;font-size:14.5px;font-weight:500;color:#16181D;box-sizing:border-box;outline:none;" />
              <div *ngIf="fieldInvalid('lastName')" style="color:#BE123C;font-size:12px;margin-top:5px;font-weight:500;">
                <span *ngIf="ctrl('lastName').errors?.['required']">This field is required.</span>
                <span *ngIf="ctrl('lastName').errors?.['minlength']">Must be at least 2 characters.</span>
              </div>
            </div>
          </div>

          <!-- Email -->
          <div>
            <label style="display:block;font-size:13.5px;font-weight:600;color:#16181D;margin-bottom:6px;">Email</label>
            <input
              formControlName="email"
              type="email"
              [style.border]="fieldInvalid('email') ? '1px solid #E11D48' : '1px solid #DDDFE6'"
              style="width:100%;height:48px;padding:0 14px;border-radius:11px;font-family:inherit;font-size:14.5px;font-weight:500;color:#16181D;box-sizing:border-box;outline:none;" />
            <div *ngIf="fieldInvalid('email')" style="color:#BE123C;font-size:12px;margin-top:5px;font-weight:500;">
              <span *ngIf="ctrl('email').errors?.['required']">This field is required.</span>
              <span *ngIf="ctrl('email').errors?.['email']">Enter a valid email address.</span>
            </div>
          </div>

          <!-- Topic -->
          <div>
            <label style="display:block;font-size:13.5px;font-weight:600;color:#16181D;margin-bottom:6px;">Topic</label>
            <select
              formControlName="topic"
              [style.border]="fieldInvalid('topic') ? '1px solid #E11D48' : '1px solid #DDDFE6'"
              style="width:100%;height:48px;padding:0 14px;border-radius:11px;font-family:inherit;font-size:14.5px;font-weight:500;color:#16181D;box-sizing:border-box;outline:none;background:#fff;appearance:auto;">
              <option value="">Select a topic</option>
              <option value="booking">Booking help</option>
              <option value="coach">Partner as a coach</option>
              <option value="billing">Billing question</option>
              <option value="other">Other</option>
            </select>
            <div *ngIf="fieldInvalid('topic')" style="color:#BE123C;font-size:12px;margin-top:5px;font-weight:500;">
              This field is required.
            </div>
          </div>

          <!-- Message -->
          <div>
            <label style="display:block;font-size:13.5px;font-weight:600;color:#16181D;margin-bottom:6px;">Message</label>
            <textarea
              formControlName="message"
              placeholder="Tell us a bit about what you need…"
              [style.border]="fieldInvalid('message') ? '1px solid #E11D48' : '1px solid #DDDFE6'"
              style="width:100%;min-height:110px;padding:14px;border-radius:11px;font-family:inherit;font-size:14.5px;font-weight:500;color:#16181D;box-sizing:border-box;outline:none;resize:vertical;"></textarea>
            <div *ngIf="fieldInvalid('message')" style="color:#BE123C;font-size:12px;margin-top:5px;font-weight:500;">
              <span *ngIf="ctrl('message').errors?.['required']">This field is required.</span>
              <span *ngIf="ctrl('message').errors?.['minlength']">Message must be at least 10 characters.</span>
            </div>
          </div>

          <!-- Submit -->
          <button
            type="submit"
            [disabled]="contactForm.invalid"
            [style.opacity]="contactForm.invalid ? '0.6' : '1'"
            [style.cursor]="contactForm.invalid ? 'not-allowed' : 'pointer'"
            style="width:100%;font-family:inherit;border:none;background:#5B4BE3;color:#fff;font-weight:700;font-size:15.5px;padding:15px;border-radius:13px;box-shadow:0 8px 20px rgba(91,75,227,0.30);">
            Send message
          </button>
        </form>
      </div>

      <!-- Right: Info panel -->
      <div style="padding:56px 44px;background:#F7F7FA;border-left:1px solid #F1F1F4;display:flex;flex-direction:column;gap:16px;">

        <!-- Email card -->
        <div style="background:#fff;border:1px solid #ECECEF;border-radius:16px;padding:20px 22px;display:flex;align-items:center;gap:16px;">
          <div style="width:44px;height:44px;border-radius:11px;background:#EEEBFD;display:flex;align-items:center;justify-content:center;flex-shrink:0;">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
              <rect x="2" y="4" width="20" height="16" rx="3" stroke="#5B4BE3" stroke-width="1.8"/>
              <path d="M2 7l10 7 10-7" stroke="#5B4BE3" stroke-width="1.8" stroke-linecap="round"/>
            </svg>
          </div>
          <div>
            <div style="font-size:12.5px;color:#8A909C;font-weight:600;margin-bottom:3px;">Email us</div>
            <div style="font-size:15.5px;font-weight:700;color:#16181D;">hello&#64;bookease.app</div>
          </div>
        </div>

        <!-- Phone card -->
        <div style="background:#fff;border:1px solid #ECECEF;border-radius:16px;padding:20px 22px;display:flex;align-items:center;gap:16px;">
          <div style="width:44px;height:44px;border-radius:11px;background:#E7F8EE;display:flex;align-items:center;justify-content:center;flex-shrink:0;">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
              <path d="M6.6 10.8c1.4 2.8 3.8 5.1 6.6 6.6l2.2-2.2c.3-.3.7-.4 1-.2 1.1.4 2.3.6 3.6.6.6 0 1 .4 1 1V20c0 .6-.4 1-1 1C10.6 21 3 13.4 3 4c0-.6.4-1 1-1h3.5c.6 0 1 .4 1 1 0 1.3.2 2.5.6 3.6.1.3 0 .7-.2 1L6.6 10.8z" stroke="#16A34A" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
          <div>
            <div style="font-size:12.5px;color:#8A909C;font-weight:600;margin-bottom:3px;">Call us</div>
            <div style="font-size:15.5px;font-weight:700;color:#16181D;">+1 (415) 555-0100</div>
          </div>
        </div>

        <!-- Location card -->
        <div style="background:#fff;border:1px solid #ECECEF;border-radius:16px;padding:20px 22px;display:flex;align-items:center;gap:16px;">
          <div style="width:44px;height:44px;border-radius:11px;background:#EAEFFC;display:flex;align-items:center;justify-content:center;flex-shrink:0;">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
              <path d="M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7z" stroke="#2563EB" stroke-width="1.8"/>
              <circle cx="12" cy="9" r="2.5" stroke="#2563EB" stroke-width="1.8"/>
            </svg>
          </div>
          <div>
            <div style="font-size:12.5px;color:#8A909C;font-weight:600;margin-bottom:3px;">Visit the studio</div>
            <div style="font-size:15.5px;font-weight:700;color:#16181D;">540 Market St, San Francisco</div>
          </div>
        </div>

        <!-- Hours card -->
        <div style="background:#fff;border:1px solid #ECECEF;border-radius:16px;padding:20px 22px;">
          <div style="font-size:13.5px;font-weight:700;color:#16181D;margin-bottom:12px;">Opening hours</div>
          <div style="display:flex;flex-direction:column;gap:8px;">
            <div style="display:flex;justify-content:space-between;font-size:13.5px;">
              <span style="color:#545A66;">Mon &ndash; Fri</span>
              <span style="font-weight:600;color:#16181D;">6:00 AM &ndash; 9:00 PM</span>
            </div>
            <div style="display:flex;justify-content:space-between;font-size:13.5px;">
              <span style="color:#545A66;">Saturday</span>
              <span style="font-weight:600;color:#16181D;">8:00 AM &ndash; 6:00 PM</span>
            </div>
            <div style="display:flex;justify-content:space-between;font-size:13.5px;">
              <span style="color:#545A66;">Sunday</span>
              <span style="font-weight:600;color:#16181D;">Closed</span>
            </div>
          </div>
        </div>

        <!-- Map placeholder -->
        <div style="width:100%;height:150px;background:#EEEBFD;border-radius:16px;display:flex;align-items:center;justify-content:center;">
          <span style="font-size:14px;color:#8A909C;font-weight:500;">Map / studio photo</span>
        </div>

      </div>
    </div>
  `,
  styles: [`:host { display:block; background:#fff; }`],
})
export class ContactComponent {
  private router = inject(Router);
  private fb = inject(FormBuilder);
  private toastService = inject(ToastService);

  readonly activeRoute = computed(() => this.router.url.split('?')[0]);

  readonly contactForm = this.fb.nonNullable.group({
    firstName: ['Jordan', [Validators.required, Validators.minLength(2)]],
    lastName:  ['Hayes',  [Validators.required, Validators.minLength(2)]],
    email:     ['jordan@email.com', [Validators.required, Validators.email]],
    topic:     ['booking', Validators.required],
    message:   ['', [Validators.required, Validators.minLength(10)]],
  });

  ctrl(name: string): AbstractControl {
    return this.contactForm.get(name)!;
  }

  fieldInvalid(name: string): boolean {
    const c = this.ctrl(name);
    return c.invalid && c.touched;
  }

  onSubmit(): void {
    if (this.contactForm.valid) {
      this.toastService.show("Message sent! We'll be in touch within a day.", 'success');
      this.contactForm.reset({ topic: 'booking' });
    } else {
      this.contactForm.markAllAsTouched();
      this.toastService.show('Please fill in all required fields.', 'error');
    }
  }
}

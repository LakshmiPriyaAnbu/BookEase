import { Component, signal, computed, inject, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { ReactiveFormsModule, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { ToastService } from '../../shared/services/toast.service';
import { APP_STRINGS } from '../../core/constants';

@Component({
  selector: 'be-login',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <div style="display:grid;grid-template-columns:1fr 1fr;min-height:100vh;">

      <!-- Left brand panel -->
      <div style="background:linear-gradient(158deg,#4A3DAE,#2C2370);padding:56px 48px;color:#fff;display:flex;flex-direction:column;justify-content:space-between;">

        <!-- Logo -->
        <div style="display:flex;align-items:center;gap:10px;">
          <div style="width:32px;height:32px;border-radius:9px;background:rgba(255,255,255,0.14);display:flex;align-items:center;justify-content:center;">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none"><path d="M13 2L4 14h7l-1 8 10-13h-7l0-7z" fill="#C8F25C"/></svg>
          </div>
          <span style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:19px;letter-spacing:-0.02em;color:#fff;">{{ appStrings.BRAND.NAME }}</span>
        </div>

        <!-- Middle content -->
        <div>
          <h2 style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:34px;line-height:1.12;letter-spacing:-0.025em;margin:0 0 16px;color:#fff;">Your next session is<br>one tap away.</h2>
          <p style="font-size:16px;color:#C7C2E8;line-height:1.6;margin:0 0 28px;">{{ appStrings.AUTH.BRAND_SUBTEXT }}</p>
          <div style="display:flex;flex-direction:column;gap:12px;">
            <div *ngFor="let pt of proofPoints" style="display:flex;align-items:center;gap:12px;">
              <div style="width:28px;height:28px;border-radius:50%;background:rgba(200,242,92,0.18);display:flex;align-items:center;justify-content:center;flex-shrink:0;">
                <svg width="14" height="14" viewBox="0 0 24 24" fill="none">
                  <path d="M20 6L9 17l-5-5" stroke="#C8F25C" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
              </div>
              <span style="font-size:14.5px;color:#fff;font-weight:500;">{{ pt }}</span>
            </div>
          </div>
        </div>

        <!-- Bottom quote -->
        <div style="display:flex;align-items:flex-start;gap:12px;">
          <div style="width:42px;height:42px;border-radius:50%;background:rgba(255,255,255,0.16);display:flex;align-items:center;justify-content:center;font-size:16px;font-weight:700;color:#fff;flex-shrink:0;">J</div>
          <div>
            <div style="font-size:13.5px;color:#fff;font-weight:600;margin-bottom:4px;">{{ appStrings.AUTH.TESTIMONIAL_BODY }}</div>
            <div style="font-size:12.5px;color:#9890C9;">{{ appStrings.AUTH.TESTIMONIAL_AUTHOR }} &middot; {{ appStrings.AUTH.TESTIMONIAL_SINCE }}</div>
          </div>
        </div>

      </div>

      <!-- Right form panel -->
      <div style="padding:56px;background:#fff;display:flex;flex-direction:column;justify-content:center;align-items:center;">
        <div style="width:100%;max-width:360px;">
          <h1 style="font-family:'Space Grotesk',sans-serif;font-weight:700;font-size:30px;letter-spacing:-0.02em;color:#0F1115;margin:0 0 8px;">{{ appStrings.AUTH.WELCOME_HEADING }}</h1>
          <p style="font-size:15px;color:#545A66;margin:0 0 28px;">{{ appStrings.AUTH.WELCOME_SUBTEXT }}</p>

          <!-- Error summary banner -->
          <div
            *ngIf="showErrorBanner()"
            class="error-banner"
            role="alert"
            aria-live="assertive">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" aria-hidden="true" style="flex-shrink:0;">
              <circle cx="12" cy="12" r="9" stroke="#E11D48" stroke-width="2"/>
              <path d="M12 7v6" stroke="#E11D48" stroke-width="2" stroke-linecap="round"/>
              <circle cx="12" cy="16.3" r="1.2" fill="#E11D48"/>
            </svg>
            <span>Please fix the highlighted fields before continuing.</span>
          </div>

          <form [formGroup]="loginForm" (ngSubmit)="onSubmit()" style="display:flex;flex-direction:column;gap:16px;">

            <!-- Email -->
            <div>
              <label style="display:block;font-size:13.5px;font-weight:600;color:#16181D;margin-bottom:6px;">{{ appStrings.AUTH.LABEL_EMAIL }}</label>
              <div style="position:relative;">
                <input
                  formControlName="email"
                  type="email"
                  placeholder="your&#64;email.com"
                  [style.border]="fieldInvalid('email') ? '1.5px solid #E11D48' : '1px solid #DDDFE6'"
                  [style.boxShadow]="fieldInvalid('email') ? '0 0 0 3px rgba(225,29,72,0.1)' : emailFocused() ? '0 0 0 4px rgba(91,75,227,0.13)' : 'none'"
                  (focus)="emailFocused.set(true)"
                  (blur)="emailFocused.set(false)"
                  style="width:100%;height:50px;padding:0 44px 0 14px;border-radius:12px;font-family:inherit;font-size:14.5px;color:#16181D;box-sizing:border-box;outline:none;" />
                <!-- Error icon in trailing edge -->
                <svg *ngIf="fieldInvalid('email')" width="16" height="16" viewBox="0 0 24 24" fill="none"
                  aria-hidden="true"
                  style="position:absolute;right:13px;top:50%;transform:translateY(-50%);pointer-events:none;">
                  <circle cx="12" cy="12" r="9" stroke="#E11D48" stroke-width="2"/>
                  <path d="M12 7v6" stroke="#E11D48" stroke-width="2" stroke-linecap="round"/>
                  <circle cx="12" cy="16.3" r="1.2" fill="#E11D48"/>
                </svg>
              </div>
              <div *ngIf="fieldInvalid('email')" style="color:#BE123C;font-size:12px;margin-top:5px;font-weight:500;">
                {{ appStrings.AUTH.ERROR_EMAIL }}
              </div>
            </div>

            <!-- Password -->
            <div>
              <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:6px;">
                <label style="font-size:13.5px;font-weight:600;color:#16181D;">{{ appStrings.AUTH.LABEL_PASSWORD }}</label>
                <a href="#" style="font-size:12.5px;font-weight:700;color:#5B4BE3;text-decoration:none;">{{ appStrings.AUTH.FORGOT_PASSWORD }}</a>
              </div>
              <div style="position:relative;">
                <input
                  formControlName="password"
                  [type]="showPassword() ? 'text' : 'password'"
                  [style.border]="fieldInvalid('password') ? '1.5px solid #E11D48' : '1px solid #DDDFE6'"
                  [style.boxShadow]="fieldInvalid('password') ? '0 0 0 3px rgba(225,29,72,0.1)' : passwordFocused() ? '0 0 0 4px rgba(91,75,227,0.13)' : 'none'"
                  (focus)="passwordFocused.set(true)"
                  (blur)="passwordFocused.set(false)"
                  style="width:100%;height:50px;padding:0 80px 0 14px;border-radius:12px;font-family:inherit;font-size:14.5px;color:#16181D;box-sizing:border-box;outline:none;" />
                <!-- Error icon (shown when invalid, left of the eye toggle) -->
                <svg *ngIf="fieldInvalid('password')" width="16" height="16" viewBox="0 0 24 24" fill="none"
                  aria-hidden="true"
                  style="position:absolute;right:42px;top:50%;transform:translateY(-50%);pointer-events:none;">
                  <circle cx="12" cy="12" r="9" stroke="#E11D48" stroke-width="2"/>
                  <path d="M12 7v6" stroke="#E11D48" stroke-width="2" stroke-linecap="round"/>
                  <circle cx="12" cy="16.3" r="1.2" fill="#E11D48"/>
                </svg>
                <!-- Eye toggle -->
                <button
                  type="button"
                  (click)="togglePassword()"
                  [attr.aria-label]="S.AUTH.TOGGLE_PASSWORD_ARIA"
                  style="position:absolute;right:12px;top:50%;transform:translateY(-50%);border:none;background:none;cursor:pointer;padding:4px;display:flex;align-items:center;">
                  <svg *ngIf="!showPassword()" width="18" height="18" viewBox="0 0 24 24" fill="none">
                    <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z" stroke="#8A909C" stroke-width="1.8"/>
                    <circle cx="12" cy="12" r="3" stroke="#8A909C" stroke-width="1.8"/>
                  </svg>
                  <svg *ngIf="showPassword()" width="18" height="18" viewBox="0 0 24 24" fill="none">
                    <path d="M17.94 17.94A10.07 10.07 0 0112 20c-7 0-11-8-11-8a18.45 18.45 0 015.06-5.94M9.9 4.24A9.12 9.12 0 0112 4c7 0 11 8 11 8a18.5 18.5 0 01-2.16 3.19m-6.72-1.07a3 3 0 11-4.24-4.24" stroke="#8A909C" stroke-width="1.8" stroke-linecap="round"/>
                    <line x1="1" y1="1" x2="23" y2="23" stroke="#8A909C" stroke-width="1.8" stroke-linecap="round"/>
                  </svg>
                </button>
              </div>
              <div *ngIf="fieldInvalid('password')" style="color:#BE123C;font-size:12px;margin-top:5px;font-weight:500;">
                {{ appStrings.AUTH.ERROR_PASSWORD }}
              </div>
            </div>

            <!-- Remember me -->
            <div style="display:flex;align-items:center;gap:10px;">
              <div
                (click)="toggleRemember()"
                style="width:18px;height:18px;border-radius:5px;cursor:pointer;display:flex;align-items:center;justify-content:center;flex-shrink:0;"
                [style.background]="rememberMe() ? '#5B4BE3' : '#fff'"
                [style.border]="rememberMe() ? 'none' : '1.5px solid #DDDFE6'">
                <svg *ngIf="rememberMe()" width="11" height="11" viewBox="0 0 24 24" fill="none">
                  <path d="M20 6L9 17l-5-5" stroke="#fff" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
              </div>
              <span style="font-size:13.5px;color:#545A66;cursor:pointer;" (click)="toggleRemember()">{{ appStrings.AUTH.REMEMBER_ME }}</span>
            </div>

            <!-- Log in button -->
            <button
              type="submit"
              [style.opacity]="loginForm.invalid && _submitted() ? '0.7' : '1'"
              style="width:100%;font-family:inherit;border:none;background:#5B4BE3;color:#fff;font-weight:700;font-size:15.5px;padding:15px;border-radius:13px;box-shadow:0 8px 20px rgba(91,75,227,0.30);margin-top:2px;cursor:pointer;">
              {{ appStrings.AUTH.LOGIN_BUTTON }}
            </button>

          </form>

          <!-- OR divider -->
          <div style="display:flex;align-items:center;gap:12px;margin:20px 0;">
            <div style="flex:1;height:1px;background:#ECECEF;"></div>
            <span style="font-size:12.5px;color:#B6BBC4;font-weight:500;">{{ appStrings.AUTH.OR_DIVIDER }}</span>
            <div style="flex:1;height:1px;background:#ECECEF;"></div>
          </div>

          <!-- Social buttons -->
          <div style="display:flex;gap:10px;">
            <button style="flex:1;display:flex;align-items:center;justify-content:center;gap:8px;font-family:inherit;cursor:pointer;background:#fff;border:1px solid #DDDFE6;border-radius:12px;padding:12px;font-size:14px;font-weight:700;color:#16181D;">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none">
                <path d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z" fill="#4285F4"/>
                <path d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z" fill="#34A853"/>
                <path d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l3.66-2.84z" fill="#FBBC05"/>
                <path d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z" fill="#EA4335"/>
              </svg>
              {{ appStrings.AUTH.GOOGLE_BUTTON }}
            </button>
            <button style="flex:1;display:flex;align-items:center;justify-content:center;gap:8px;font-family:inherit;cursor:pointer;background:#fff;border:1px solid #DDDFE6;border-radius:12px;padding:12px;font-size:14px;font-weight:700;color:#16181D;">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none">
                <path d="M18.71 19.5c-.83 1.24-1.71 2.45-3.05 2.47-1.34.03-1.77-.79-3.29-.79-1.53 0-2 .77-3.27.82-1.31.05-2.3-1.32-3.14-2.53C4.25 17 2.94 12.45 4.7 9.39c.87-1.52 2.43-2.48 4.12-2.51 1.28-.02 2.5.87 3.29.87.78 0 2.26-1.07 3.8-.91.65.03 2.47.26 3.64 1.98-.09.06-2.17 1.28-2.15 3.81.03 3.02 2.65 4.03 2.68 4.04-.03.07-.42 1.44-1.38 2.83M13 3.5c.73-.83 1.94-1.46 2.94-1.5.13 1.17-.34 2.35-1.04 3.19-.69.85-1.83 1.51-2.95 1.42-.15-1.15.41-2.35 1.05-3.11z" fill="#000"/>
              </svg>
              {{ appStrings.AUTH.APPLE_BUTTON }}
            </button>
          </div>

          <!-- Sign up link -->
          <p style="text-align:center;font-size:14px;color:#545A66;margin:20px 0 0;">
            {{ appStrings.AUTH.NO_ACCOUNT }}&nbsp;
            <a routerLink="/book" style="color:#5B4BE3;font-weight:700;text-decoration:none;">{{ appStrings.AUTH.SIGN_UP_LINK }}</a>
          </p>

        </div>
      </div>

    </div>
  `,
  styles: [`
    :host { display: block; }

    .error-banner {
      display: flex;
      align-items: center;
      gap: 10px;
      background: #FCE4E9;
      border: 1px solid #F6C6D1;
      border-radius: 12px;
      padding: 13px 15px;
      margin-bottom: 16px;
      font-size: 13.5px;
      font-weight: 600;
      color: #BE123C;
      line-height: 1.4;
    }
  `],
})
export class LoginComponent {
  readonly appStrings = APP_STRINGS;
  protected readonly S = APP_STRINGS;

  private fb = inject(FormBuilder);
  private router = inject(Router);
  private toastService = inject(ToastService);

  readonly showPassword = signal(false);
  readonly rememberMe = signal(true);
  readonly emailFocused = signal(false);
  readonly passwordFocused = signal(false);
  readonly _submitted = signal(false);

  readonly loginForm = this.fb.nonNullable.group({
    email:    ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8)]],
  });

  readonly showErrorBanner = computed(() => this.loginForm.invalid && this._submitted());

  readonly proofPoints = APP_STRINGS.AUTH.PROOF_POINTS;

  ctrl(name: string): AbstractControl {
    return this.loginForm.get(name)!;
  }

  fieldInvalid(name: string): boolean {
    const c = this.ctrl(name);
    return c.invalid && c.touched;
  }

  togglePassword(): void {
    this.showPassword.update(v => !v);
  }

  toggleRemember(): void {
    this.rememberMe.update(v => !v);
  }

  onSubmit(): void {
    this._submitted.set(true);
    if (this.loginForm.valid) {
      this.toastService.show(APP_STRINGS.AUTH.TOAST_WELCOME, 'success');
      this.router.navigate(['/services']);
    } else {
      this.loginForm.markAllAsTouched();
      this.toastService.show(APP_STRINGS.AUTH.TOAST_CHECK_CREDENTIALS, 'error');
    }
  }
}

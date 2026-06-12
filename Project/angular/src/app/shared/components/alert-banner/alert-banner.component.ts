import { Component, Input, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';

type AlertType = 'success' | 'error' | 'info' | 'warning';

@Component({
  selector: 'be-alert-banner',
  standalone: true,
  imports: [CommonModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <div class="alert" [ngClass]="'alert--' + type" role="alert">
      <!-- Icon -->
      <span class="alert__icon" aria-hidden="true">
        <!-- success -->
        <svg *ngIf="type === 'success'" width="19" height="19" viewBox="0 0 24 24" fill="none">
          <circle cx="12" cy="12" r="9" stroke="#16A34A" stroke-width="2"/>
          <path d="M8 12l3 3 5-5" stroke="#16A34A" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
        <!-- error -->
        <svg *ngIf="type === 'error'" width="19" height="19" viewBox="0 0 24 24" fill="none">
          <circle cx="12" cy="12" r="9" stroke="#E11D48" stroke-width="2"/>
          <path d="M12 7v6" stroke="#E11D48" stroke-width="2" stroke-linecap="round"/>
          <circle cx="12" cy="16.3" r="1.2" fill="#E11D48"/>
        </svg>
        <!-- info -->
        <svg *ngIf="type === 'info'" width="19" height="19" viewBox="0 0 24 24" fill="none">
          <circle cx="12" cy="12" r="9" stroke="#2563EB" stroke-width="2"/>
          <path d="M12 11v5" stroke="#2563EB" stroke-width="2" stroke-linecap="round"/>
          <circle cx="12" cy="8" r="1.2" fill="#2563EB"/>
        </svg>
        <!-- warning -->
        <svg *ngIf="type === 'warning'" width="19" height="19" viewBox="0 0 24 24" fill="none">
          <path d="M10.3 3.9L2.4 18a2 2 0 001.7 3h15.8a2 2 0 001.7-3L13.7 3.9a2 2 0 00-3.4 0z"
            stroke="#D97706" stroke-width="2" stroke-linejoin="round"/>
          <path d="M12 9v4" stroke="#D97706" stroke-width="2" stroke-linecap="round"/>
          <circle cx="12" cy="16.5" r="1.2" fill="#D97706"/>
        </svg>
      </span>

      <div class="alert__body">
        <p class="alert__title">{{ title }}</p>
        <p class="alert__message" *ngIf="message">{{ message }}</p>
      </div>
    </div>
  `,
  styles: [`
    :host { display: block; }

    .alert {
      display: flex;
      align-items: flex-start;
      gap: 12px;
      border-radius: 12px;
      padding: 15px 17px;
      border-width: 1px;
      border-style: solid;
    }

    .alert--error   { background: #FCE4E9; border-color: #F6C6D1; }
    .alert--success { background: #E7F8EE; border-color: #BBE9CD; }
    .alert--info    { background: #EAEFFC; border-color: #C5D6F7; }
    .alert--warning { background: #FEF3D9; border-color: #F5DCA0; }

    .alert__icon {
      flex-shrink: 0;
      display: flex;
      align-items: center;
      margin-top: 1px;
    }

    .alert__body {
      display: flex;
      flex-direction: column;
      gap: 3px;
    }

    .alert__title {
      font-size: 14px;
      font-weight: 700;
      margin: 0;
      line-height: 1.4;
    }

    .alert--error   .alert__title { color: #881337; }
    .alert--success .alert__title { color: #14532D; }
    .alert--info    .alert__title { color: #1E3A8A; }
    .alert--warning .alert__title { color: #78350F; }

    .alert__message {
      font-size: 13px;
      margin: 0;
      line-height: 1.5;
    }

    .alert--error   .alert__message { color: #BE123C; }
    .alert--success .alert__message { color: #166534; }
    .alert--info    .alert__message { color: #1D4ED8; }
    .alert--warning .alert__message { color: #92400E; }
  `],
})
export class AlertBannerComponent {
  @Input() type: AlertType = 'info';
  @Input() title!: string;
  @Input() message?: string;
}

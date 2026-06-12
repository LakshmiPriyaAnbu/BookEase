import { Component, Input, Output, EventEmitter, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'be-error-state',
  standalone: true,
  imports: [CommonModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <div class="error-state">
      <div class="error-state__icon-wrap" aria-hidden="true">
        <svg width="36" height="36" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M12 8v5" stroke="#E11D48" stroke-width="2.4" stroke-linecap="round"/>
          <circle cx="12" cy="16.5" r="1.4" fill="#E11D48"/>
          <path d="M10.3 3.9L2.4 18a2 2 0 001.7 3h15.8a2 2 0 001.7-3L13.7 3.9a2 2 0 00-3.4 0z"
            stroke="#E11D48" stroke-width="2" stroke-linejoin="round"/>
        </svg>
      </div>

      <h1 class="error-state__title">{{ title }}</h1>
      <p class="error-state__message">{{ message }}</p>

      <div class="error-state__actions">
        <button
          type="button"
          class="error-state__retry"
          (click)="retry.emit()"
          aria-label="Try again">
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" aria-hidden="true">
            <path d="M1 4v6h6" stroke="#fff" stroke-width="2.2" stroke-linecap="round" stroke-linejoin="round"/>
            <path d="M3.51 15a9 9 0 102.63-9.36L1 10" stroke="#fff" stroke-width="2.2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          Try again
        </button>
        <a href="mailto:support@bookease.app" class="error-state__support">Contact support</a>
      </div>

      <p class="error-state__code" *ngIf="errorCode !== null">Error code: {{ errorCode }}</p>
    </div>
  `,
  styles: [`
    :host { display: block; }

    .error-state {
      display: flex;
      flex-direction: column;
      align-items: center;
      text-align: center;
      padding: 88px 40px;
      background: #FBFBFC;
    }

    .error-state__icon-wrap {
      width: 84px;
      height: 84px;
      border-radius: 50%;
      background: #FCE4E9;
      display: flex;
      align-items: center;
      justify-content: center;
      margin-bottom: 28px;
      flex-shrink: 0;
    }

    .error-state__title {
      font-family: 'Space Grotesk', sans-serif;
      font-size: 28px;
      font-weight: 700;
      color: #0F1115;
      margin: 0 0 12px;
      letter-spacing: -0.02em;
    }

    .error-state__message {
      font-size: 16px;
      color: #545A66;
      line-height: 1.6;
      max-width: 44ch;
      margin: 0 0 32px;
    }

    .error-state__actions {
      display: flex;
      align-items: center;
      gap: 20px;
      flex-wrap: wrap;
      justify-content: center;
      margin-bottom: 20px;
    }

    .error-state__retry {
      display: inline-flex;
      align-items: center;
      gap: 8px;
      background: #5B4BE3;
      color: #fff;
      font-family: inherit;
      font-size: 15px;
      font-weight: 700;
      padding: 12px 22px;
      border-radius: 13px;
      border: none;
      cursor: pointer;
      box-shadow: 0 8px 22px rgba(91, 75, 227, 0.3);
      transition: opacity 0.15s;

      &:hover { opacity: 0.88; }
    }

    .error-state__support {
      font-size: 14.5px;
      font-weight: 600;
      color: #5B4BE3;
      text-decoration: none;
      transition: opacity 0.15s;

      &:hover { opacity: 0.75; }
    }

    .error-state__code {
      font-size: 12.5px;
      color: #B6BBC4;
      font-family: 'Courier New', Courier, monospace;
      margin: 0;
    }
  `],
})
export class ErrorStateComponent {
  @Input() title = 'Something went wrong';
  @Input() message = 'We hit a snag. Please try again.';
  @Input() errorCode: string | null = null;
  @Output() readonly retry = new EventEmitter<void>();
}

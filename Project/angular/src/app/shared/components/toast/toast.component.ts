import { Component, inject, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ToastService, Toast } from '../../services/toast.service';

@Component({
  selector: 'be-toast',
  standalone: true,
  imports: [CommonModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <div class="toast-container" aria-live="polite" aria-atomic="false">
      <div
        *ngFor="let toast of toastService.toasts(); trackBy: trackById"
        class="toast-item toast-enter"
        [class.toast--success]="toast.type === 'success'"
        [class.toast--error]="toast.type === 'error'"
        [class.toast--info]="toast.type === 'info'"
        [class.toast--warning]="toast.type === 'warning'"
        role="alert">
        <!-- Icon -->
        <div class="toast__icon" [class.toast__icon--success]="toast.type === 'success'"
             [class.toast__icon--error]="toast.type === 'error'"
             [class.toast__icon--info]="toast.type === 'info'"
             [class.toast__icon--warning]="toast.type === 'warning'">
          <!-- success checkmark -->
          <svg *ngIf="toast.type === 'success'" width="12" height="12" viewBox="0 0 24 24" fill="none">
            <path d="M20 6L9 17l-5-5" stroke="#16A34A" stroke-width="2.8" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          <!-- error × -->
          <svg *ngIf="toast.type === 'error'" width="12" height="12" viewBox="0 0 24 24" fill="none">
            <path d="M18 6L6 18M6 6l12 12" stroke="#E11D48" stroke-width="2.8" stroke-linecap="round"/>
          </svg>
          <!-- info ℹ -->
          <svg *ngIf="toast.type === 'info'" width="12" height="12" viewBox="0 0 24 24" fill="none">
            <path d="M12 16v-4M12 8h.01" stroke="#2563EB" stroke-width="2.5" stroke-linecap="round"/>
          </svg>
          <!-- warning ⚠ -->
          <svg *ngIf="toast.type === 'warning'" width="12" height="12" viewBox="0 0 24 24" fill="none">
            <path d="M12 9v4M12 17h.01M10.29 3.86L1.82 18a2 2 0 001.71 3h16.94a2 2 0 001.71-3L13.71 3.86a2 2 0 00-3.42 0z"
              stroke="#D97706" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </div>

        <!-- Message -->
        <span class="toast__message"
          [class.toast__message--success]="toast.type === 'success'"
          [class.toast__message--error]="toast.type === 'error'"
          [class.toast__message--info]="toast.type === 'info'"
          [class.toast__message--warning]="toast.type === 'warning'">
          {{ toast.message }}
        </span>

        <!-- Dismiss -->
        <button
          class="toast__dismiss"
          type="button"
          (click)="toastService.dismiss(toast.id)"
          aria-label="Dismiss notification">
          <svg width="14" height="14" viewBox="0 0 24 24" fill="none">
            <path d="M18 6L6 18M6 6l12 12" stroke="#8A909C" stroke-width="2" stroke-linecap="round"/>
          </svg>
        </button>
      </div>
    </div>
  `,
  styles: [`
    .toast-container {
      position: fixed;
      bottom: 28px;
      right: 28px;
      z-index: 9000;
      display: flex;
      flex-direction: column;
      gap: 10px;
      pointer-events: none;
    }

    .toast-item {
      pointer-events: auto;
      display: flex;
      align-items: center;
      gap: 12px;
      min-width: 280px;
      max-width: 380px;
      border-radius: 12px;
      padding: 14px 18px;
      box-shadow: 0 8px 20px rgba(16, 17, 22, 0.12);
      border-left-width: 4px;
      border-left-style: solid;
    }

    .toast--success {
      background: #E7F8EE;
      border-left-color: #16A34A;
    }

    .toast--error {
      background: #FCE4E9;
      border-left-color: #E11D48;
    }

    .toast--info {
      background: #EAEFFC;
      border-left-color: #2563EB;
    }

    .toast--warning {
      background: #FEF3D9;
      border-left-color: #D97706;
    }

    .toast__icon {
      width: 20px;
      height: 20px;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      flex-shrink: 0;
    }

    .toast__icon--success { background: rgba(22, 163, 74, 0.15); }
    .toast__icon--error   { background: rgba(225, 29, 72, 0.15); }
    .toast__icon--info    { background: rgba(37, 99, 235, 0.15); }
    .toast__icon--warning { background: rgba(217, 119, 6, 0.15); }

    .toast__message {
      flex: 1;
      font-size: 14px;
      font-weight: 600;
      line-height: 1.4;
    }

    .toast__message--success { color: #14532D; }
    .toast__message--error   { color: #881337; }
    .toast__message--info    { color: #1E3A8A; }
    .toast__message--warning { color: #78350F; }

    .toast__dismiss {
      margin-left: auto;
      flex-shrink: 0;
      border: none;
      background: none;
      cursor: pointer;
      padding: 2px;
      display: flex;
      align-items: center;
      justify-content: center;
      border-radius: 4px;
      color: #8A909C;
    }

    .toast__dismiss:hover {
      background: rgba(0, 0, 0, 0.06);
    }

    @keyframes toastIn {
      from { transform: translateX(110%); opacity: 0; }
      to   { transform: translateX(0);    opacity: 1; }
    }

    @keyframes toastOut {
      from { transform: translateX(0);    opacity: 1; }
      to   { transform: translateX(110%); opacity: 0; }
    }

    .toast-enter {
      animation: toastIn 0.22s cubic-bezier(0.4, 0, 0.2, 1) forwards;
    }
  `],
})
export class ToastComponent {
  readonly toastService = inject(ToastService);

  trackById(_index: number, toast: Toast): number {
    return toast.id;
  }
}

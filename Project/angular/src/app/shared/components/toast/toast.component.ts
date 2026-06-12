import { Component, inject, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ToastService, Toast, ToastType } from '../../services/toast.service';

function typeLabel(type: ToastType): string {
  const map: Record<ToastType, string> = {
    success: 'Success',
    error: 'Error',
    info: 'Info',
    warning: 'Warning',
  };
  return map[type];
}

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

        <!-- 32×32 icon circle -->
        <div class="toast__icon-circle"
          [class.toast__icon-circle--success]="toast.type === 'success'"
          [class.toast__icon-circle--error]="toast.type === 'error'"
          [class.toast__icon-circle--info]="toast.type === 'info'"
          [class.toast__icon-circle--warning]="toast.type === 'warning'"
          aria-hidden="true">
          <svg *ngIf="toast.type === 'success'" width="15" height="15" viewBox="0 0 24 24" fill="none">
            <path d="M20 6L9 17l-5-5" stroke="#16A34A" stroke-width="2.8" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          <svg *ngIf="toast.type === 'error'" width="15" height="15" viewBox="0 0 24 24" fill="none">
            <path d="M18 6L6 18M6 6l12 12" stroke="#E11D48" stroke-width="2.8" stroke-linecap="round"/>
          </svg>
          <svg *ngIf="toast.type === 'info'" width="15" height="15" viewBox="0 0 24 24" fill="none">
            <path d="M12 16v-4M12 8h.01" stroke="#2563EB" stroke-width="2.5" stroke-linecap="round"/>
          </svg>
          <svg *ngIf="toast.type === 'warning'" width="15" height="15" viewBox="0 0 24 24" fill="none">
            <path d="M12 9v4M12 17h.01M10.29 3.86L1.82 18a2 2 0 001.71 3h16.94a2 2 0 001.71-3L13.71 3.86a2 2 0 00-3.42 0z"
              stroke="#D97706" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </div>

        <!-- Text block: title + subtitle -->
        <div class="toast__text">
          <span class="toast__title">{{ toast.title ?? typeLabel(toast.type) }}</span>
          <span class="toast__subtitle">{{ toast.message }}</span>
        </div>

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
      min-width: 300px;
      max-width: 400px;
      background: #fff;
      border: 1px solid #ECECEF;
      border-radius: 12px;
      padding: 14px 16px;
      box-shadow: 0 12px 30px rgba(16, 17, 22, 0.1);
      border-left-width: 4px;
      border-left-style: solid;
    }

    .toast--success { border-left-color: #16A34A; }
    .toast--error   { border-left-color: #E11D48; }
    .toast--info    { border-left-color: #2563EB; }
    .toast--warning { border-left-color: #D97706; }

    /* 32×32 icon circle */
    .toast__icon-circle {
      width: 32px;
      height: 32px;
      min-width: 32px;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      flex-shrink: 0;
    }

    .toast__icon-circle--success { background: rgba(22, 163, 74, 0.12); }
    .toast__icon-circle--error   { background: rgba(225, 29, 72, 0.12); }
    .toast__icon-circle--info    { background: rgba(37, 99, 235, 0.12); }
    .toast__icon-circle--warning { background: rgba(217, 119, 6, 0.12); }

    /* Text */
    .toast__text {
      flex: 1;
      display: flex;
      flex-direction: column;
      gap: 2px;
      min-width: 0;
    }

    .toast__title {
      font-size: 14px;
      font-weight: 700;
      color: #16181D;
      line-height: 1.3;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }

    .toast__subtitle {
      font-size: 13px;
      color: #8A909C;
      line-height: 1.4;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }

    /* Dismiss button */
    .toast__dismiss {
      margin-left: 4px;
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
      transition: background 0.12s;

      &:hover { background: rgba(0, 0, 0, 0.06); }
    }

    @keyframes toastIn {
      from { transform: translateX(110%); opacity: 0; }
      to   { transform: translateX(0);    opacity: 1; }
    }

    .toast-enter {
      animation: toastIn 0.22s cubic-bezier(0.4, 0, 0.2, 1) forwards;
    }
  `],
})
export class ToastComponent {
  readonly toastService = inject(ToastService);
  readonly typeLabel = typeLabel;

  trackById(_index: number, toast: Toast): number {
    return toast.id;
  }
}

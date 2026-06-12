import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

type Variant = 'primary' | 'secondary' | 'danger' | 'ghost';
type Size    = 'sm' | 'md' | 'lg';

@Component({
  selector: 'be-button',
  standalone: true,
  imports: [CommonModule],
  template: `
    <button
      [class]="'be-btn be-btn--' + variant + ' be-btn--' + size"
      [disabled]="disabled || loading"
      (click)="clicked.emit($event)">
      <span *ngIf="loading" class="be-btn__spinner"></span>
      <ng-content />
    </button>
  `,
  styles: [`
    @use 'tokens' as *;
    .be-btn {
      display: inline-flex; align-items: center; justify-content: center; gap: 8px;
      font-family: $font-ui; font-weight: 700; border: none; cursor: pointer;
      border-radius: $radius-md; transition: all .15s ease; white-space: nowrap;
      &:disabled { opacity: .55; cursor: not-allowed; }
    }
    .be-btn--primary {
      background: $primary; color: #fff; box-shadow: $shadow-btn;
      &:hover:not(:disabled) { background: $primary-700; }
    }
    .be-btn--secondary {
      background: $surface-200; color: $ink-700; border: 1px solid $border-200;
      &:hover:not(:disabled) { background: $border-200; }
    }
    .be-btn--danger {
      background: #fff; color: $danger; border: 1px solid $border-200;
      &:hover:not(:disabled) { background: $danger-bg; }
    }
    .be-btn--ghost {
      background: transparent; color: $primary;
      &:hover:not(:disabled) { background: $primary-soft; }
    }
    .be-btn--sm { padding: 6px 14px; font-size: 13.5px; }
    .be-btn--md { padding: 12px 20px; font-size: 15px; height: 46px; }
    .be-btn--lg { padding: 14px 28px; font-size: 16px; height: 54px; }
    .be-btn__spinner {
      width: 16px; height: 16px; border: 2px solid rgba(255,255,255,.4);
      border-top-color: #fff; border-radius: 50%;
      animation: spin .7s linear infinite;
    }
    @keyframes spin { to { transform: rotate(360deg); } }
  `],
})
export class ButtonComponent {
  @Input() variant: Variant = 'primary';
  @Input() size: Size = 'md';
  @Input() disabled = false;
  @Input() loading = false;
  @Output() clicked = new EventEmitter<MouseEvent>();
}

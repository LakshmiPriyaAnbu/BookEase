import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'be-stat-card',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="stat">
      <div class="stat__icon" [style.background]="iconBg">{{ icon }}</div>
      <div class="stat__info">
        <p class="stat__label">{{ label }}</p>
        <p class="stat__value">{{ value }}</p>
      </div>
    </div>`,
  styles: [`
    @use 'tokens' as *;
    .stat {
      background: $surface-0; border: 1px solid $border-100;
      border-radius: $radius-xl; padding: 20px; display: flex; gap: 16px; align-items: center;
    }
    .stat__icon {
      width: 48px; height: 48px; border-radius: $radius-lg;
      display: flex; align-items: center; justify-content: center; font-size: 22px; flex-shrink: 0;
    }
    .stat__label { font-size: 13px; color: $muted-500; font-weight: 500; margin-bottom: 4px; }
    .stat__value { font-family: $font-display; font-size: 26px; font-weight: 700; color: $ink-800; }
  `],
})
export class StatCardComponent {
  @Input() icon = '📊';
  @Input() label = '';
  @Input() value = '';
  @Input() iconBg = '#EEEBFD';
}

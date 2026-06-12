import { Component, Input } from '@angular/core';
import { BookingStatus } from '../../models/models';

@Component({
  selector: 'be-status-badge',
  standalone: true,
  template: `<span class="badge badge--{{status}}">{{ label }}</span>`,
  styles: [`
    @use 'tokens' as *;
    .badge {
      display: inline-flex; align-items: center;
      padding: 4px 12px; border-radius: $radius-pill;
      font-size: 12.5px; font-weight: 600;
    }
    .badge--confirmed  { background: $success-bg;  color: $success-text; }
    .badge--pending    { background: $warning-bg;  color: $warning-text; }
    .badge--cancelled  { background: $danger-bg;   color: $danger-text;  }
    .badge--completed  { background: $surface-200; color: $text-600;     }
  `],
})
export class StatusBadgeComponent {
  @Input() status: BookingStatus = 'pending';
  get label(): string {
    return this.status.charAt(0).toUpperCase() + this.status.slice(1);
  }
}

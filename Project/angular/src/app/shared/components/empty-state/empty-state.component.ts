import { Component, Input } from '@angular/core';

@Component({
  selector: 'be-empty-state',
  standalone: true,
  template: `
    <div class="empty">
      <div class="empty__icon">{{ icon }}</div>
      <h3 class="empty__title">{{ title }}</h3>
      <p class="empty__message">{{ message }}</p>
    </div>`,
  styles: [`
    @use 'tokens' as *;
    .empty { text-align: center; padding: 64px 24px; }
    .empty__icon { font-size: 48px; margin-bottom: 16px; }
    .empty__title { font-size: 20px; font-weight: 800; color: $ink-700; margin-bottom: 8px; }
    .empty__message { font-size: 14.5px; color: $muted-500; }
  `],
})
export class EmptyStateComponent {
  @Input() icon = '🔍';
  @Input() title = 'Nothing here yet';
  @Input() message = 'Try adjusting your filters.';
}

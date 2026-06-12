import { Component, Input, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

export type EmptyStateVariant = 'default' | 'bookings';

@Component({
  selector: 'be-empty-state',
  standalone: true,
  imports: [CommonModule, RouterModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <div class="empty">

      <!-- Bookings variant: calendar-plus SVG in rounded square -->
      <div *ngIf="variant === 'bookings'" class="empty__icon-box empty__icon-box--bookings" aria-hidden="true">
        <svg width="44" height="44" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
          <rect x="3" y="5" width="18" height="16" rx="3" stroke="#5B4BE3" stroke-width="1.8"/>
          <path d="M3 9h18M8 3v4M16 3v4" stroke="#5B4BE3" stroke-width="1.8" stroke-linecap="round"/>
          <path d="M12 13v4M10 15h4" stroke="#5B4BE3" stroke-width="1.8" stroke-linecap="round"/>
        </svg>
      </div>

      <!-- Default variant: emoji icon fallback -->
      <div *ngIf="variant === 'default'" class="empty__icon" aria-hidden="true">{{ icon }}</div>

      <h3 class="empty__title">{{ resolvedTitle }}</h3>
      <p class="empty__message">{{ resolvedMessage }}</p>

      <a *ngIf="variant === 'bookings'" routerLink="/services" class="empty__cta">Browse services</a>
    </div>
  `,
  styles: [`
    :host { display: block; }

    .empty {
      text-align: center;
      padding: 64px 24px;
      display: flex;
      flex-direction: column;
      align-items: center;
    }

    /* Bookings icon: 96×96 rounded square */
    .empty__icon-box {
      width: 96px;
      height: 96px;
      border-radius: 24px;
      display: flex;
      align-items: center;
      justify-content: center;
      margin-bottom: 24px;
      flex-shrink: 0;
    }

    .empty__icon-box--bookings {
      background: #EEEBFD;
    }

    /* Default emoji icon */
    .empty__icon {
      font-size: 48px;
      margin-bottom: 16px;
    }

    .empty__title {
      font-family: 'Space Grotesk', sans-serif;
      font-size: 20px;
      font-weight: 700;
      color: #16181D;
      margin: 0 0 10px;
      letter-spacing: -0.01em;
    }

    .empty__message {
      font-size: 14.5px;
      color: #545A66;
      line-height: 1.6;
      max-width: 42ch;
      margin: 0 0 28px;
    }

    .empty__cta {
      display: inline-flex;
      align-items: center;
      justify-content: center;
      background: #5B4BE3;
      color: #fff;
      font-family: inherit;
      font-size: 14.5px;
      font-weight: 700;
      padding: 12px 22px;
      border-radius: 13px;
      text-decoration: none;
      box-shadow: 0 8px 22px rgba(91, 75, 227, 0.28);
      transition: opacity 0.15s;

      &:hover { opacity: 0.88; text-decoration: none; }
    }
  `],
})
export class EmptyStateComponent {
  @Input() variant: EmptyStateVariant = 'default';
  @Input() icon = '🔍';
  @Input() title = '';
  @Input() message = '';

  get resolvedTitle(): string {
    if (this.title) return this.title;
    return this.variant === 'bookings' ? 'No bookings yet' : 'Nothing here yet';
  }

  get resolvedMessage(): string {
    if (this.message) return this.message;
    return this.variant === 'bookings'
      ? "When you book a session it'll show up here. Find a coach and lock in your first one."
      : 'Try adjusting your filters.';
  }
}

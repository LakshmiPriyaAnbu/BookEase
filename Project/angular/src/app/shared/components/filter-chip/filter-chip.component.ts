import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'be-filter-chip',
  standalone: true,
  imports: [CommonModule],
  template: `
    <button class="chip" [class.chip--active]="active" (click)="clicked.emit()">
      {{ label }}
    </button>`,
  styles: [`
    @use 'tokens' as *;
    .chip {
      display: inline-flex; align-items: center;
      padding: 8px 16px; border-radius: $radius-pill; border: 1.5px solid transparent;
      font-family: $font-ui; font-size: 13.5px; font-weight: 700; cursor: pointer;
      background: $surface-200; color: $text-600;
      transition: all $dur-fast $ease; white-space: nowrap;
      &:hover { background: $primary-soft; color: $primary; }
    }
    .chip--active { background: $primary-soft; color: $primary; border-color: $primary;
    }
  `],
})
export class FilterChipComponent {
  @Input() label = '';
  @Input() active = false;
  @Output() clicked = new EventEmitter<void>();
}

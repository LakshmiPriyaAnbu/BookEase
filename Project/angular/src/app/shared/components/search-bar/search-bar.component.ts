import { Component, Input, Output, EventEmitter, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR, FormsModule } from '@angular/forms';

@Component({
  selector: 'be-search-bar',
  standalone: true,
  imports: [FormsModule],
  template: `
    <div class="search">
      <svg class="search__icon" viewBox="0 0 20 20" fill="none">
        <path d="M9 17A8 8 0 1 0 9 1a8 8 0 0 0 0 16zM19 19l-4.35-4.35" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
      </svg>
      <input class="search__input" [placeholder]="placeholder"
        [(ngModel)]="value" (ngModelChange)="emit($event)" />
    </div>`,
  styles: [`
    @use 'tokens' as *;
    .search {
      display: flex; align-items: center; gap: 10px;
      background: $surface-150; border-radius: $radius-md;
      padding: 0 16px; height: 46px;
    }
    .search__icon { width: 16px; height: 16px; color: $muted-500; flex-shrink: 0; }
    .search__input {
      border: none; background: transparent; outline: none;
      font-family: $font-ui; font-size: 14.5px; font-weight: 500; color: $ink-700;
      width: 100%;
      &::placeholder { color: $muted-500; }
    }
  `],
  providers: [{ provide: NG_VALUE_ACCESSOR, useExisting: forwardRef(() => SearchBarComponent), multi: true }],
})
export class SearchBarComponent implements ControlValueAccessor {
  @Input() placeholder = 'Search…';
  value = '';
  private onChange = (_: string) => {};
  emit(v: string) { this.onChange(v); }
  writeValue(v: string) { this.value = v ?? ''; }
  registerOnChange(fn: (v: string) => void) { this.onChange = fn; }
  registerOnTouched() {}
}

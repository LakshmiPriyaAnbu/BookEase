import { Injectable, signal } from '@angular/core';

export type ToastType = 'success' | 'error' | 'info' | 'warning';

export interface Toast {
  id: number;
  message: string;
  title?: string;
  type: ToastType;
}

@Injectable({ providedIn: 'root' })
export class ToastService {
  private _nextId = 0;
  private _timers = new Map<number, ReturnType<typeof setTimeout>>();

  readonly toasts = signal<Toast[]>([]);

  show(message: string, type: ToastType, title?: string, durationMs = 3500): void {
    const id = ++this._nextId;
    const toast: Toast = { id, message, type, title };

    this.toasts.update(current => {
      const updated = [...current, toast];
      // Keep max 3 toasts; drop the oldest when a 4th arrives
      if (updated.length > 3) {
        const removed = updated.shift()!;
        this._clearTimer(removed.id);
      }
      return updated;
    });

    const timer = setTimeout(() => this.dismiss(id), durationMs);
    this._timers.set(id, timer);
  }

  dismiss(id: number): void {
    this._clearTimer(id);
    this.toasts.update(current => current.filter(t => t.id !== id));
  }

  private _clearTimer(id: number): void {
    const timer = this._timers.get(id);
    if (timer !== undefined) {
      clearTimeout(timer);
      this._timers.delete(id);
    }
  }
}

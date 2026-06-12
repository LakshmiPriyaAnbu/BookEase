import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Service } from '../../models/models';
import { MoneyPipe } from '../../pipes/money.pipe';
import { DurationPipe } from '../../pipes/duration.pipe';

@Component({
  selector: 'be-service-card',
  standalone: true,
  imports: [CommonModule, RouterModule, MoneyPipe, DurationPipe],
  template: `
    <div class="card" [routerLink]="['/services', service.id]">
      <div class="card__thumb">
        <img *ngIf="service.imageUrl" [src]="service.imageUrl" [alt]="service.name" />
        <div *ngIf="!service.imageUrl" class="card__thumb-placeholder">
          <span>💪</span>
        </div>
        <span class="card__category">{{ service.categoryName }}</span>
      </div>
      <div class="card__body">
        <h3 class="card__title">{{ service.name }}</h3>
        <p class="card__desc">{{ service.description }}</p>
        <div class="card__meta">
          <span class="card__meta-item">⏱ {{ service.durationMin | duration }}</span>
          <span class="card__meta-item" *ngIf="service.rating">⭐ {{ service.rating }}</span>
          <span class="card__meta-item" *ngIf="service.coachName">👤 {{ service.coachName }}</span>
        </div>
        <div class="card__footer">
          <span class="card__price">{{ service.priceCents | money }}</span>
          <button class="card__book" (click)="book($event)">Book</button>
        </div>
      </div>
    </div>`,
  styles: [`
    @use 'tokens' as *;
    .card {
      background: $surface-0; border: 1px solid $border-100;
      border-radius: $radius-xl; overflow: hidden; cursor: pointer;
      transition: box-shadow $dur-base $ease, transform $dur-base $ease;
      &:hover { box-shadow: $shadow-card; transform: translateY(-2px); }
    }
    .card__thumb {
      position: relative; height: 180px; background: $primary-soft;
      display: flex; align-items: center; justify-content: center;
      img { width: 100%; height: 100%; object-fit: cover; }
    }
    .card__thumb-placeholder { font-size: 48px; }
    .card__category {
      position: absolute; top: 12px; left: 12px;
      background: rgba(255,255,255,.9); color: $primary;
      font-size: 12px; font-weight: 700; padding: 4px 10px;
      border-radius: $radius-pill;
    }
    .card__body { padding: 18px; }
    .card__title { font-size: 17px; font-weight: 800; color: $ink-800; margin-bottom: 6px; }
    .card__desc {
      font-size: 13.5px; color: $text-600; line-height: 1.5;
      display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden;
      margin-bottom: 12px;
    }
    .card__meta { display: flex; gap: 12px; flex-wrap: wrap; margin-bottom: 16px; }
    .card__meta-item { font-size: 12.5px; color: $muted-500; font-weight: 500; }
    .card__footer { display: flex; align-items: center; justify-content: space-between; }
    .card__price { font-family: $font-display; font-size: 20px; font-weight: 700; color: $primary; }
    .card__book {
      background: $primary-soft; color: $primary; border: none; cursor: pointer;
      font-family: $font-ui; font-size: 13.5px; font-weight: 700;
      padding: 8px 16px; border-radius: $radius-pill;
      transition: background $dur-fast; &:hover { background: $primary; color: #fff; }
    }
  `],
})
export class ServiceCardComponent {
  @Input() service!: Service;
  @Output() booked = new EventEmitter<Service>();
  book(e: Event) { e.stopPropagation(); this.booked.emit(this.service); }
}

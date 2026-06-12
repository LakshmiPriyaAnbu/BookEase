import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Booking, BookingStatus, MOCK_BOOKINGS } from '../../shared/models/models';

@Injectable({ providedIn: 'root' })
export class BookingsService {
  private bookings = [...MOCK_BOOKINGS];

  getBookings(params?: { status?: BookingStatus; page?: number }): Observable<Booking[]> {
    let filtered = this.bookings;
    if (params?.status) filtered = filtered.filter(b => b.status === params.status);
    return of(filtered);
  }

  getBooking(id: string): Observable<Booking | undefined> {
    return of(this.bookings.find(b => b.id === id));
  }

  createBooking(data: Partial<Booking>): Observable<Booking> {
    const booking: Booking = {
      ...data as Booking,
      id: 'bk-' + Date.now(),
      ref: 'BK-' + Math.floor(10000 + Math.random() * 90000),
      status: 'confirmed',
      createdAt: new Date().toISOString(),
    };
    this.bookings.unshift(booking);
    return of(booking);
  }

  updateStatus(id: string, status: BookingStatus): Observable<Booking> {
    const idx = this.bookings.findIndex(b => b.id === id);
    if (idx !== -1) this.bookings[idx] = { ...this.bookings[idx], status };
    return of(this.bookings[idx]);
  }

  getAdminStats() {
    const confirmed = this.bookings.filter(b => b.status === 'confirmed' || b.status === 'completed');
    return of({
      totalBookings: this.bookings.length,
      totalRevenueCents: this.bookings.filter(b => b.status === 'completed').reduce((s, b) => s + b.priceCents, 0),
      totalCustomers: 24,
      activeServices: 6,
    });
  }
}

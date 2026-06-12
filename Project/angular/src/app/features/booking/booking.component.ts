import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ServicesService } from '../../core/services/services.service';
import { BookingsService } from '../../core/services/bookings.service';
import { ToastService } from '../../shared/services/toast.service';
import { Service, TimeSlot } from '../../shared/models/models';
import { APP_STRINGS } from '../../core/constants';

@Component({
  selector: 'be-booking',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './booking.component.html',
  styleUrl: './booking.component.scss',
})
export class BookingComponent implements OnInit {
  service: Service | undefined;
  slots: (TimeSlot & { isAvailable: boolean })[] = [];
  selectedSlot: TimeSlot | null = null;
  selectedDateIdx = 2;
  submitting = false;
  submitAttempted = false;
  datePills: { weekday: string; day: string; fullDate: string; date: Date }[] = [];
  form = { fullName: '', phone: '', email: '', notes: '' };

  readonly appStrings = APP_STRINGS;
  protected readonly S = APP_STRINGS;

  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private svcService = inject(ServicesService);
  private bookingService = inject(BookingsService);
  private toastService = inject(ToastService);

  get canConfirm(): boolean {
    return !!(this.selectedSlot && this.form.fullName.trim() && this.form.phone.trim());
  }

  get slotError(): boolean {
    return this.submitAttempted && !this.selectedSlot;
  }

  get fullNameError(): boolean {
    return this.submitAttempted && !this.form.fullName.trim();
  }

  get phoneError(): boolean {
    return this.submitAttempted && !this.form.phone.trim();
  }

  ngOnInit(): void {
    const days = ['MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN'];
    this.datePills = Array.from({ length: 7 }, (_, i) => {
      const d = new Date(Date.now() + i * 86400000);
      return {
        weekday: days[d.getDay() === 0 ? 6 : d.getDay() - 1],
        day: String(d.getDate()),
        fullDate: d.toLocaleDateString('en-US', { weekday: 'short', month: 'short', day: 'numeric' }),
        date: d,
      };
    });
    const serviceId = this.route.snapshot.queryParamMap.get('serviceId') ?? 'svc-1';
    this.svcService.getService(serviceId).subscribe(s => {
      this.service = s;
      this.loadSlots();
    });
  }

  loadSlots(): void {
    if (!this.service) return;
    const date = this.datePills[this.selectedDateIdx]?.date.toISOString().split('T')[0] ?? '';
    this.svcService.getAvailability(this.service.id, date).subscribe(raw => {
      this.slots = raw.map(s => ({ ...s, isAvailable: !s.isBooked }));
      this.selectedSlot = null;
    });
  }

  selectDate(idx: number): void {
    this.selectedDateIdx = idx;
    this.loadSlots();
  }

  selectSlot(slot: TimeSlot & { isAvailable: boolean }): void {
    if (slot.isAvailable) this.selectedSlot = slot;
  }

  confirm(): void {
    this.submitAttempted = true;

    if (!this.canConfirm) {
      this.toastService.show(APP_STRINGS.BOOKING.TOAST_MISSING, 'error');
      return;
    }

    this.submitting = true;
    this.bookingService.createBooking({
      serviceId: this.service!.id,
      serviceName: this.service!.name,
      coachId: this.service!.coachId ?? '',
      coachName: this.service!.coachName,
      userId: 'user-current',
      startsAt: this.selectedSlot!.startsAt,
      priceCents: this.service!.priceCents,
      fullName: this.form.fullName,
      phone: this.form.phone,
      notes: this.form.notes,
    }).subscribe({
      next: b => {
        this.submitting = false;
        this.toastService.show(APP_STRINGS.BOOKING.TOAST_CONFIRMED, 'success');
        this.router.navigate(['/confirmation', b.id]);
      },
      error: () => {
        this.submitting = false;
        this.toastService.show(APP_STRINGS.BOOKING.TOAST_ERROR, 'error');
      },
    });
  }

  formattedTotal(): string {
    return this.service ? '$' + (this.service.priceCents / 100).toFixed(2) : '';
  }

  summaryDate(): string {
    const p = this.datePills[this.selectedDateIdx];
    return p ? p.fullDate : '—';
  }
}

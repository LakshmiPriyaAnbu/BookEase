import { Injectable, signal } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Category, Service, TimeSlot, MOCK_CATEGORIES, MOCK_SERVICES, MOCK_TIME_SLOTS } from '../../shared/models/models';

@Injectable({ providedIn: 'root' })
export class ServicesService {
  // Using mock data — swap for ApiService calls when backend is live
  getCategories(): Observable<Category[]> {
    return of(MOCK_CATEGORIES);
  }

  getServices(params?: { search?: string; categoryId?: string; page?: number }): Observable<Service[]> {
    let filtered = [...MOCK_SERVICES];
    if (params?.search) {
      const q = params.search.toLowerCase();
      filtered = filtered.filter(s => s.name.toLowerCase().includes(q) || (s.coachName ?? '').toLowerCase().includes(q));
    }
    if (params?.categoryId && params.categoryId !== 'all') {
      filtered = filtered.filter(s => s.categoryId === params.categoryId);
    }
    return of(filtered);
  }

  getService(id: string): Observable<Service | undefined> {
    return of(MOCK_SERVICES.find(s => s.id === id));
  }

  getAvailability(serviceId: string, date: string): Observable<TimeSlot[]> {
    return of(MOCK_TIME_SLOTS);
  }

  createService(data: Partial<Service>): Observable<Service> {
    const newSvc: Service = {
      ...data as Service,
      id: 'svc-' + Date.now(),
      active: true,
      createdAt: new Date().toISOString(),
    };
    MOCK_SERVICES.push(newSvc);
    return of(newSvc);
  }

  updateService(id: string, data: Partial<Service>): Observable<Service> {
    const idx = MOCK_SERVICES.findIndex(s => s.id === id);
    if (idx !== -1) MOCK_SERVICES[idx] = { ...MOCK_SERVICES[idx], ...data };
    return of(MOCK_SERVICES[idx]);
  }

  deleteService(id: string): Observable<void> {
    const idx = MOCK_SERVICES.findIndex(s => s.id === id);
    if (idx !== -1) MOCK_SERVICES[idx].active = false;
    return of(undefined);
  }
}

import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./features/landing/landing.component').then(m => m.LandingComponent),
  },
  {
    path: 'services',
    loadComponent: () => import('./features/services/services-list.component').then(m => m.ServicesListComponent),
  },
  {
    path: 'services/:id',
    loadComponent: () => import('./features/services/service-detail.component').then(m => m.ServiceDetailComponent),
  },
  {
    path: 'book',
    loadComponent: () => import('./features/booking/booking.component').then(m => m.BookingComponent),
  },
  {
    path: 'confirmation/:id',
    loadComponent: () => import('./features/confirmation/confirmation.component').then(m => m.ConfirmationComponent),
  },
  {
    path: 'admin',
    loadComponent: () => import('./features/admin/admin-layout.component').then(m => m.AdminLayoutComponent),
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      {
        path: 'dashboard',
        loadComponent: () => import('./features/admin/dashboard/dashboard.component').then(m => m.DashboardComponent),
      },
      {
        path: 'bookings',
        loadComponent: () => import('./features/admin/bookings/admin-bookings.component').then(m => m.AdminBookingsComponent),
      },
      {
        path: 'services',
        loadComponent: () => import('./features/admin/services-management/admin-services.component').then(m => m.AdminServicesComponent),
      },
    ],
  },
  { path: 'services-page', loadComponent: () => import('./features/services-page/services-page.component').then(m => m.ServicesPageComponent) },
  { path: 'how-it-works', loadComponent: () => import('./features/how-it-works/how-it-works.component').then(m => m.HowItWorksComponent) },
  { path: 'pricing', loadComponent: () => import('./features/pricing/pricing.component').then(m => m.PricingComponent) },
  { path: 'contact', loadComponent: () => import('./features/contact/contact.component').then(m => m.ContactComponent) },
  { path: 'login', loadComponent: () => import('./features/login/login.component').then(m => m.LoginComponent) },
  { path: '**', loadComponent: () => import('./features/not-found/not-found.component').then(m => m.NotFoundComponent) },
];

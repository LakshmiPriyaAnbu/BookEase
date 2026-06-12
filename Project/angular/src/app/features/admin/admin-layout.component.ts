import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'be-admin-layout',
  standalone: true,
  imports: [RouterModule],
  template: `
    <div class="admin">
      <!-- Sidebar -->
      <aside class="sidebar">
        <div class="sidebar__brand">
          <span>⚡</span>
          <span class="sidebar__name">BookEase</span>
          <span class="sidebar__tag">Admin</span>
        </div>

        <nav class="sidebar__nav">
          <a routerLink="/admin/dashboard" routerLinkActive="nav-item--active" class="nav-item">
            <span>📊</span> Dashboard
          </a>
          <a routerLink="/admin/bookings" routerLinkActive="nav-item--active" class="nav-item">
            <span>📅</span> Bookings
          </a>
          <a routerLink="/admin/services" routerLinkActive="nav-item--active" class="nav-item">
            <span>🏋️</span> Services
          </a>
        </nav>

        <div class="sidebar__footer">
          <a routerLink="/" class="nav-item">
            <span>🌐</span> View site
          </a>
        </div>
      </aside>

      <!-- Main -->
      <main class="main">
        <router-outlet />
      </main>
    </div>
  `,
  styles: [`
    @use 'tokens' as *;
    .admin { display: flex; min-height: 100vh; }

    .sidebar {
      width: 240px; flex-shrink: 0; background: $ink-800;
      display: flex; flex-direction: column; padding: 24px 0; position: sticky; top: 0; height: 100vh;
    }
    .sidebar__brand {
      display: flex; align-items: center; gap: 8px; padding: 0 20px 24px;
      border-bottom: 1px solid rgba(255,255,255,.1); margin-bottom: 16px;
    }
    .sidebar__name { font-family: $font-display; font-size: 16px; font-weight: 700; color: #fff; }
    .sidebar__tag { font-size: 10px; font-weight: 700; background: $primary-soft; color: $primary; padding: 2px 6px; border-radius: $radius-pill; }

    .sidebar__nav { display: flex; flex-direction: column; gap: 2px; padding: 0 12px; flex: 1; }
    .sidebar__footer { padding: 12px 12px 0; border-top: 1px solid rgba(255,255,255,.1); }

    .nav-item {
      display: flex; align-items: center; gap: 10px; padding: 10px 12px;
      border-radius: $radius-lg; color: rgba(255,255,255,.65); font-size: 14.5px; font-weight: 600;
      text-decoration: none; transition: all $dur-fast;
      &:hover { background: rgba(255,255,255,.1); color: #fff; text-decoration: none; }
    }
    .nav-item--active { background: $primary; color: #fff;
    }

    .main { flex: 1; background: $surface-100; overflow-y: auto; }
  `],
})
export class AdminLayoutComponent {}

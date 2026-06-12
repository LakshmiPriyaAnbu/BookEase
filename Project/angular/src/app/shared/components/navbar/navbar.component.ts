import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'be-navbar',
  standalone: true,
  imports: [RouterModule],
  template: `
    <nav class="nav">
      <div class="container nav__inner">
        <a class="nav__brand" routerLink="/">
          <span class="nav__logo">⚡</span>
          <span class="nav__name">BookEase</span>
        </a>
        <div class="nav__links">
          <a routerLink="/services" routerLinkActive="nav__link--active" class="nav__link">Services</a>
          <a routerLink="/admin" class="nav__link">Admin</a>
        </div>
        <a routerLink="/book" class="nav__cta">Book a session</a>
      </div>
    </nav>`,
  styles: [`
    @use 'tokens' as *;
    .nav {
      position: sticky; top: 0; z-index: 100;
      background: rgba(255,255,255,.92); backdrop-filter: blur(12px);
      border-bottom: 1px solid $border-100;
    }
    .nav__inner { display: flex; align-items: center; gap: 32px; height: 64px; }
    .nav__brand { display: flex; align-items: center; gap: 8px; text-decoration: none; }
    .nav__logo { font-size: 22px; }
    .nav__name { font-family: $font-display; font-size: 18px; font-weight: 700; color: $ink-800; }
    .nav__links { display: flex; gap: 24px; flex: 1; justify-content: center; }
    .nav__link {
      font-size: 14.5px; font-weight: 600; color: $text-600; text-decoration: none;
      transition: color $dur-fast; &:hover { color: $primary; text-decoration: none; }
    }
    .nav__link--active { color: $primary; text-decoration: none;
    }
    .nav__cta {
      margin-left: auto; background: $primary; color: #fff;
      font-size: 14px; font-weight: 700; padding: 9px 18px;
      border-radius: $radius-md; text-decoration: none;
      box-shadow: $shadow-btn-sm; transition: background $dur-fast;
      &:hover { background: $primary-700; text-decoration: none; }
    }
  `],
})
export class NavbarComponent {}

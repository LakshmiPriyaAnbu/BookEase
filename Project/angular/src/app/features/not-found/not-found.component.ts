import { Component, ChangeDetectionStrategy } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from '../../shared/components/navbar/navbar.component';

@Component({
  selector: 'be-not-found',
  standalone: true,
  imports: [RouterModule, NavbarComponent],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <be-navbar />

    <main class="nf-page">
      <div class="nf-content">
        <p class="nf-numeral" aria-hidden="true">404</p>
        <h1 class="nf-heading">We couldn't find that page</h1>
        <p class="nf-body">
          The page you're looking for may have moved, or the link might be broken.
          Let's get you back on track.
        </p>
        <div class="nf-actions">
          <a routerLink="/" class="btn btn--primary">Back to home</a>
          <a routerLink="/services" class="btn btn--secondary">Browse services</a>
        </div>
      </div>
    </main>
  `,
  styles: [`
    :host { display: block; background: #fff; min-height: 100vh; }

    .nf-page {
      display: flex;
      justify-content: center;
      align-items: center;
      padding: 84px 40px;
      min-height: calc(100vh - 64px);
    }

    .nf-content {
      text-align: center;
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 0;
    }

    .nf-numeral {
      font-family: 'Space Grotesk', sans-serif;
      font-size: 120px;
      font-weight: 700;
      letter-spacing: -0.04em;
      line-height: 1;
      margin: 0 0 24px;
      background: linear-gradient(145deg, #6B5BF0, #4A3BD0);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
    }

    .nf-heading {
      font-family: 'Space Grotesk', sans-serif;
      font-size: 30px;
      font-weight: 700;
      color: #0F1115;
      margin: 0 0 16px;
      letter-spacing: -0.02em;
    }

    .nf-body {
      font-size: 16px;
      color: #545A66;
      line-height: 1.6;
      max-width: 46ch;
      margin: 0 0 36px;
    }

    .nf-actions {
      display: flex;
      gap: 12px;
      flex-wrap: wrap;
      justify-content: center;
    }

    .btn {
      display: inline-flex;
      align-items: center;
      justify-content: center;
      font-family: inherit;
      font-size: 15px;
      font-weight: 700;
      padding: 13px 26px;
      border-radius: 13px;
      text-decoration: none;
      border: none;
      cursor: pointer;
      transition: opacity 0.15s;

      &:hover { opacity: 0.88; text-decoration: none; }
    }

    .btn--primary {
      background: #5B4BE3;
      color: #fff;
      box-shadow: 0 8px 22px rgba(91, 75, 227, 0.3);
    }

    .btn--secondary {
      background: #fff;
      color: #16181D;
      border: 1px solid #DDDFE6;
    }
  `],
})
export class NotFoundComponent {}

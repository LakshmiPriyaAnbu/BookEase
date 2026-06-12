import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'money', standalone: true })
export class MoneyPipe implements PipeTransform {
  transform(cents: number): string {
    return `$${(cents / 100).toFixed(0)}`;
  }
}

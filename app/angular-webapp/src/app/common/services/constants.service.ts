import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConstantsService {
readonly baseAppUrl: string = 'http://localhost:8080';
  constructor() { }
}

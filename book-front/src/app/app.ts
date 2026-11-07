import { Component, signal } from '@angular/core';
import { BookReservation } from "./components/book-reservation/book-reservation";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [BookReservation],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {}

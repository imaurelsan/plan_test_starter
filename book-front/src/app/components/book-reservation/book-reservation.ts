import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Book } from '../../models/book';
import { BookService } from '../../services/book.service';

@Component({
  selector: 'app-book-reservation',
  standalone: true,
  imports: [],
  templateUrl: './book-reservation.html',
  styleUrl: './book-reservation.css',
})
export class BookReservation implements OnInit {

  books: Book[] = [];
  notGetBooks: Book[] = [];

  constructor(
    private bookService: BookService,
    private cdr: ChangeDetectorRef) { }

  ngOnInit() {
    this.loadBooks();
    this.loadNotGetBooks();
  }

  loadBooks() {
    this.bookService.getAllBooks().subscribe(data => {
      //console.log('Books loaded:', data);
      this.books = data;
      this.cdr.detectChanges();
    });
  }

  loadNotGetBooks() {
    this.bookService.notGet().subscribe(data => {
      this.notGetBooks = data;
      //console.log('Books not Get:', data);
      this.cdr.detectChanges();
    });
  }

  reserve(bookId: number) {

    this.bookService.reserve(bookId, 1).subscribe({
      next: () => {
        this.loadBooks();
        this.loadNotGetBooks();
      },
      error: (err) => {
        console.error('Reservation failed', err);
      }
    });

  }

  canReserve(book: Book): boolean {

    if (book.status === 'BORROWED') {
      return false;
    }

    if (book.status === 'AVAILABLE') {
      return true;
    }

    if (book.status === 'BOOKED') {
      return this.notGetBooks.some(b => b.id === book.id);
    }

    return false;

  }



}

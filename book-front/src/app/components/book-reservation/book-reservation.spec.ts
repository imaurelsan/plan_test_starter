import { ChangeDetectorRef } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { BookReservation } from './book-reservation';
import { BookService } from '../../services/book.service';

describe('BookReservation', () => {
  const bookedBook = {
    id: 1,
    name: 'Livre réservé',
    description: 'Description',
    status: 'BOOKED'
  };

  const bookServiceMock = {
    getAllBooks: jest.fn(() => of([])),
    notGet: jest.fn(() => of([])),
    getBookByUserId: jest.fn(() => of([])),
    reserve: jest.fn(() => of(bookedBook))
  };

  beforeEach(() => {
    jest.clearAllMocks();
    TestBed.configureTestingModule({
      imports: [BookReservation],
      providers: [
        { provide: BookService, useValue: bookServiceMock },
        { provide: ChangeDetectorRef, useValue: { detectChanges: jest.fn() } }
      ]
    });
  });

  it('réserve un livre avec les bons arguments et reçoit un livre BOOKED', () => {
    // Arrange : je prépare le composant et le livre renvoyé par le mock.
    const component = TestBed.createComponent(BookReservation).componentInstance;
    let response: typeof bookedBook | undefined;

    // Act : je demande la réservation du livre 1.
    component.reserve(1);
    bookServiceMock.reserve.mock.results[0].value.subscribe((book: typeof bookedBook) => {
      response = book;
    });

    // Assert : le service reçoit les bons identifiants et renvoie BOOKED.
    expect(bookServiceMock.reserve).toHaveBeenCalledWith(1, 1);
    expect(response?.status).toBe('BOOKED');
  });
});

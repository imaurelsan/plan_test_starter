import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Book } from '../models/book';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private apiUrl = 'http://localhost:8080/book';

  constructor(private http: HttpClient) {}

  getAllBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(this.apiUrl);
  }

  getBookByUserId(userId: number): Observable<Book[]> {
    return this.http.get<Book[]>(`${this.apiUrl}/${userId}`);
  }

  notGet(): Observable<Book[]> {
    return this.http.get<Book[]>(`${this.apiUrl}/notget`);
  }

  reserve(bookId: number, userId: number) {
    return this.http.post(`${this.apiUrl}/reserve/${bookId}/${userId}`, {});
  }
}

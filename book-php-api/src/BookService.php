<?php

class BookService {

    private $books = [
        ['id' => 1, 'name' => 'Book 1', 'status' => 'BOOKED'],
        ['id' => 2, 'name' => 'Book 2', 'status' => 'AVAILABLE'],
        ['id' => 3, 'name' => 'Book 3', 'status' => 'BORROWED'],
    ];

    public function getAllBooks() {
        return $this->books;
    }

    public function reserve($bookId) {

        foreach ($this->books as &$book) {

            if ($book['id'] === $bookId) {

                if ($book['status'] === 'BORROWED') {
                    throw new Exception('Already borrowed');
                }

                if ($book['status'] === 'BOOKED') {
                    throw new Exception('Already booked');
                }

                $book['status'] = 'BOOKED';
                return $book;
            }
        }

        throw new Exception('Book not found');
    }
}
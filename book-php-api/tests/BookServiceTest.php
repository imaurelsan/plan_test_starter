<?php

use PHPUnit\Framework\TestCase;

require_once __DIR__ . '/../src/BookService.php';

class BookServiceTest extends TestCase {

    public function testGetAllBooks() {
        // Arrange
        $service = new BookService();
        // Act
        $books = $service->getAllBooks();
        // Assert   
        $this->assertCount(3, $books);
    }

    public function testReserveAvailableBook() {
        // Arrange
        $service = new BookService();
        // Act
        $book = $service->reserve(2);
        // Assert
        $this->assertEquals('BOOKED', $book['status']);
    }

    public function testReserveBorrowedBookShouldFail() {
        // Arrange
        $this->expectException(Exception::class);
        $service = new BookService();
        // Act & Assert
        $service->reserve(3);
    }
}

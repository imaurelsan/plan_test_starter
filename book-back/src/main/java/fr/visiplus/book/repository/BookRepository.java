package fr.visiplus.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.visiplus.book.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}

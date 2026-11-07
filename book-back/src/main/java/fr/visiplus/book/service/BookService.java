package fr.visiplus.book.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import fr.visiplus.book.dto.BookDTO;
import fr.visiplus.book.entity.Book;
import fr.visiplus.book.entity.User;
import fr.visiplus.book.entity.BookStatus;
import fr.visiplus.book.repository.BookRepository;
import fr.visiplus.book.repository.UserRepository;


@Service
public class BookService {
	
	private ReservationService reservationService;		
	private BookRepository bookRepository;
	private UserRepository userRepository;
	
	public BookService(
			final ReservationService reservationService,
			final BookRepository bookRepository, 
			final UserRepository userRepository) {
		this.reservationService = reservationService;
		this.bookRepository = bookRepository;
		this.userRepository = userRepository;
	}
	
	public List<BookDTO> getAllBooks() {
		return convert(bookRepository.findAll());
	}

	public Set<BookDTO> getBooksByUserId(final Long userId) {		
		User user = userRepository.getReferenceById(userId);
		Set<BookDTO> books = new LinkedHashSet<BookDTO>();
		user.getReservations().forEach((resa) -> {
				books.add(convert(resa.getBook()));
		});
		return books;
	}

	public List<BookDTO> getBookBookedButNotGet() {	
		return bookRepository.findAll().stream()
				.filter((book) -> isNotGetLast21Days(book))
				.map((book) -> convert(book))
				.collect(Collectors.toList());		
	}
	
	/**
	 * Vérifie si le livre n'a pas été récupéré par l'utilisateur lors des 21 derniers jours.
	 * @param book
	 * @return Vrai si le livre n'a pas été récupéré depuis au moins 21 jours après sa réservation. Faux sinon.
	 */
	public boolean isNotGetLast21Days(final Book book) {
		boolean booked = book.getStatus().equals(BookStatus.BOOKED);
		if (booked) {
			return reservationService.isReservationMoreThan(book.getReservation(), 21);
		}
		return false;
	}
	
	private List<BookDTO> convert(final List<Book> books) {
		return books.stream().map( (book) -> convert(book) ).collect(Collectors.toList());
	}
	
	private BookDTO convert(final Book book) {
		return new BookDTO(book.getId(), book.getName(), book.getDescription(), book.getStatus());
	}

}

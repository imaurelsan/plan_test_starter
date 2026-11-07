package fr.visiplus.book.service;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Service;

import fr.visiplus.book.dto.BookDTO;
import fr.visiplus.book.dto.ReservationDTO;
import fr.visiplus.book.dto.UserDTO;
import fr.visiplus.book.entity.Book;
import fr.visiplus.book.entity.BookStatus;
import fr.visiplus.book.entity.Reservation;
import fr.visiplus.book.entity.User;
import fr.visiplus.book.repository.BookRepository;
import fr.visiplus.book.repository.ReservationRepository;
import fr.visiplus.book.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class ReservationService {
		
	private BookRepository bookRepository;
	private ReservationRepository reservationRepository;	
	private UserRepository userRepository;
	
	public ReservationService(
			final BookRepository bookRepository, 
			final ReservationRepository reservationRepository,
			final UserRepository userRepository) {		
		this.bookRepository = bookRepository;
		this.reservationRepository = reservationRepository;
		this.userRepository = userRepository;
	}
	
	/**
	 * Permet de savoir si la réservation dépasse à un certain nombre de jours.
	 * @param reservation La réservation à vérifier
	 * @param numberOfDays Le nombre de jour à vérifier
	 * @return Vrai si la réservation a plus du nombre de jours à vérifier, faux dans le cas inverse.
	 */
	public boolean isReservationMoreThan(Reservation reservation, int numberOfDays) {
		LocalDate bookedDate = reservation.getDateResa();
		LocalDate today = LocalDate.now();
		
		Period period = Period.between(bookedDate, today);
		if(period.getYears() > 0 || period.getMonths() > 0 || period.getDays() > numberOfDays) {			
			return true;
		}	
		return false;
	}
	
	/**
	 * Réserve un livre si le livre n'est pas emprunté et si dans le cas où une réservation  
	 * existe cette dernière est de plus de 7 jours
	 * @param bookId L'id du livre à réserver
	 * @param userId L'id de l'utilisateur qui veut réserver
	 * @return Le détail de la réservation
	 * @throws Exception Lorsqu'une réservation est impossible
	 */
	@Transactional
	public ReservationDTO bookByBookIdAndUserId(final Long bookId, final Long userId) throws Exception {
		
		User user = userRepository.getReferenceById(userId);
		Book book = bookRepository.getReferenceById(bookId);
		
		if(book.getStatus().equals(BookStatus.BORROWED)) {
			throw new Exception("Already borrowed");
		} else if(book.getStatus().equals(BookStatus.BOOKED)) {
			// Une réservation est valide 7 jours. Au delà ça un autre utilisateur peut réserver le livre.
			if(isReservationMoreThan(book.getReservation(), 7)) {
				Reservation oldReservation = book.getReservation();
				book.setReservation(null);
				reservationRepository.deleteById(oldReservation.getId());
			} else {
				throw new Exception("Booked < 7 days");
			}
		}		
		
		book.setStatus(BookStatus.BOOKED);
		
		Reservation resa = new Reservation();
		resa.setDateResa(LocalDate.now());
		resa.setBook(book);
		resa.setUser(user);
		
		resa = reservationRepository.save(resa);
		
		book.setReservation(resa);
		bookRepository.save(book);		
		
		return convert(resa);
	}
	
	private ReservationDTO convert(final Reservation reservation) {
		UserDTO userDTO = new UserDTO(
				reservation.getUser().getId(), 
				reservation.getUser().getUsername());
		BookDTO bookDTO = new BookDTO(
				reservation.getBook().getId(),
				reservation.getBook().getName(),
				reservation.getBook().getDescription(),
				reservation.getBook().getStatus());
		ReservationDTO resaDTO = new ReservationDTO(reservation.getId(), reservation.getDateResa(), userDTO, bookDTO);		
		return resaDTO;
	}

}

package fr.visiplus.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.visiplus.book.entity.Reservation;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{

}

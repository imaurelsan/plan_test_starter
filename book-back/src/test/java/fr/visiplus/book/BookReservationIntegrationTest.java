package fr.visiplus.book;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import fr.visiplus.book.entity.Book;
import fr.visiplus.book.entity.BookStatus;
import fr.visiplus.book.entity.User;
import fr.visiplus.book.repository.BookRepository;
import fr.visiplus.book.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
class BookReservationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void reserveAvailableBookReturnsBookedBook() throws Exception {
        // Arrange : je prépare un utilisateur et un livre disponibles.
        User user = new User();
        user.setUsername("test-user");
        user.setPassword("test-password");
        user = userRepository.save(user);

        Book book = new Book();
        book.setName("Livre de test");
        book.setDescription("Description du livre de test");
        book.setStatus(BookStatus.AVAILABLE);
        book = bookRepository.save(book);

        // Act : j'appelle la route de réservation avec les identifiants générés.
        mockMvc.perform(post("/book/reserve/{bookId}/{userId}", book.getId(), user.getId()))

                // Assert : la réservation réussit et le livre devient BOOKED.
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookDTO.status").value("BOOKED"));
    }
}

package uz.pdp.b7begzodganievexam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.b7begzodganievexam.entity.Book;
import uz.pdp.b7begzodganievexam.repository.BookRepository;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public ResponseEntity<?> getAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        return ResponseEntity.ok(bookList);
    }

    public ResponseEntity<?> getBookById(Integer bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        return ResponseEntity.status(optionalBook.isPresent() ? 200 : 404).body(optionalBook.get());
    }

    public ResponseEntity<?> addBook(Book book) {
        try {
            Book savedBook = bookRepository.save(book);
            return ResponseEntity.ok(savedBook);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public ResponseEntity<?> editBook(Integer bookId, Book book) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (!optionalBook.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Book editingBook = optionalBook.get();
        editingBook.setTitle(book.getTitle());
        editingBook.setAuthor(book.getAuthor());
        Book editedBook = bookRepository.save(editingBook);
        return ResponseEntity.ok(editedBook);
    }

    public ResponseEntity<?> deleteBook(Integer bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (!optionalBook.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        bookRepository.deleteById(bookId);
        return ResponseEntity.ok().build();
    }
}

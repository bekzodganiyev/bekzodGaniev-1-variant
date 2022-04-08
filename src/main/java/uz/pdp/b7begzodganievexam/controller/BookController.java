package uz.pdp.b7begzodganievexam.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.b7begzodganievexam.entity.Book;
import uz.pdp.b7begzodganievexam.service.BookService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<?> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable Integer bookId){
        return  bookService.getBookById(bookId);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody Book book){
        return bookService.addBook(book);
    }

    @PutMapping("/edit/{bookId}")
    public ResponseEntity<?> editBook(@PathVariable Integer bookId, @RequestBody Book book){
        return bookService.editBook(bookId,book);
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<?> editBook(@PathVariable Integer bookId){
        return bookService.deleteBook(bookId);
    }


}

package com.bookstoreapplication.bookstore.book;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@AllArgsConstructor
@Validated
public class BookService {

    private final BookRepository bookRepository;
    private final Logger logger = LoggerFactory.getLogger(BookService.class);

    BookDatabaseModel addBookToDatabase(@Valid BookWriteModel bookWriteModel){
        var bookToSave = buildFromWriteToDatabaseModel(bookWriteModel);
        logger.info("Successfully added to the database");
        return bookRepository.save(bookToSave);
    }

    BookDatabaseModel getBookById(long bookId){
        if(bookRepository.findById(bookId).isPresent()){
            var result = bookRepository.findById(bookId).get();
            logger.info("Successively fetch a book from the database");
            return result;
        }else{
            logger.warn("The book with given id does not exist");
            throw new IllegalArgumentException("Book with given id does not exist");
        }
    }

    List<BookDatabaseModel> getAllBooks() {
        var result = bookRepository.findAll();
        logger.info("All books have been fetched from the database");
        return result;
    }

    void deleteBook(long bookId) {
        if(bookRepository.existsById(bookId)){
            bookRepository.deleteById(bookId);
            logger.info("The book was successfully removed from the database");
        }else{
            logger.warn("The book with given id does not exist");
            throw new IllegalArgumentException("Book with given id does not exist");
        }
    }

    void deleteAllBooks() {
        bookRepository.deleteAll();
        logger.warn("All books have been removed from the database");
    }

    BookDatabaseModel updateBookById(@Valid long bookId, @Valid BookWriteModel bookWriteModel) {
        if(bookRepository.findById(bookId).isPresent()){
            var bookToEdit = buildFromWriteToDatabaseModelWithId(bookId, bookWriteModel);
            logger.info("The book with given id has been updated");
            return bookRepository.save(bookToEdit);
        }else{
            logger.warn("The book with given id does not exist");
            throw new IllegalArgumentException("Book with given id does not exist");
        }
    }

    public double getBookPriceByAmount(long bookId, int amount){
        var price = bookRepository.findById(bookId)
                .map(BookDatabaseModel::getPrice)
                .orElseThrow( () -> new IllegalArgumentException("Book with given id does not exist"));
        return price * (double)amount;
    }

    private static BookDatabaseModel buildFromWriteToDatabaseModel(BookWriteModel bookWriteModel) {
        return BookDatabaseModel.builder()
                .title(bookWriteModel.getTitle())
                .author(bookWriteModel.getAuthor())
                .releaseDate(bookWriteModel.getReleaseDate())
                .numberOfPages(bookWriteModel.getNumberOfPages())
                .status(bookWriteModel.isStatus())
                .availablePieces(bookWriteModel.getAvailablePieces())
                .price(bookWriteModel.getPrice())
                .build();
    }

    private static BookDatabaseModel buildFromWriteToDatabaseModelWithId(long bookId, BookWriteModel bookWriteModel) {
        return BookDatabaseModel.builder()
                .bookId(bookId)
                .title(bookWriteModel.getTitle())
                .author(bookWriteModel.getAuthor())
                .releaseDate(bookWriteModel.getReleaseDate())
                .numberOfPages(bookWriteModel.getNumberOfPages())
                .status(bookWriteModel.isStatus())
                .availablePieces(bookWriteModel.getAvailablePieces())
                .price(bookWriteModel.getPrice())
                .build();
    }

}

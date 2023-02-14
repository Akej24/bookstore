package com.bookstoreapplication.bookstore.book;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
class BookService {

    private final BookRepository bookRepository;
    private final Logger logger = LoggerFactory.getLogger(BookService.class);

    public BookDatabaseModel addBookToDatabase(BookWriteModel bookWriteModel){
        var bookToSave = buildFromWriteToDatabaseModel(bookWriteModel);
        var savedBook=  bookRepository.save(bookToSave);
        logger.info("Successfully added to the database");
        return savedBook;
    }

    public BookDatabaseModel getBookById(long bookId){
        if(bookRepository.findById(bookId).isPresent()){
            var result = bookRepository.findById(bookId).get();
            logger.info("Successively fetch a book from the database");
            return result;
        }else{
            logger.warn("The book with given id does not exist");
            throw new IllegalArgumentException("Book with given id does not exist");
        }
    }

    public List<BookDatabaseModel> getAllBooks() {
        var result = bookRepository.findAll();
        logger.info("All books have been fetched from the database");
        return result;
    }

    public void deleteBook(long bookId) {
        if(bookRepository.existsById(bookId)){
            bookRepository.deleteById(bookId);
            logger.info("The book was successfully removed from the database");
        }else{
            logger.warn("The book with given id does not exist");
            throw new IllegalArgumentException("Book with given id does not exist");
        }
    }

    public void deleteAllBooks() {
        bookRepository.deleteAll();
        logger.warn("All books have been removed from the database");
    }

    public BookDatabaseModel updateBookById(long bookId, BookWriteModel bookWriteModel) {
        if(bookRepository.findById(bookId).isPresent()){
            var bookToEdit = buildFromWriteToDatabaseModelWithId(bookId, bookWriteModel);
            return bookRepository.save(bookToEdit);
        }else{
            logger.warn("The book with given id does not exist");
            throw new IllegalArgumentException("Book with given id does not exist");
        }
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

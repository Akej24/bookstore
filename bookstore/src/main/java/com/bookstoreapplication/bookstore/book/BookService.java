package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.exception.BookNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
class BookService {

    private final BookRepository bookRepository;
    private final Logger logger = LoggerFactory.getLogger(BookService.class);

    public BookDatabaseModel addBookToDatabase(BookWriteModel bookWriteModel){
        var bookToSave = new BookDatabaseModel(
                bookWriteModel.getTitle(),
                bookWriteModel.getAuthor(),
                bookWriteModel.getReleaseDate(),
                bookWriteModel.getNumberOfPages(),
                bookWriteModel.isStatus(),
                bookWriteModel.getAvailablePieces(),
                bookWriteModel.getPrice()
        );
        var savedBook=  bookRepository.save(bookToSave);
        logger.info("Successfully added to the database");
        return savedBook;
    }

    public BookDatabaseModel getBookById(long bookId){
        if(bookRepository.findById(bookId).isPresent()){
            logger.info("Successively fetch a book from the database");
            return bookRepository.findById(bookId).get();
        }else{
            logger.warn("The book with given id does not exist");
            throw new BookNotFoundException();
        }
    }

}

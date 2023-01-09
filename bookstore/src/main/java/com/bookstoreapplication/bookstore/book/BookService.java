package com.bookstoreapplication.bookstore.book;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class BookService {

    private final BookRepository bookRepository;

    public void addBookToDatabase(BookWriteModel bookWriteModel){
        var bookToSave = new BookDatabaseModel(
                bookWriteModel.getTitle(),
                bookWriteModel.getAuthor(),
                bookWriteModel.getReleaseDate(),
                bookWriteModel.getNumberOfPages(),
                bookWriteModel.isStatus(),
                bookWriteModel.getAvailablePieces(),
                bookWriteModel.getPrice()
        );
        bookRepository.save(bookToSave);
    }

}

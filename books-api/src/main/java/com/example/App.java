package com.example;

import com.example.model.Book;
import com.example.service.BookService;
import com.example.service.BookServiceImpl;
import com.example.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static final String AUTHOR = "J.K. Rowling";
    private static final String RESOURCE = "src/main/resources/books.json";

    public static void main(String[] args) {
        try {
            List<Book> books = JsonUtils.readBooksFromJson(RESOURCE);
            BookService bookService = new BookServiceImpl();

            //Question 1.:
            List<Book> filteredBooks = bookService.filterBooksByPagesAndTitle(books);
            logger.info("ANSWER 1: ".concat(filteredBooks.stream().map(Book::getTitle).toList().toString()));

            //Question 2:
            List<Book> filteredBooksByAuthor = bookService.findBooksByAuthor(books, AUTHOR);
            logger.info("ANSWER 2: ".concat(filteredBooksByAuthor.stream().map(Book::getTitle).toList().toString()));

            //Question 3:
            logger.info("ANSWER 3: ".concat(bookService.getSortedTitles(books).toString()));
            logger.info("ANSWER 3: ".concat(bookService.countBooksByAuthor(books).toString()));

            //Question 4:
            bookService.convertTimestamps(books);
            logger.info("ANSWER 4: ".concat(books.stream().map(Book::getPublicationTimestamp).toList().toString()));

            //Question 6:
            bookService.calculateWordCount(books);
            Map<String, List<Book>> booksByAuthor = bookService.groupBooksByAuthor(books);
            bookService.getTitleWithWordCountByAuthor(booksByAuthor);

        } catch (Exception e) {
            logger.error("Error reading resources", e);        }
    }
}

package com.example;

import com.example.model.Book;
import com.example.service.BookService;
import com.example.service.BookServiceImpl;
import com.example.utils.JsonUtils;
import org.apache.commons.lang3.ObjectUtils;
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
            logger.info("ANSWER 1: {}", filteredBooks.stream().map(Book::getTitle).toList());
            //Question 2:
            List<Book> filteredBooksByAuthor = bookService.findBooksByAuthor(books, AUTHOR);
            logger.info("ANSWER 2: {}", filteredBooksByAuthor.stream().map(Book::getTitle).toList());

            //Question 3:
            logger.info("ANSWER 3: {}" ,bookService.getSortedTitles(books).toString());
            logger.info("ANSWER 3: {}" ,bookService.countBooksByAuthor(books).toString());

            //Question 4:
            bookService.convertTimestamps(books);
            logger.info("ANSWER 4: {}", books.stream().map(Book::getPublicationTimestamp).toList());

            //Question 5:
            logger.info("ANSWER 5: The average number of pages: {}", bookService.calculateAveragePages(books));
            Book mostPagesBook = bookService.findBookWithMostPages(books);
            Book leastPagesBook = bookService.findBookWithLeastPages(books);
            logger.info("ANSWER 5: The book with most pages is: {}",
                    !ObjectUtils.isEmpty(mostPagesBook) ? mostPagesBook.getTitle() : "No book found");
            logger.info("ANSWER 5: The book with least pages is: {}",
                    !ObjectUtils.isEmpty(leastPagesBook) ? leastPagesBook.getTitle() : "No book found");

            //Question 6:
            bookService.calculateWordCount(books);
            Map<String, List<Book>> booksByAuthor = bookService.groupBooksByAuthor(books);
            bookService.getTitleWithWordCountByAuthor(booksByAuthor);

            //Question 7:
            logger.info("ANSWER 7: {}", bookService.findDuplicateAuthors(books).toString());
            logger.info("ANSWER 7: {}", bookService.findBooksWithoutPublicationTimestamp(books)
                    .stream()
                    .map(Book::getTitle)
                    .toList());

            //Question 8:
            logger.info("ANSWER 8: ".concat(bookService.findMostRecentBook(books).getTitle()));

            //Question 9:
            bookService.exportBooksJsonToCsv(bookService.generateJsonWithTitlesAndAuthors(books),
                    "src/main/resources/books_output.csv");
            logger.info("ANSWER 9: Books have been exported to CSV at src/main/resources/books_output.csv");

        } catch (Exception e) {
            logger.error("Error reading resources", e);        }
    }
}

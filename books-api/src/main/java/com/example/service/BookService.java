package com.example.service;

import com.example.model.Book;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BookService {

    List<Book> filterBooksByPagesAndTitle(final List<Book> books);

    List<Book> findBooksByAuthor(final List<Book> books, final String author);

    List<String> getSortedTitles(final List<Book> books);

    Map<String, Long> countBooksByAuthor(final List<Book> books);

    void convertTimestamps(final List<Book> books);

    double calculateAveragePages(final List<Book> books);

    Book findBookWithMostPages(final List<Book> books);

    Book findBookWithLeastPages(final List<Book> books);

    void calculateWordCount(final List<Book> books);

    Map<String, List<Book>> groupBooksByAuthor(final List<Book> books);

    Set<String> findDuplicateAuthors(final List<Book> books);

    List<Book> findBooksWithoutPublicationTimestamp(final List<Book> books);

    Book findMostRecentBook(final List<Book> books);

    String generateJsonWithTitlesAndAuthors(final List<Book> books) throws JsonProcessingException, JsonProcessingException;

    void exportBooksToCsv(final List<Book> books, final String filePath) throws IOException;

    void getTitleWithWordCountByAuthor(final Map<String, List<Book>> booksByAuthor);
}

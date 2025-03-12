package com.example.service;

import com.example.model.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jesús Jiménez Ocaña
 */
public class BookServiceImpl implements BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    private static final String HARRY_TITLE = "harry";
    private static final int NUM_PAGES = 400;
    private static final String TITLE = "title";
    private static final String ID = "id";
    private static final String AUTHOR = "author";
    private static final String AUTHOR_NAME = "author_name";
    private static final String PAGES = "pages";

    /**
     * {@inheritDoc
     */
    @Override
    public List<Book> filterBooksByPagesAndTitle(final List<Book> books) {
        return books.stream()
                .filter(book -> book.getPages() > NUM_PAGES || book.getTitle().toLowerCase().contains(HARRY_TITLE)).toList();
    }

    /**
     * {@inheritDoc
     */
    @Override
    public List<Book> findBooksByAuthor(final List<Book> books, final String author) {
        return books.stream()
                .filter(book -> book.getAuthor().getName().equalsIgnoreCase(author)).toList();
    }

    @Override
    public List<String> getSortedTitles(final List<Book> books) {
        return books.stream()
                .map(Book::getTitle)
                .sorted().toList();
    }

    /**
     * {@inheritDoc
     */
    @Override
    public Map<String, Long> countBooksByAuthor(final List<Book> books) {
        return books.stream()
                .collect(Collectors.groupingBy(book -> book.getAuthor().getName(), Collectors.counting()));
    }

    /**
     * {@inheritDoc
     */
    @Override
    public void convertTimestamps(final List<Book> books) {
        books.forEach(book -> {
            if (!ObjectUtils.isEmpty(book.getPublicationTimestamp())) {
                long timestamp = Long.parseLong(book.getPublicationTimestamp());
                LocalDate date = Instant.ofEpochMilli(timestamp)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                book.setPublicationTimestamp(date.toString());
            }
        });
    }

    /**
     * {@inheritDoc
     */
    @Override
    public double calculateAveragePages(final List<Book> books) {
        return books.stream()
                .mapToInt(Book::getPages)
                .average()
                .orElse(0);
    }

    /**
     * {@inheritDoc
     */
    @Override
    public Book findBookWithMostPages(final List<Book> books) {
        return books.stream()
                .max(Comparator.comparingInt(Book::getPages))
                .orElse(null);
    }


    /**
     * {@inheritDoc
     */
    @Override
    public Book findBookWithLeastPages(final List<Book> books) {
        return books.stream()
                .min(Comparator.comparingInt(Book::getPages))
                .orElse(null);
    }

    @Override
    public void calculateWordCount(final List<Book> books) {
        books.forEach(book -> book.setWordCount(book.getPages() * 250));
    }

    @Override
    public Map<String, List<Book>> groupBooksByAuthor(final List<Book> books) {
        return books.stream()
                .collect(Collectors.groupingBy(book -> book.getAuthor().getName()));
    }

    @Override
    public Set<String> findDuplicateAuthors(final List<Book> books) {
        return books.stream()
                .map(book -> book.getAuthor().getName())
                .collect(Collectors.groupingBy(author -> author, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    @Override
    public List<Book> findBooksWithoutPublicationTimestamp(final List<Book> books) {
        return books.stream()
                .filter(book -> ObjectUtils.isEmpty(book.getPublicationTimestamp()))
                .collect(Collectors.toList());
    }

    @Override
    public Book findMostRecentBook(final List<Book> books) {
        return books.stream()
                .filter(book -> book.getPublicationTimestamp() != null)
                .max(Comparator.comparing(book -> LocalDate.parse(book.getPublicationTimestamp())))
                .orElse(null);
    }


    @Override
    public String generateJsonWithTitlesAndAuthors(final List<Book> books) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(
                books.stream()
                        .map(book -> Map.of(
                                ID, book.getId(),
                                TITLE, book.getTitle(),
                                AUTHOR_NAME, book.getAuthor().getName(),
                                PAGES, book.getPages()
                        )).toList()
        );
    }


    @Override
    public void exportBooksJsonToCsv(final String json, final String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> books = objectMapper.readValue(json, new TypeReference<>() {
        });

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                     .withHeader(ID, TITLE, AUTHOR_NAME, PAGES))) {
            for (Map<String, Object> book : books) {
                csvPrinter.printRecord(book.get(ID), book.get(TITLE), book.get(AUTHOR_NAME), book.get(PAGES));
            }
        }
    }


    public void getTitleWithWordCountByAuthor(final Map<String, List<Book>> booksByAuthor) {
        booksByAuthor.forEach((author, booksList) -> {
            logger.info("ANSWER 6: ".concat(AUTHOR.concat(": {}")), author);
            booksList.forEach(book -> logger.info(TITLE.concat(" : {}, WordCount: {}"), book.getTitle(), book.getWordCount()));
        });
    }
}

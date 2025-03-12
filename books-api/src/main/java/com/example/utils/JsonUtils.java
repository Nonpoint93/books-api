package com.example.utils;

import com.example.model.Book;

import java.io.File;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    public static List<Book> readBooksFromJson(String filePath) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(
                new File(filePath),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Book.class)
        );
    }
}

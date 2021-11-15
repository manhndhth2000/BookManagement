package com.homework.service;

import com.homework.model.Book;
import com.homework.model.inventory.BookInventory;
import com.homework.model.request.BookRequest;

import java.util.HashMap;
import java.util.List;

public interface BookService {
    List<Book> createData();

    List<Book> sortByBookTitle();

    List<Book> searchByTitle(String subTitle);

    List<Book> getListOutOfStock();

    String buyBook(BookRequest request);

    HashMap<String, List<BookInventory>> getListInventory();
}

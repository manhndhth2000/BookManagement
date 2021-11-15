package com.homework.controller;

import com.homework.model.Book;
import com.homework.model.inventory.BookInventory;
import com.homework.model.request.BookRequest;
import com.homework.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BooksController {

    public BookService bookService;

    public BooksController(@Autowired BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/create")
    public List<Book> addList() {
        return bookService.createData();
    }

    @GetMapping("/sort")
    public List<Book> sortList() {
        return bookService.sortByBookTitle();
    }

    @GetMapping("/search/{subTitle}")
    public List<Book> searchBook(@PathVariable String subTitle) {
        return bookService.searchByTitle(subTitle);
    }

    @GetMapping("/list")
    public List<Book> getListOutOfStock() {
        return bookService.getListOutOfStock();
    }

    @PostMapping("/buy")
    public String orderBook(@RequestBody BookRequest request) {
        return bookService.buyBook(request);
    }

    @GetMapping("/inventory")
    public Map<String, List<BookInventory>> getListInventory() {
        return bookService.getListInventory();
    }
}

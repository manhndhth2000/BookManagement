package com.homework.service.impl;

import com.github.javafaker.Faker;
import com.homework.event.BookEvent;
import com.homework.constant.Constant;
import com.homework.exception.BusinessCode;
import com.homework.model.Book;
import com.homework.model.inventory.BookInventory;
import com.homework.model.request.BookRequest;
import com.homework.service.BookService;
import com.homework.springbootaop.BenchMark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private static int sequence = 0;

    private static List<Book> books = new ArrayList<>();

    private HashMap<String, List<BookInventory>> bookInventories = new HashMap<>();

    private ApplicationEventPublisher applicationEventPublisher;

    public BookServiceImpl(@Autowired ApplicationEventPublisher applicationEventPublisher){
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public synchronized int getSequence(int sequence) {
        sequence += 1;
        return sequence;
    }

    public List<BookInventory> addBookInventory(Faker faker, String bookId) {
        List<BookInventory> list = new ArrayList<>();
        var bookInv = new BookInventory();
        bookInv.setBookId(bookId);
        bookInv.setAmount(faker.number().numberBetween(0, 100));
        bookInv.setUpdateDate(LocalDateTime.now());
        bookInv.setId(getSequence(sequence));
        list.add(bookInv);
        return list;
    }

    @Override
    @BenchMark
    public List<Book> createData() {
        Faker faker = new Faker();
        for (int i = 0; i < 1000; i++) {
            var book = new Book();
            book.setAuthor(faker.book().author());
            book.setTitle(faker.book().title());
            book.setId(faker.regexify("[a-z1-9]{8}"));
            bookInventories.put(book.getId(), addBookInventory(faker, book.getId()));
            books.add(book);
        }
        return books;
    }

    @Override
    @BenchMark
    public List<Book> sortByBookTitle() {
        var list = new ArrayList<>(books);
        list.sort(Comparator.comparing(Book::getTitle));
        return list;
    }

    @Override
    @BenchMark
    public List<Book> searchByTitle(String subTitle) {
        List<Book> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            var book = books.get(i);
            if (book.getTitle().contains(subTitle)) {
                list.add(book);
            } else {
                throw new BusinessCode(Constant.ErrorCode.BOOK_NOT_FOUND);
            }
        }
        return list;
    }

    @Override
    @BenchMark
    public List<Book> getListOutOfStock() {
        List<Book> book = new ArrayList<>();
        for (Book bookEntity : books) {
            var bookInv = bookInventories.get(bookEntity.getId());
            if (bookInv.get(bookInv.size() - 1).getAmount() == 0) {
                book.add(bookEntity);
            }
        }
        return book;
    }

    @Override
    @BenchMark
    public String buyBook(BookRequest request) {
        var bookId = request.getBookID();
        var bookInv = bookInventories.get(bookId);
        if (bookInventories.containsKey(bookId)) {
            var amount = bookInv.get(bookInv.size() - 1).getAmount() - request.getAmount();
            if (amount > 0) {
                bookInv.get(bookInv.size() - 1).setAmount(amount);
                bookInv.get(bookInv.size() - 1).setUpdateDate(LocalDateTime.now());
                return "Success";
            } else {
                applicationEventPublisher.publishEvent(new BookEvent(this, bookId, request.getAmount()));
                return "We are ordering more books. Pls wait!";
            }
        } else {
            throw new BusinessCode(Constant.ErrorCode.BOOK_NOT_FOUND);
        }
    }

    @Override
    public HashMap<String, List<BookInventory>> getListInventory() {
        return bookInventories;
    }

    @EventListener
    public void orderBook(BookEvent bookEvent) {
        addBookInventory(bookEvent.getBookId(), bookEvent.getQuantity());
    }

    private List<Book> getListByAmount() {
        var list = new ArrayList<Book>();
        for (Book book : books) {
            var bookInv = bookInventories.get(book.getId());
            if (bookInv.get(bookInv.size() - 1).getAmount() == 1) {
                list.add(book);
            }
        }

        return list;
    }

    private void addBookInventory(String bookId, int quantity) {
        var bookInv = bookInventories.get(bookId);
        if (bookInventories.containsKey(bookId)) {
            var index = bookInv.size() - 1;
            var amount = bookInv.get(index).getAmount();

            bookInv.get(index).setAmount(amount + quantity);
            bookInv.get(index).setUpdateDate(LocalDateTime.now());
        } else {
            throw new BusinessCode(Constant.ErrorCode.BOOK_NOT_FOUND);
        }
    }

    @Scheduled(fixedDelay = 10000)
    @BenchMark
    public void addBook() {
        var scheduleList = getListByAmount();
        for (Book book : scheduleList) {
            addBookInventory(book.getId(), 5);
        }
    }
}

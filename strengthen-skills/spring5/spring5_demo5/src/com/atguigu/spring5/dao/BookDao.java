package com.atguigu.spring5.dao;

import com.atguigu.spring5.entity.Book;

import java.util.List;

/**
 * @author wei.peng
 */
public interface BookDao {
    void add(Book book);

    void updateBook(Book book);

    void delete(String id);

    int selectCount();

    Book findBookInfo(String id);

    List<Book> findAllBook();

    void batchAddBook(List<Object[]> batchArgs);

    void batchUpdateBook(List<Object[]> batchArgs);

    void batchDeleteBook(List<Object[]> batchArgs);
}

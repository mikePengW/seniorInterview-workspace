package com.atguigu.spring5.service;

import com.atguigu.spring5.dao.BookDao;
import com.atguigu.spring5.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wei.peng
 */
@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    public void addBook(Book book) {
        bookDao.add(book);
    }

    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    public void deleteBook(String id) {
        bookDao.delete(id);
    }

    public int findCount() {
        return bookDao.selectCount();
    }

    public Book findOne(String id) {
        return bookDao.findBookInfo(id);
    }

    public List<Book> findAll() {
        return bookDao.findAllBook();
    }

    public void batchAdd(List<Object[]> batchArgs) {
        bookDao.batchAddBook(batchArgs);
    }

    public void batchUpdate(List<Object[]> batchArgs) {
        bookDao.batchUpdateBook(batchArgs);
    }

    public void batchDelete(List<Object[]> batchArgs) {
        bookDao.batchDeleteBook(batchArgs);
    }

}

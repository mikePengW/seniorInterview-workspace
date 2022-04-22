package com.atguigu.spring5.test;

import com.atguigu.spring5.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei.peng
 */
public class TestBook {

    @Test
    public void testJdbcTemplate() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        BookService bookService = context.getBean("bookService", BookService.class);

        // 添加
//        Book book = new Book();
//        book.setUserId("1");
//        book.setUsername("java");
//        book.setUstatus("a");
//        bookService.addBook(book);

        // 修改
//        Book book = new Book();
//        book.setUserId("1");
//        book.setUsername("javaupup");
//        book.setUstatus("atguigu");
//        bookService.updateBook(book);

        // 删除
//        bookService.deleteBook("1");

        // 查询返回某个值
//        int count = bookService.findCount();
//        System.out.println(count);

        // 查询返回对象
//        Book book = bookService.findOne("1");
//        System.out.println(book);

//        List<Book> all = bookService.findAll();
//        System.out.println(all);

//        List<Object[]> batchArgs = new ArrayList<>();
//        Object[] o1 = {"3","java","c"};
//        Object[] o2 = {"4","c++","d"};
//        Object[] o3 = {"5","MySQL","e"};
//        batchArgs.add(o1);
//        batchArgs.add(o2);
//        batchArgs.add(o3);
//        bookService.batchAdd(batchArgs);

//        List<Object[]> batchArgs = new ArrayList<>();
//        Object[] o1 = {"javaupup", "c3", "3"};
//        Object[] o2 = {"c++upup", "d4", "4"};
//        Object[] o3 = {"MySQLupup", "e5", "5"};
//        batchArgs.add(o1);
//        batchArgs.add(o2);
//        batchArgs.add(o3);
//        bookService.batchUpdate(batchArgs);

        List<Object[]> batchArgs = new ArrayList<>();
        Object[] o1 = {"3"};
        Object[] o2 = {"4"};
        Object[] o3 = {"5"};
        batchArgs.add(o1);
        batchArgs.add(o2);
        batchArgs.add(o3);
        bookService.batchDelete(batchArgs);
    }
}

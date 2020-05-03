package me.zhoudongyu.service;

import me.zhoudongyu.entity.Book;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface LibraryService {

    /**
     * 获取我的图书借阅信息
     */
    List<Book> getMyLibraryInfo();

    /**
     * 获取图书借阅排行榜
     *
     * @param req HttpServletRequest
     */
    List<Book> getBorrowList(HttpServletRequest req);

    /**
     * 获取图书详情信息
     *
     * @param req HttpServletRequest
     * @param id  图书id
     */
    Book getBookDetail(HttpServletRequest req, String id);

    /**
     * 获取搜索图书信息
     *
     * @param req      HttpServletRequest
     * @param bookName 书名
     * @param page     分页信息
     */
    List<Book> getSearchList(HttpServletRequest req, String bookName, String page);
}

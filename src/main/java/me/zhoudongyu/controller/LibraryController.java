package me.zhoudongyu.controller;

import me.zhoudongyu.entity.Book;
import me.zhoudongyu.service.LibraryService;
import me.zhoudongyu.utils.HttpResult;
import me.zhoudongyu.utils.Messages;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/library")
public class LibraryController {

    @Resource(name = "libraryService")
    private LibraryService libraryService;

    @RequestMapping("getMyLibraryInfo.do")
    @ResponseBody
    public HttpResult getMyLibraryInfo(HttpServletRequest req, HttpResult httpResult) {
        List<Book> books = null;
        try {
            books = libraryService.getMyLibraryInfo();
            return httpResult.setSuccess(true).setData(books).setMessage(Messages.QUERY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return httpResult.setSuccess(false).setMessage(Messages.QUERY_FAILED);

        }
    }

    @RequestMapping("getBorrowList.do")
    @ResponseBody
    public HttpResult getBorrowList(HttpServletRequest req, HttpResult httpResult) {
        List<Book> books = null;
        try {
            books = libraryService.getBorrowList(req);
            return httpResult.setSuccess(true).setData(books).setMessage(Messages.QUERY_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            return httpResult.setSuccess(false).setMessage(Messages.QUERY_FAILED);
        }
    }

    @RequestMapping("getBookDetail.do")
    @ResponseBody
    public HttpResult getBookDetail(HttpServletRequest req, HttpResult httpResult, String id) {
        Book book = null;
        try {
            book = libraryService.getBookDetail(req, id);
            return httpResult.setSuccess(true).setData(book).setMessage(Messages.QUERY_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            return httpResult.setSuccess(false).setMessage(Messages.QUERY_FAILED);
        }
    }

    @RequestMapping("getSearchList.do")
    @ResponseBody
    public HttpResult getSearchList(HttpServletRequest req, HttpResult httpResult, String bookName, String page) {
        List<Book> books = null;
        try {
            books = libraryService.getSearchList(req, bookName, page);
            return httpResult.setSuccess(true).setData(books).setMessage(Messages.QUERY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return httpResult.setSuccess(false).setMessage(Messages.QUERY_FAILED);
        }
    }
}

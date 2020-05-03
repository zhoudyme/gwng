package me.zhoudongyu.entity;

import java.util.List;

/**
 * 图书实体类
 */
public class Book {
    private String bookId;    //id
    private String bookName;    //书名
    private String author;    //作者
    private String publisher;    //出版社
    private String describe;    //描述
    private String library;    //所在馆
    private String borrowBooksTime;    //借阅时间
    private String returnBooksTime;    //应还时间
    private String renewCount;    //已续借数
    private String maxRenewCount;    //最大续借数
    private String overdue;    //是否过期
    private String borrowCount;    //借阅次数
    private List<BookHolding> holding;    //藏书情况

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public String getBorrowBooksTime() {
        return borrowBooksTime;
    }

    public void setBorrowBooksTime(String borrowBooksTime) {
        this.borrowBooksTime = borrowBooksTime;
    }

    public String getReturnBooksTime() {
        return returnBooksTime;
    }

    public void setReturnBooksTime(String returnBooksTime) {
        this.returnBooksTime = returnBooksTime;
    }

    public String getRenewCount() {
        return renewCount;
    }

    public void setRenewCount(String renewCount) {
        this.renewCount = renewCount;
    }

    public String getMaxRenewCount() {
        return maxRenewCount;
    }

    public void setMaxRenewCount(String maxRenewCount) {
        this.maxRenewCount = maxRenewCount;
    }

    public String getOverdue() {
        return overdue;
    }

    public void setOverdue(String overdue) {
        this.overdue = overdue;
    }

    public String getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(String borrowCount) {
        this.borrowCount = borrowCount;
    }

    public List<BookHolding> getHolding() {
        return holding;
    }

    public void setHolding(List<BookHolding> holding) {
        this.holding = holding;
    }
}

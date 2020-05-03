package me.zhoudongyu.entity;

/**
 * 图书馆藏信息实体类
 */
public class BookHolding {
    private String accessionNumber;    //索取号
    private String library;    //原始馆藏地
    private String state;    //书刊状态
    private String returnBooksTime;    //应还日期

    public String getAccessionNumber() {
        return accessionNumber;
    }

    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReturnBooksTime() {
        return returnBooksTime;
    }

    public void setReturnBooksTime(String returnBooksTime) {
        this.returnBooksTime = returnBooksTime;
    }
}

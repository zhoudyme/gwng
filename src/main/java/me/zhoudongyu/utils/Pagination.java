package me.zhoudongyu.utils;

import java.io.Serializable;

public class Pagination implements Serializable {

    private static final long serialVersionUID = -5111149568542860181L;

    private Long currPage = 1L;

    private Long pageSize = 20L;

    private Long totalPages;

    private Long totalResults;

    public Long getCurrPage() {
        return currPage;
    }

    public Pagination setCurrPage(Long currPage) {
        this.currPage = currPage < 1L ? 1L : currPage;
        return this;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public Pagination setPageSize(Long pageSize) {
        this.pageSize = pageSize > 0L ? pageSize : 20L;
        return this;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public Pagination setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public Pagination setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
        this.totalPages = totalResults % pageSize > 0 ? totalResults / pageSize + 1 : totalResults / pageSize;
        return this;
    }

}

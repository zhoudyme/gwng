package me.zhoudongyu.service.impl;

import me.zhoudongyu.config.UrlConfig;
import me.zhoudongyu.core.consts.RequestType;
import me.zhoudongyu.entity.Book;
import me.zhoudongyu.entity.BookHolding;
import me.zhoudongyu.service.LibraryService;
import me.zhoudongyu.utils.HttpClientHelper;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Service("libraryService")
public class LibraryServiceImpl implements LibraryService {
    @Override
    public List<Book> getMyLibraryInfo() {
        List<Book> bookList = new ArrayList<>();
        //获取查询我的图书借阅的Url
        String myLibraryInfoUrl = UrlConfig.MY_LIBRARY_INFO_URL;
        try {
            //发起Get请求，查询我的图书借阅信息
            Document doc = HttpClientHelper.loadDocumentFromURL(myLibraryInfoUrl, RequestType.GET, null);
            Elements elements = doc.select("form[name=mylibberestore]").select("tr");
            elements.remove(0);    //移除提示信息
            //处理查询结果，放入Book对象中
            for (Element element : elements) {
                Book book = new Book();
                Elements e = element.select("td");
                book.setBookName(e.get(1).select("a").text());
                book.setLibrary(e.get(4).text());
                book.setBorrowBooksTime(e.get(5).text());
                book.setReturnBooksTime(e.get(6).text());
                book.setRenewCount(e.get(7).text().substring(0, e.get(7).text().indexOf("/")));
                book.setMaxRenewCount(e.get(7).text().substring(e.get(7).text().indexOf("/") + 1));
                book.setOverdue(e.get(8).text());
                bookList.add(book);
            }
            return bookList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Book> getBorrowList(HttpServletRequest req) {
        List<Book> bookList = new ArrayList<>();
        //获取查询图书借阅排行的Url
        String myLibraryInfoUrl = UrlConfig.BORROW_LIST_URL;
        try {
            //发起Get请求，查询图书借阅排行信息
            Document doc = HttpClientHelper.loadDocumentFromURL(myLibraryInfoUrl, RequestType.GET, null);
            Elements elements = doc.select("form[name=LIST]").select("tr");
            //处理查询结果，放入Book对象中
            for (int i = 0; i < 3; i++) {    //移除提示信息
                elements.remove(0);
            }
            for (int i = 0; i < 10; i++) {
                Book book = new Book();
                Elements e = elements.get(i).select("td");
                book.setBookId(e.get(1).html().substring(e.get(1).html().indexOf("(") + 1, e.get(1).html().indexOf(")")));
                book.setBookName(e.get(1).select("a").text());
                book.setAuthor(e.get(2).text());
                book.setBorrowCount(e.get(6).text());
                bookList.add(book);
            }
            //将图书借阅排行信息放入Session，避免查看图书详情和搜索的时候重复查询
            HttpSession session = req.getSession();
            session.setAttribute("borrowList", doc);
            return bookList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Book getBookDetail(HttpServletRequest req, String id) {
        HttpClient httpClient = HttpClientHelper.getHttpClient();
        HttpPost httpPost = null;
        //获取查询图书详情的Url
        String bookDetailUrl = UrlConfig.BOOKDETAIL_URL;
        //填充需要提交的表单
        List<NameValuePair> nvps = new ArrayList<>();
        HttpResponse response = null;
        Document document = null;
        List<Book> books;
        try {
            //通过Session获取图书排行的信息
            HttpSession session = req.getSession();
            Document borrowList = (Document) session.getAttribute("borrowList");
            String filter = borrowList.getElementsByTag("input").get(1).attr("value");
            httpPost = HttpClientHelper.getHttpPost(bookDetailUrl);
            nvps.add(new BasicNameValuePair("bookid", id));     //填充bookid
            nvps.add(new BasicNameValuePair("cmdACT", "query.bookdetail"));     //填充cmdACT
            nvps.add(new BasicNameValuePair("FILTER", filter));     //填充FILTER
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));    //设置字符
            //发起post请求，查询图书详情信息
            response = httpClient.execute(httpPost);
            document = Jsoup.parse(EntityUtils.toString(response.getEntity(), Consts.UTF_8));
            httpPost.abort();
            //处理查询结果，放入Book对象中
            Elements bookElements = document.select("table[cellspacing=4]");    //获取存放课程表信息的表格
            Elements holdingElements = document.select("#queryholding").select("table").get(1).select("tr");
            holdingElements.remove(0);
            Book book = new Book();
            List<BookHolding> holding = new ArrayList();
            book.setBookName(bookElements.select("tr").get(0).select("td").get(1).select("a").text());
            book.setAuthor(bookElements.select("tr").get(0).select("td").get(1).text().substring(bookElements.select("tr").select("td").get(1).text().indexOf("/") + 1));
            if ("附注 ".equals(bookElements.select("tr").get(4).select("td").get(0).text())) {
                book.setDescribe(bookElements.select("tr").get(4).select("td").get(1).text());
            } else {
                book.setDescribe(bookElements.select("tr").get(5).select("td").get(1).text());
            }
            for (Element e : holdingElements) {
                BookHolding bookHolding = new BookHolding();
                bookHolding.setLibrary(e.select("td").get(3).text());
                bookHolding.setAccessionNumber(e.select("td").get(1).text());
                bookHolding.setState(e.select("td").get(5).text());
                bookHolding.setReturnBooksTime(e.select("td").get(6).text());
                holding.add(bookHolding);
            }
            book.setHolding(holding);
            return book;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Book> getSearchList(HttpServletRequest req, String bookName, String page) {
        HttpClient httpClient = HttpClientHelper.getHttpClient();
        //获取图书搜索的Url
        String bookDetailUrl = UrlConfig.BOOKDETAIL_URL;
        HttpPost httpPost = null;
        //填充需要提交的表单
        List<NameValuePair> nvps = new ArrayList<>();
        HttpResponse response = null;
        Document document = null;
        List<Book> books;
        try {
            httpPost = HttpClientHelper.getHttpPost(bookDetailUrl);
            //通过Session获取图书排行的信息
            HttpSession session = req.getSession();
            String filter = (String) session.getAttribute("filter");
            nvps.add(new BasicNameValuePair("FIELD1", "TITLE"));     //填充FIELD1
            nvps.add(new BasicNameValuePair("cmdACT", "simple.list"));     //填充cmdACT
            nvps.add(new BasicNameValuePair("VAL1", bookName));     //VAL1
            if (null != page && !"undefined".equals(page)) {
                nvps.add(new BasicNameValuePair("FILTER", filter));     //填充密码
                nvps.add(new BasicNameValuePair("PAGE", String.valueOf(Integer.parseInt(page) - 1)));     //填充密码
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));    //设置字符
            //发起post请求，查询搜索图书的信息
            response = httpClient.execute(httpPost);
            document = Jsoup.parse(EntityUtils.toString(response.getEntity(), Consts.UTF_8));
            httpPost.abort();
            //处理查询结果，放入Book对象中
            String prefilter = document.select("input[name=PREFILTER]").attr("value");
            session.setAttribute("filter", prefilter);
            Elements bookElements = document.select("table[cellpadding=4]");    //获取存放课程表信息的表格
            Elements holdingElements = bookElements.select("tr");
            holdingElements.remove(0);
            books = new ArrayList<>();
            for (Element e : holdingElements) {
                Book book = new Book();
                book.setBookId(e.select("td").get(1).html().substring(e.select("td").get(1).html().indexOf("(") + 1,
                        e.select("td").get(1).html().indexOf(")")));
                book.setBookName(e.select("td").get(1).text());
                book.setAuthor(e.select("td").get(2).text());
                book.setPublisher(e.select("td").get(3).text());
                books.add(book);
            }
            return books;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
package com.reins.bookstore.endpoint;

import com.reins.bookstore.entity.Book;
import com.reins.bookstore.service.BookService;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import io.spring.guides.gs_producing_web_service.SearchBookByDescriptionResponse;
import io.spring.guides.gs_producing_web_service.SearchBookByDescriptionRequest;

import javax.jws.WebParam;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Endpoint
public class BookEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    @Autowired
    BookService bookService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SearchBookByDescriptionRequest")
    @ResponsePayload
    public SearchBookByDescriptionResponse searchBookByDescription(@RequestPayload SearchBookByDescriptionRequest request) throws IOException, ParseException {
        System.out.println("请求进来了");
        /*List<Book> list = new ArrayList<>();
        String keyword = request.getKeyword();
        if(keyword == "")
        {
            list = bookService.getBooks();
        }
        else
        {
            list = bookService.searchBookByDescription(keyword);
        }
        SearchBookByDescriptionResponse response = new SearchBookByDescriptionResponse();
        List<SearchBookByDescriptionResponse.Param.Book> bookList = new ArrayList<>();
        //response.setCountry(countryRepository.findCountry(request.getName()));
        for(int i = 0; i < list.size(); ++i)
        {
            SearchBookByDescriptionResponse.Param.Book tmp = new SearchBookByDescriptionResponse.Param.Book();
            Book b = list.get(i);
            tmp.setDescription(b.getDescription());
            tmp.setBookId(b.getBookId());
            tmp.setAuthor(b.getAuthor());
            tmp.setAvailable(b.getAvailable());
            tmp.setInventory(b.getInventory());
            tmp.setImage1(b.getImage1());
            tmp.setImage2(b.getImage2());
            tmp.setIsbn(b.getIsbn());
            tmp.setTitle(b.getTitle());
            tmp.setSales(b.getSales());
            tmp.setType(b.getType());
            tmp.setPrice(b.getPrice());
            bookList.add(tmp);
        }
        SearchBookByDescriptionResponse.Param param = new SearchBookByDescriptionResponse.Param();
        param.setBook(bookList);
        response.setParam(param);
        return response;*/
        SearchBookByDescriptionResponse response = new SearchBookByDescriptionResponse();
        return response;
    }
}

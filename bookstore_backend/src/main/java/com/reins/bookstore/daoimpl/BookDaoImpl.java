package com.reins.bookstore.daoimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.reins.bookstore.dao.BookDao;
import com.reins.bookstore.entity.Book;
import com.reins.bookstore.entity.Comment;
import com.reins.bookstore.entity.OrderInfo;
import com.reins.bookstore.entity.OrderItem;
import com.reins.bookstore.repository.BookRepository;
import com.reins.bookstore.repository.CommentRepository;
import com.reins.bookstore.repository.OrderInfoRepository;
import com.reins.bookstore.repository.OrderItemRepository;
import com.reins.bookstore.utils.redisutils.RedisUtil;
import com.reins.bookstore.utils.sessionutils.SessionUtil;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }



    @Autowired
    void setOrderInfoRepository(OrderInfoRepository orderInfoRepository) {
        this.orderInfoRepository = orderInfoRepository;
    }

    @Override
    public Book findOne(Integer id)
    {
        Book book = null;
        Object b = redisUtil.get("book" + id);
        if (b == null) {
            System.out.println("Book: " + id + " is not in Redis");
            book = bookRepository.findById(id).get();
            redisUtil.set("book" + id, JSONArray.toJSON(book));
        } else {
            book = JSONArray.parseObject(b.toString(), Book.class);
            System.out.println("Book: " + id + " is in Redis");

        }
        return book;
    }



    @Override
    public List<Book> getBooks()
    {
        return bookRepository.getBooks();
    }

    @Override
    public List<Book> getBooksRankedBySales()
    {
        return bookRepository.getBooksRankedBySales();
    }

    @Override
    public List<Book> getSingleOrderBook(Integer bookId) {
        return bookRepository.getSingleOrderBook(bookId);
    }

    @Override
    public List<Book> getBooksByKeyword(String keyword) {
        return bookRepository.getBooksByKeyword(keyword);
    }

    @Override
    public Boolean deleteBookByBookId(Integer bookId) {
        return bookRepository.deleteBookByBookId(bookId) > 0;
    }

    @Override
    public Boolean undercarriageBookByBookId(Integer bookId) {
        return bookRepository.undercarriageBookByBookId(bookId) > 0;
    }

    @Override
    public Boolean putOnSale(Integer bookId) {
        return bookRepository.putOnSale(bookId) > 0;
    }

    @Override
    //@Transactional
    public void modifyBook(Map<String, String> bookInfo) {
        System.out.println(bookInfo);
        Integer bookId = Integer.parseInt(bookInfo.get("bookId"));
        String title = bookInfo.get("title");
        String image1 = bookInfo.get("image1");
        String image2 = bookInfo.get("image2");
        String author = bookInfo.get("author");
        String description = bookInfo.get("description");
        Integer inventory = Integer.parseInt(bookInfo.get("inventory"));
        String type = bookInfo.get("type");
        String isbn = bookInfo.get("isbn");
        Integer price = Integer.parseInt(bookInfo.get("price"));
        Integer sales = Integer.parseInt(bookInfo.get("sales"));
        Integer available = Integer.parseInt(bookInfo.get("available"));

        bookRepository.modifyBookWithBookId(bookId,  isbn,  title,  author,  type,  price, description, inventory, sales, image1, image2, available);
    }

    @Override
    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    public void placeOrder(Integer bookId, Integer purchaseNumber)
    {
        bookRepository.placeOrder(bookId, purchaseNumber);
    }

    @Override
    @Transactional
    public List<Book> getBooksRankedBySalesByTime(Date start, Date end)
    {
        System.out.println("getBooksRankedBySalesByTime");
        List<Book> tmp = bookRepository.getBooks();
        String json = JSON.toJSONString(tmp, SerializerFeature.DisableCircularReferenceDetect);
        List<Book> bookList = JSON.parseArray(json, Book.class);
        /*System.out.println(bookList);
        System.out.println(bookList.toArray().length);*/
        int[] salesList = new int[bookList.get(bookList.size() - 1).getBookId() + 1];
        ArrayList<OrderInfo> orderList = orderInfoRepository.getOrdersInRangeForManager(start, end);
        if(orderList.toArray().length > 0)
        {
            for(OrderInfo info: orderList)
            {

                List<OrderItem> orderItemList = info.getOrders();
                for(OrderItem item: orderItemList)
                {
                    salesList[item.getBookId()] += item.getBookNumber();
                }
            }
        }
        for(Book b: bookList)
        {
            b.setSales(salesList[b.getBookId()]);
        }
        Collections.sort(bookList, new Comparator<Book>() {
            @Override
            public int compare(Book b1, Book b2)
            {
                int diff = b1.getSales() - b2.getSales();
                if(diff > 0)
                    return -1;
                else if(diff < 0)
                    return 1;
                else
                    return 0;
            }
        });
        System.out.println(bookList);
        return bookList;
    }

    @Override
    public List<Book> getConsumption() {
        List<Book> tmp = bookRepository.getBooks();
        String json = JSON.toJSONString(tmp, SerializerFeature.DisableCircularReferenceDetect);
        List<Book> bookList = JSON.parseArray(json, Book.class);
        int[] salesList = new int[bookList.toArray().length + 1];
        /*for(Book b:bookList)
        {
            b.setSales(0);
        }*/
        Integer userId = SessionUtil.getUserId();

        ArrayList<OrderInfo> orderList = orderInfoRepository.getAllOrders(userId);
        for(OrderInfo info: orderList)
        {
            List<OrderItem> orderItemList = info.getOrders();
            for(OrderItem item: orderItemList)
            {
                salesList[item.getBookId()] += item.getBookNumber();
            }
        }
        for(Book b:bookList)
        {
            b.setSales(salesList[b.getBookId()]);
        }
        return bookList;
    }

    @Override
    @Transactional
    public List<Book> getConsumptionByTime(Date start, Date end)
    {
        List<Book> tmp = bookRepository.getBooks();
        String json = JSON.toJSONString(tmp, SerializerFeature.DisableCircularReferenceDetect);
        List<Book> bookList = JSON.parseArray(json, Book.class);
        int[] salesList = new int[bookList.toArray().length + 1];
        Integer userId = SessionUtil.getUserId();

        ArrayList<OrderInfo> orderList = orderInfoRepository.getOrdersInRange(userId, start, end);
        for(OrderInfo info: orderList)
        {
            List<OrderItem> orderItemList = info.getOrders();
            for(OrderItem item: orderItemList)
            {
                salesList[item.getBookId()] += item.getBookNumber();
            }
        }
        for(Book b:bookList)
        {
            b.setSales(salesList[b.getBookId()]);
        }
        return bookList;
    }

    @Override
    public Integer getBookPriceById(Integer id)
    {
        Book book = null;
        Object b = redisUtil.get("book" + id);
        if (b == null) {
            System.out.println("Book: " + id + " is not in Redis");
            book = bookRepository.findById(id).get();
            redisUtil.set("book" + id, JSONArray.toJSON(book));

        } else {
            book = JSONArray.parseObject(b.toString(), Book.class);
            System.out.println("Book: " + id + " is in Redis");
        }
        return book.getPrice();
    }

    @Override
    public Integer getBookInventoryById(Integer id)
    {
        Book book = null;
        Object b = redisUtil.get("book" + id);
        if (b == null) {
            System.out.println("Book: " + id + " is not in Redis");
            book = bookRepository.findById(id).get();
            redisUtil.set("book" + id, JSONArray.toJSON(book));

        } else {
            book = JSONArray.parseObject(b.toString(), Book.class);
            System.out.println("Book: " + id + " is in Redis");
        }
        return book.getInventory();
    }

    @Override
    public boolean commentBook(Integer bookId, Integer userId, String comment)
    {
        commentRepository.save(new Comment(bookId, userId, comment));
        return true;
    }

    @Override
    public List<Comment> searchCommentByBookId(Integer bookId)
    {
        return commentRepository.findByBookId(bookId);
    }

    @Override
    public List<Book> searchBookByType(String type)
    {
        return bookRepository.findBookByType(type);
    }
}

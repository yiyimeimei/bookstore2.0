package com.reins.bookstore.serviceimpl;

import com.reins.bookstore.dao.BookDao;
import com.reins.bookstore.dao.BookTypeDao;
import com.reins.bookstore.dao.OrderInfoDao;
import com.reins.bookstore.entity.Book;
import com.reins.bookstore.entity.BookType;
import com.reins.bookstore.entity.Comment;
import com.reins.bookstore.service.BookService;
import com.reins.bookstore.utils.luceneutils.MyIKAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.store.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * @ClassName BookServiceImpl
 * @Description the Implement of BookService
 * @Author thunderBoy
 * @Date 2019/11/6 16:04
 */

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private OrderInfoDao orderInfoDao;

    @Autowired
    private BookTypeDao bookTypeDao;

    @Override
    public Book findBookById(Integer id){
        return bookDao.findOne(id);
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    @Override
    public List<Book> getBooksRankedBySales() {
        return bookDao.getBooksRankedBySales();
    }

    @Override
    public List<Book> getSingleOrderBook(Integer bookId) {

        System.out.println("servicesssfsdfsdf");

        return bookDao.getSingleOrderBook(bookId);
    }

    @Override
    public List<Book> getBooksByKeyword(String keyword) {
        return bookDao.getBooksByKeyword(keyword);
    }


    @Override
    @Transactional
    public Boolean deleteBooks(ArrayList<Integer> bookIdList)
    {
        for(Integer bookId: bookIdList) {
            System.out.println("orderInfoDao.searchOrderItemByBookId(bookId).toArray().length");
            if(orderInfoDao.searchOrderItemByBookId(bookId).toArray().length > 0)
            {
                return false;
            }
        }
        for (Integer bookId : bookIdList) {
            Boolean flag = bookDao.deleteBookByBookId(bookId);
            System.out.println(flag);
            if (!flag)
                return false;
        }
        return true;
    }

    @Override
    public Boolean undercarriage(ArrayList<Integer> bookIdList) {
        for (Integer bookId : bookIdList) {
            if (!bookDao.undercarriageBookByBookId(bookId))
                return false;
        }
        return true;
    }

    @Override
    public Boolean putOnSale(ArrayList<Integer> bookIdList) {
        for (Integer bookId : bookIdList) {
            if (!bookDao.putOnSale(bookId))
                return false;
        }
        return true;
    }


    @Override
    public void modifyBook(Map<String, String> bookInfo) {
        bookDao.modifyBook(bookInfo);
    }

    @Override
    @Transactional
    public List<Book> getBooksRankedBySalesByTime(ArrayList<Date> time)
    {
        if (time != null && time.size() == 2)
        {
            Date start = (Date) time.get(0);
            Date end = (Date) time.get(1);
            if (start != null && end != null)
                return bookDao.getBooksRankedBySalesByTime(start, end);
        }
        return bookDao.getBooksRankedBySales();
    }

    @Override
    public List<Book> getConsumption() {
        return bookDao.getConsumption();
    }

    @Override
    public List<Book> getConsumptionByTime(ArrayList<Date> time)
    {
        if (time != null && time.size() == 2)
        {
            Date start = (Date) time.get(0);
            Date end = (Date) time.get(1);
            if (start != null && end != null)
                return bookDao.getConsumptionByTime(start, end);
        }
        return bookDao.getConsumption();
    }

    @Override
    @PostConstruct
    public void createBookIndex() throws IOException {
        List<Book> list1 = new ArrayList<>();
        list1 = bookDao.getBooks();
        // ?????????????????????
        Collection<Document> docs = new ArrayList<>();
        for(int i=0;i<list1.size();i++)
        {
            Book b = list1.get(i);
            // ??????????????????
            Document document1 = new Document();
            //StringField???????????????????????????????????????
            document1.add(new StringField("bookId", b.getBookId()+"", Field.Store.YES));
            document1.add(new StringField("type", b.getType(), Field.Store.YES));
            //TextField?????????????????????????????????
            document1.add(new TextField("title", b.getTitle(), Field.Store.YES));
            document1.add(new TextField("author", b.getAuthor(), Field.Store.YES));
            document1.add(new TextField("description", b.getDescription(), Field.Store.YES));
            //StoredField????????????????????????????????????
            document1.add(new StoredField("available", b.getAvailable()));
            document1.add(new StoredField("price", b.getPrice()));
            document1.add(new StoredField("inventory", b.getInventory()));
            document1.add(new StoredField("sales", b.getSales()));
            document1.add(new StoredField("image1", b.getImage1()));
            document1.add(new StoredField("image2", b.getImage2()));
            docs.add(document1);
        }

        // ???????????????,???????????????????????????????????????????????????D??????indexDir?????????
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("d:\\bookstore-base/bookstore_backend/indexDir"));
        // ??????IK?????????
        Analyzer analyzer = new MyIKAnalyzer();
        // ??????????????????????????????????????????????????????????????????????????????????????????
        IndexWriterConfig conf = new IndexWriterConfig(analyzer);
        // ?????????????????????OpenMode.APPEND ?????????????????????????????????????????????OpenMode.CREATE????????????????????????????????????????????????
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        // ????????????????????????????????????????????????????????????????????????
        IndexWriter indexWriter = new IndexWriter(directory, conf);
        // ?????????????????????IndexWriter
        indexWriter.addDocuments(docs);
        // ??????
        indexWriter.commit();
        // ??????
        indexWriter.close();
    }


    @Override
    public List<Book> searchBookByDescription(String keyword) throws IOException, ParseException {
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("d:\\bookstore-base/bookstore_backend/indexDir"));
        // ??????????????????
        IndexReader reader = DirectoryReader.open(directory);
        // ??????????????????
        IndexSearcher searcher = new IndexSearcher(reader);
        // ?????????????????????,????????????????????????????????????????????????????????????
        QueryParser parser = new QueryParser("description", new MyIKAnalyzer());//------
        // ??????????????????
        Query query = parser.parse(keyword);
        // ?????????????????????
        TopDocs topDocs = searcher.search(query, 10);
        // ???????????????
        System.out.println("?????????????????????" + topDocs.totalHits + "?????????");

        // ???????????????????????????ScoreDoc?????????.SocreDoc?????????????????????????????????????????????
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        List<Book> list = new ArrayList<>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            // ??????????????????
            int docID = scoreDoc.doc;
            // ????????????????????????
            Document doc = reader.document(docID);
            Book b = bookDao.findOne(Integer.parseInt(doc.get("bookId")));
            list.add(b);
        }
        return list;
    }

    @Override
    public boolean commentBook(Integer bookId, Integer userId, String comment)
    {
        return bookDao.commentBook(bookId, userId, comment);
    }

    @Override
    public List<Comment> searchCommentByBookId(Integer bookId)
    {
        return bookDao.searchCommentByBookId(bookId);
    }

    @Override
    @Transactional
    public List<Book> searchBookByType(String type)
    {
        List<BookType> relatedType = bookTypeDao.searchDependentType(type);
        List<Book> result = new ArrayList<>();
        for(BookType iter:relatedType)
        {
            result.addAll(bookDao.searchBookByType(iter.getType()));
        }
        System.out.println(result);
        return result;
    }
}

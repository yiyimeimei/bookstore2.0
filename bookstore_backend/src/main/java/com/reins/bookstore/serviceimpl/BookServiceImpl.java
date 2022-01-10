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
        // 创建文档的集合
        Collection<Document> docs = new ArrayList<>();
        for(int i=0;i<list1.size();i++)
        {
            Book b = list1.get(i);
            // 创建文档对象
            Document document1 = new Document();
            //StringField会创建索引，但是不会被分词
            document1.add(new StringField("bookId", b.getBookId()+"", Field.Store.YES));
            document1.add(new StringField("type", b.getType(), Field.Store.YES));
            //TextField，即创建索引又会被分词
            document1.add(new TextField("title", b.getTitle(), Field.Store.YES));
            document1.add(new TextField("author", b.getAuthor(), Field.Store.YES));
            document1.add(new TextField("description", b.getDescription(), Field.Store.YES));
            //StoredField不分词、不索引，但会存储
            document1.add(new StoredField("available", b.getAvailable()));
            document1.add(new StoredField("price", b.getPrice()));
            document1.add(new StoredField("inventory", b.getInventory()));
            document1.add(new StoredField("sales", b.getSales()));
            document1.add(new StoredField("image1", b.getImage1()));
            document1.add(new StoredField("image2", b.getImage2()));
            docs.add(document1);
        }

        // 索引目录类,指定索引在硬盘中的位置，我的设置为D盘的indexDir文件夹
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("d:\\bookstore-base/bookstore_backend/indexDir"));
        // 引入IK分词器
        Analyzer analyzer = new MyIKAnalyzer();
        // 索引写出工具的配置对象，这个地方就是最上面报错的问题解决方案
        IndexWriterConfig conf = new IndexWriterConfig(analyzer);
        // 设置打开方式：OpenMode.APPEND 会在索引库的基础上追加新索引。OpenMode.CREATE会先清空原来数据，再提交新的索引
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        // 创建索引的写出工具类。参数：索引的目录和配置信息
        IndexWriter indexWriter = new IndexWriter(directory, conf);
        // 把文档集合交给IndexWriter
        indexWriter.addDocuments(docs);
        // 提交
        indexWriter.commit();
        // 关闭
        indexWriter.close();
    }


    @Override
    public List<Book> searchBookByDescription(String keyword) throws IOException, ParseException {
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("d:\\bookstore-base/bookstore_backend/indexDir"));
        // 索引读取工具
        IndexReader reader = DirectoryReader.open(directory);
        // 索引搜索工具
        IndexSearcher searcher = new IndexSearcher(reader);
        // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
        QueryParser parser = new QueryParser("description", new MyIKAnalyzer());//------
        // 创建查询对象
        Query query = parser.parse(keyword);
        // 获取前十条记录
        TopDocs topDocs = searcher.search(query, 10);
        // 获取总条数
        System.out.println("本次搜索共找到" + topDocs.totalHits + "条数据");

        // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        List<Book> list = new ArrayList<>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 取出文档编号
            int docID = scoreDoc.doc;
            // 根据编号去找文档
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

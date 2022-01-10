package com.reins.bookstore;

import com.reins.bookstore.entity.BookType;
import com.reins.bookstore.repository.BookTypeRepository;
import com.reins.bookstore.repository.CommentRepository;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.jms.annotation.EnableJms;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;
import java.util.List;


@SpringBootApplication
@EnableJms
@EnableTransactionManagement
@EnableNeo4jRepositories
public class BookstoreApplication {

    @Bean
    CommandLineRunner createNeo4jGraph(BookTypeRepository bookTypeRepository) {
        return args ->
        {
            bookTypeRepository.deleteAll();
            BookType type1 = new BookType("编程");
            BookType type2 = new BookType("同性爱情");
            BookType type3 = new BookType("日系推理");
            BookType type4 = new BookType("欧美推理");
            BookType type5 = new BookType("日系短篇小说");
            BookType type6 = new BookType("日系小说");
            BookType type7 = new BookType("馆系列");

            List<BookType> typeSet = Arrays.asList(type1, type2, type3, type4, type5, type6, type7);



            /*type1 = bookTypeRepository.findByType(type1.getType());
            type2 = bookTypeRepository.findByType(type2.getType());
            type3 = bookTypeRepository.findByType(type3.getType());
            type4 = bookTypeRepository.findByType(type4.getType());
            type5 = bookTypeRepository.findByType(type5.getType());
            type6 = bookTypeRepository.findByType(type6.getType());
            type7 = bookTypeRepository.findByType(type7.getType());*/

            type1.dependent(type2);
            type2.dependent(type1);

            type4.dependent(type3);
            type3.dependent(type4);

            type3.dependent(type6);
            type6.dependent(type3);
            type6.dependent(type5);
            type5.dependent(type6);
            type3.dependent(type7);
            type7.dependent(type3);

            bookTypeRepository.save(type1);
            bookTypeRepository.save(type2);
            bookTypeRepository.save(type3);
            bookTypeRepository.save(type4);
            bookTypeRepository.save(type5);
            bookTypeRepository.save(type6);
            bookTypeRepository.save(type7);


        };
    }




    public static void main(String[] args)
    {
        SpringApplication.run(BookstoreApplication.class, args);
    }
    /*@Bean
    public Connector connector(){
        Connector connector=new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        //Connector监听的http的端口号
        connector.setPort(8080);
        connector.setSecure(false);
        //监听到http的端口号后转向到的https的端口号
        connector.setRedirectPort(8443);
        return connector;
    }

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory(Connector connector){
        TomcatServletWebServerFactory tomcat=new TomcatServletWebServerFactory(){
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint=new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection=new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(connector);
        return tomcat;
    }*/
}

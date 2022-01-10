//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.3.2 生成的
// 请访问 <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2021.10.15 时间 02:09:23 AM CST 
//


package io.spring.guides.gs_producing_web_service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Param"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="book" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;group ref="{http://spring.io/guides/gs-producing-web-service}book"/&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "param"
})
@XmlRootElement(name = "searchBookByDescriptionResponse")
public class SearchBookByDescriptionResponse {

    @XmlElement(name = "Param", required = true)
    protected SearchBookByDescriptionResponse.Param param;

    /**
     * 获取param属性的值。
     * 
     * @return
     *     possible object is
     *     {@link SearchBookByDescriptionResponse.Param }
     *     
     */
    public SearchBookByDescriptionResponse.Param getParam() {
        return param;
    }

    /**
     * 设置param属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link SearchBookByDescriptionResponse.Param }
     *     
     */
    public void setParam(SearchBookByDescriptionResponse.Param value) {
        this.param = value;
    }


    /**
     * <p>anonymous complex type的 Java 类。
     * 
     * <p>以下模式片段指定包含在此类中的预期内容。
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="book" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;group ref="{http://spring.io/guides/gs-producing-web-service}book"/&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "book"
    })
    public static class Param {

        @XmlElement(required = true)
        protected List<SearchBookByDescriptionResponse.Param.Book> book;

        /**
         * Gets the value of the book property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the book property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBook().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SearchBookByDescriptionResponse.Param.Book }
         * 
         * 
         */
        public List<SearchBookByDescriptionResponse.Param.Book> getBook() {
            if (book == null) {
                book = new ArrayList<SearchBookByDescriptionResponse.Param.Book>();
            }
            return this.book;
        }

        public void setBook(List<SearchBookByDescriptionResponse.Param.Book> value) {
            this.book = value;
        }


        /**
         * <p>anonymous complex type的 Java 类。
         * 
         * <p>以下模式片段指定包含在此类中的预期内容。
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;group ref="{http://spring.io/guides/gs-producing-web-service}book"/&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "bookId",
            "isbn",
            "title",
            "author",
            "type",
            "price",
            "description",
            "inventory",
            "sales",
            "image1",
            "image2",
            "available"
        })
        public static class Book {

            protected int bookId;
            @XmlElement(required = true)
            protected String isbn;
            @XmlElement(required = true)
            protected String title;
            @XmlElement(required = true)
            protected String author;
            @XmlElement(required = true)
            protected String type;
            protected int price;
            @XmlElement(required = true)
            protected String description;
            protected int inventory;
            protected int sales;
            @XmlElement(required = true)
            protected String image1;
            @XmlElement(required = true)
            protected String image2;
            protected int available;

            /**
             * 获取bookId属性的值。
             * 
             */
            public int getBookId() {
                return bookId;
            }

            /**
             * 设置bookId属性的值。
             * 
             */
            public void setBookId(int value) {
                this.bookId = value;
            }

            /**
             * 获取isbn属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsbn() {
                return isbn;
            }

            /**
             * 设置isbn属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsbn(String value) {
                this.isbn = value;
            }

            /**
             * 获取title属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTitle() {
                return title;
            }

            /**
             * 设置title属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTitle(String value) {
                this.title = value;
            }

            /**
             * 获取author属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAuthor() {
                return author;
            }

            /**
             * 设置author属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAuthor(String value) {
                this.author = value;
            }

            /**
             * 获取type属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getType() {
                return type;
            }

            /**
             * 设置type属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setType(String value) {
                this.type = value;
            }

            /**
             * 获取price属性的值。
             * 
             */
            public int getPrice() {
                return price;
            }

            /**
             * 设置price属性的值。
             * 
             */
            public void setPrice(int value) {
                this.price = value;
            }

            /**
             * 获取description属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescription() {
                return description;
            }

            /**
             * 设置description属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescription(String value) {
                this.description = value;
            }

            /**
             * 获取inventory属性的值。
             * 
             */
            public int getInventory() {
                return inventory;
            }

            /**
             * 设置inventory属性的值。
             * 
             */
            public void setInventory(int value) {
                this.inventory = value;
            }

            /**
             * 获取sales属性的值。
             * 
             */
            public int getSales() {
                return sales;
            }

            /**
             * 设置sales属性的值。
             * 
             */
            public void setSales(int value) {
                this.sales = value;
            }

            /**
             * 获取image1属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getImage1() {
                return image1;
            }

            /**
             * 设置image1属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setImage1(String value) {
                this.image1 = value;
            }

            /**
             * 获取image2属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getImage2() {
                return image2;
            }

            /**
             * 设置image2属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setImage2(String value) {
                this.image2 = value;
            }

            /**
             * 获取available属性的值。
             * 
             */
            public int getAvailable() {
                return available;
            }

            /**
             * 设置available属性的值。
             * 
             */
            public void setAvailable(int value) {
                this.available = value;
            }

        }

    }

}

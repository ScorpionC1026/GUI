/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRODUCT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findByProductid", query = "SELECT p FROM Product p WHERE p.productid = :productid"),
    @NamedQuery(name = "Product.findByBookname", query = "SELECT p FROM Product p WHERE p.bookname = :bookname"),
    @NamedQuery(name = "Product.findByAuthor", query = "SELECT p FROM Product p WHERE p.author = :author"),
    @NamedQuery(name = "Product.findByPublisher", query = "SELECT p FROM Product p WHERE p.publisher = :publisher"),
    @NamedQuery(name = "Product.findByPublicationdate", query = "SELECT p FROM Product p WHERE p.publicationdate = :publicationdate"),
    @NamedQuery(name = "Product.findByGenre", query = "SELECT p FROM Product p WHERE p.genre = :genre"),
    @NamedQuery(name = "Product.findByPrice", query = "SELECT p FROM Product p WHERE p.price = :price"),
    @NamedQuery(name = "Product.findByDescription", query = "SELECT p FROM Product p WHERE p.description = :description"),
    @NamedQuery(name = "Product.findByLanguage", query = "SELECT p FROM Product p WHERE p.language = :language"),
    @NamedQuery(name = "Product.findByCreateDate", query = "SELECT p FROM Product p WHERE p.createDate = :createDate"),
    @NamedQuery(name = "Product.findByUpdateDate", query = "SELECT p FROM Product p WHERE p.updateDate = :updateDate")})
public class Product implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "BOOKNAME")
    private String bookname;
    @Size(max = 30)
    @Column(name = "AUTHOR")
    private String author;
    @Size(max = 100)
    @Column(name = "PUBLISHER")
    private String publisher;
    @Size(max = 30)
    @Column(name = "GENRE")
    private String genre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRICE")
    private double price;
    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "LANGUAGE")
    private String language;
    @Lob
    @Column(name = "IMAGE")
    private byte[] image;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Orderdetails> orderdetailsList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PRODUCTID")
    private String productid;
    @Column(name = "PUBLICATIONDATE")
    @Temporal(TemporalType.DATE)
    private Date publicationdate;
    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "UPDATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    public Product() {
    }

    public Product(String productid) {
        this.productid = productid;
    }

    public Product(String productid, String bookname, double price, String language) {
        this.productid = productid;
        this.bookname = bookname;
        this.price = price;
        this.language = language;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }


    public Date getPublicationdate() {
        return publicationdate;
    }

    public void setPublicationdate(Date publicationdate) {
        this.publicationdate = publicationdate;
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productid != null ? productid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productid == null && other.productid != null) || (this.productid != null && !this.productid.equals(other.productid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Product[ productid=" + productid + " ]";
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[]  image) {
        this.image = image;
    }

    @XmlTransient
    public List<Orderdetails> getOrderdetailsList() {
        return orderdetailsList;
    }

    public void setOrderdetailsList(List<Orderdetails> orderdetailsList) {
        this.orderdetailsList = orderdetailsList;
    }
    
}

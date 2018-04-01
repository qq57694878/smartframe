package com.jldata.smartframe.demo.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "DOCUMENT")
public class Document implements Serializable {


    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_seq")
    @SequenceGenerator(name = "document_seq", sequenceName = "document_seq", allocationSize = 102)
    private Long id;

    @Column(name = "TITLE", length = 2000)
    @NotNull
    private String title;

    @Column(name = "AUTHOR", length = 200)
    @NotNull
    private String author;

    @Column(name = "STATUS", length = 20)
    @NotNull
    private String status;

    @Column(name = "PAGEVIEWS", length = 8)
    @NotNull
    private Integer pageviews;

    @Column(name = "DISPLAY_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date display_time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPageviews() {
        return pageviews;
    }

    public void setPageviews(Integer pageviews) {
        this.pageviews = pageviews;
    }

    public Date getDisplay_time() {
        return display_time;
    }

    public void setDisplay_time(Date display_time) {
        this.display_time = display_time;
    }
}
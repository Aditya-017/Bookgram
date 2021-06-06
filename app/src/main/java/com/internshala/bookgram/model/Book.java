package com.internshala.bookgram.model;

public class Book {
    public String bookName;
    public String bookAuthor;
    public String bookCost;
    public String bookPublisher;
    public String bookImage;

    public Book(String bookName, String bookAuthor, String bookCost, String bookImage,String bookPublisher) {
          this.bookName=bookName;
          this.bookAuthor=bookAuthor;
          this.bookCost=bookCost;
          this.bookPublisher=bookPublisher;
          this.bookImage=bookImage;
    }

}

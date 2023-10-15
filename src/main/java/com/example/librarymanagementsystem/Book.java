package com.example.librarymanagementsystem;

import javafx.collections.ObservableArray;

public class Book {
    String name;
    String writter;
    int quantity;
    int bookNo;

    public Book(){}

    public Book(String name, String writter, int quantity, int bookNo){
        this.name = name;
        this.writter = writter;
        this.quantity = quantity;
        this.bookNo = bookNo;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWritter() {
        return writter;
    }

    public void setWritter(String writter) {
        this.writter = writter;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getBookNo() {
        return bookNo;
    }

    public void setBookNo(int bookNo) {
        this.bookNo = bookNo;
    }
}

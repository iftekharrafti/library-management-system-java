package com.example.librarymanagementsystem;

public class ReturnBook {
    String bookName;
    String issueDate;
     String returnedDate;

     public ReturnBook(){}

    public ReturnBook(String bookName, String issueDate, String returnedDate) {
        this.bookName = bookName;
        this.issueDate = issueDate;
        this.returnedDate = returnedDate;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(String returnedDate) {
        this.returnedDate = returnedDate;
    }
}

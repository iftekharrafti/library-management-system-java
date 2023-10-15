package com.example.librarymanagementsystem;

public class IssueBook {
    String studentId;
    String studentName;
    String gender;
    String bookName;
    String issuedDate;
    String returnedDate;

    public IssueBook(String studentId, String studentName, String gender, String bookName, String issuedDate, String returnedDate){
        this.studentId = studentId;
        this.studentName = studentName;
        this.gender = gender;
        this.bookName = bookName;
        this.issuedDate = issuedDate;
        this.returnedDate = returnedDate;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getGender() {
        return gender;
    }

    public String getBookName() {
        return bookName;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public String getReturnedDate() {
        return returnedDate;
    }
}

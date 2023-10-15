package com.example.librarymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddBookController {
    @FXML
    TextField addBookName;
    @FXML
    TextField addBookWritter;
    @FXML
    TextField addBookQuantity;
    @FXML
    TextField addBookNo;
    @FXML
    Label addBookSuccess;
    @FXML
    Label showBook;

    Book[] bookArray = new Book[50];
    int count = 0;

    @FXML
    public void addBook(){
        String bookName = addBookName.getText();
        String bookWritter = addBookWritter.getText();
        int bookQuantity = Integer.parseInt(addBookQuantity.getText());
        int bookNo = Integer.parseInt(addBookNo.getText());

        String jdbcUrl = "jdbc:mysql://localhost:3306/library_management";
        String username = "root";
        String password = "@iftekh@r#r@fti";

//        Create book
        Book newBook = new Book(bookName, bookWritter, bookQuantity, bookNo);

        String insertQuery = "insert into book(name, writter, quantity, bookNo) value(?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            // Set values for placeholders
            preparedStatement.setString(1, newBook.getName());
            preparedStatement.setString(2, newBook.getWritter());
            preparedStatement.setInt(3, newBook.getQuantity());  // Assuming you have 10 copies of the book
            preparedStatement.setInt(4, newBook.getBookNo());  // Replace with the actual book number

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted successfully.");
            System.out.println("Book added successfully");
            addBookSuccess.setText("Book added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void showBookBtn() throws IOException {
        System.out.println("Go to signin page");
        HelloApplication.changeScene("showBooks.fxml");
    }
}

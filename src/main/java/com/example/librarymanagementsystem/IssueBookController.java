package com.example.librarymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class IssueBookController implements Initializable {
    @FXML
    TextField studentId;
    @FXML
    TextField studentName;
    @FXML
    ChoiceBox<String> studentGenderChoicebox;
    @FXML
    ChoiceBox<String> bookNameChoicebox;
    @FXML
    DatePicker issuedDate;
    @FXML
    DatePicker returnedDate;
    @FXML
    Label addBookSuccess;

    ObservableList<String> genderObservableList;
    ObservableList<String> bookNameObservableList;
    List<Book> books = new ArrayList<>();

    String jdbcUrl = "jdbc:mysql://localhost:3306/library_management";
    String username = "root";
    String password = "@iftekh@r#r@fti";

    @FXML
    public void takeBook(){
        String stuId = studentId.getText();
        String stuName = studentName.getText();

        String gender = studentGenderChoicebox.getValue();
        String bookName = bookNameChoicebox.getValue();

        LocalDate issDate =issuedDate.getValue();
        LocalDate returnDate =returnedDate.getValue();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Format the LocalDate to a String
        String issueFormattedDate = issDate.format(formatter);
        String returnFormattedDate = returnDate.format(formatter);

        IssueBook newIssueBook = new IssueBook(stuId, stuName, gender, bookName, issueFormattedDate, returnFormattedDate);
        String insertQuery = "insert into issue_book(id, studentName, gender, bookName, issueDate, returnedDate) value(?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            // Set values for placeholders
            preparedStatement.setString(1, newIssueBook.getStudentId());
            preparedStatement.setString(2, newIssueBook.getStudentName());
            preparedStatement.setString(3, newIssueBook.getGender());
            preparedStatement.setString(4, newIssueBook.getBookName());
            preparedStatement.setString(5, newIssueBook.getIssuedDate());
            preparedStatement.setString(6, newIssueBook.getReturnedDate());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted successfully.");
            System.out.println("Issued Book added successfully");

//            Query in book table
            String selectQuery = "select * from book";
            ResultSet resultSet = preparedStatement.executeQuery(selectQuery);
            while (resultSet.next()) {
                Book book = new Book();
                book.setName(resultSet.getString("name"));
                book.setWritter(resultSet.getString("writter"));
                book.setQuantity(resultSet.getInt("quantity"));
                book.setBookNo(resultSet.getInt("bookNo"));
                books.add(book);
            }

            for(Book book : books){
                if(book.getName().equals(bookName)){
                    String updateQuery = "UPDATE book SET quantity = ? WHERE name = ?";

                    try (PreparedStatement updatePreparedStatement = connection.prepareStatement(updateQuery)) {
                        int newQuantity = book.quantity - 1; // Replace with the new quantity value
                        String bookNameToUpdate = bookName; // Replace with the actual book number
                        preparedStatement.setInt(1, newQuantity);
                        preparedStatement.setString(2, bookNameToUpdate);
                        int quantityRowsAffected = preparedStatement.executeUpdate();

                        if (quantityRowsAffected > 0) {
                            System.out.println(rowsAffected + " row(s) updated successfully.");
                        } else {
                            System.out.println("No rows were updated. The bookNo may not exist.");
                        }
                    }

                }
            }
            addBookSuccess.setText("Book added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("stuId: " + stuId + "stuName: " + stuName + "gender: " + gender + "bookName: " + bookName + "issueDte: " + issDate + "retur date: " + returnDate);
    }

    @FXML
    public void showBooksBtn() throws Exception{
        HelloApplication.changeScene("showBooks.fxml");
    }
    @FXML
    public void returnBooksBtn() throws Exception{
        HelloApplication.changeScene("returnBook.fxml");
    }
    @FXML
    public void savedBooksBtn() throws Exception{
        HelloApplication.changeScene("savedBook.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Books set on the choiceBox

        String selectQuery = "select name from book";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {

            bookNameObservableList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                bookNameObservableList.add(resultSet.getString("name"));
            }
            bookNameChoicebox.setItems(bookNameObservableList);
            bookNameChoicebox.setValue(bookNameObservableList.get(0));
        } catch (SQLException e) {
            e.printStackTrace();
        }


//        Gender observalble list
        genderObservableList = FXCollections.observableArrayList();
        genderObservableList.add("Male");
        genderObservableList.add("Female");
        genderObservableList.add("Custom");

        studentGenderChoicebox.setItems(genderObservableList);
        studentGenderChoicebox.setValue(genderObservableList.get(0));

    }
}

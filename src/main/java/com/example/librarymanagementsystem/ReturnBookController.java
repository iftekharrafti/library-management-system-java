package com.example.librarymanagementsystem;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReturnBookController implements Initializable {
    @FXML
    TableView<ReturnBook> issuedTable;
    @FXML
    TableColumn<ReturnBook, String> issuedNameColumn;
    @FXML
    TableColumn<ReturnBook, String> issueDate;
    @FXML
    TableColumn<ReturnBook, String> returnDate;
    @FXML
    TableColumn<ReturnBook, Integer> issuedActionColumn;

    ObservableList<ReturnBook> bookObservableList;
    List<ReturnBook> books = new ArrayList<>();
    String jdbcUrl = "jdbc:mysql://localhost:3306/library_management";
    String username = "root";
    String password = "@iftekh@r#r@fti";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String selectQuery = "select * from issue_book";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {

            while (resultSet.next()) {
                ReturnBook book = new ReturnBook();
                book.setBookName(resultSet.getString("bookName"));
                book.setIssueDate(resultSet.getString("issueDate"));
                book.setReturnedDate(resultSet.getString("returnedDate"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

// Initialize TableColumn
        issuedNameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getBookName()));
        issueDate.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getIssueDate()));
        returnDate.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getReturnedDate()));
//
        bookObservableList = FXCollections.observableArrayList(books);
        issuedTable.setItems(bookObservableList);
    }


    @FXML
    public void showBooksBtn() throws Exception{
        HelloApplication.changeScene("showBooks.fxml");
    }
    @FXML
    public void issueBooksBtn() throws Exception{
        HelloApplication.changeScene("issueBook.fxml");
    }
    @FXML
    public void savedBooksBtn() throws Exception{
        HelloApplication.changeScene("savedBook.fxml");
    }
}

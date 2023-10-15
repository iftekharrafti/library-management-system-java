package com.example.librarymanagementsystem;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class showBooksController implements Initializable {
    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;
    @FXML
    TableView<Book> bookTableView;
    @FXML
    TableColumn<Book, String> tableName;
    @FXML
    TableColumn<Book, String> tableWritter;
    @FXML
    TableColumn<Book, Integer> tableQuantity;
    @FXML
    TableColumn<Book, Integer> tableBookNo;
    @FXML
    TableColumn<String, String> tableAction;

    ObservableList<Book> bookObservableList;
    List<Book> books = new ArrayList<>();

    String jdbcUrl = "jdbc:mysql://localhost:3306/library_management";
    String username = "root";
    String password = "@iftekh@r#r@fti";

    @FXML
    public void availableBooks(javafx.scene.input.MouseEvent mouseEvent) {
        bp.setCenter(ap);
    }

    private void loadPage(String page){
        Parent root = null;
        try{
            root = FXMLLoader.load(getClass().getResource(page+".fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bp.setCenter(root);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources){

        String selectQuery = "select * from book";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {

            while (resultSet.next()) {
                Book book = new Book();
                book.setName(resultSet.getString("name"));
                book.setWritter(resultSet.getString("writter"));
                book.setQuantity(resultSet.getInt("quantity"));
                book.setBookNo(resultSet.getInt("bookNo"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Initialize TableColumn
        tableName.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        tableWritter.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getWritter()));
        tableQuantity.setCellValueFactory(cell -> {
            Integer value = cell.getValue().getQuantity();
            return new SimpleIntegerProperty(value).asObject();
        });
        tableBookNo.setCellValueFactory(cell -> {
            Integer value = cell.getValue().getBookNo();
            return new SimpleIntegerProperty(value).asObject();
        });
//        tableAction.setCellValueFactory(cell -> new SimpleStringProperty("Save"));
//
        bookObservableList = FXCollections.observableArrayList(books);
        bookTableView.setItems(bookObservableList);

    }

    @FXML
    public void issueBooksBtn() throws Exception{
        HelloApplication.changeScene("issueBook.fxml");
    }
    @FXML
    public void returnBooksBtn() throws Exception{
        HelloApplication.changeScene("returnBook.fxml");
    }
    @FXML
    public void savedBooksBtn() throws Exception{
        HelloApplication.changeScene("savedBook.fxml");
    }
}
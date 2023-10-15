package com.example.librarymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.stream.Stream;

public class SigninController {
    HashMap<String, String> adminCredentials = SignupController.getAdminCredentials();
    HashMap<String, String> userCredentials = SignupController.getUserCredentials();

    @FXML
    TextField loginEmail;
    @FXML
    PasswordField loginPassword;

    @FXML
    public void login() throws IOException {
        String email = loginEmail.getText();
        String password = loginPassword.getText();

        String jdbcUrl = "jdbc:mysql://localhost:3306/library_management";
        String username = "root";
        String dbPassword = "@iftekh@r#r@fti";

        String userSelectQuery = "SELECT * FROM signup WHERE email = ? AND password = ?";
        String adminSelectQuery = "SELECT * FROM adminuser WHERE email = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, dbPassword)) {
            boolean isAdmin = false;
            boolean isUser = false;

            // Authenticate as admin
            try (PreparedStatement adminStatement = connection.prepareStatement(adminSelectQuery)) {
                adminStatement.setString(1, email);
                adminStatement.setString(2, password);
                ResultSet adminResultSet = adminStatement.executeQuery();

                if (adminResultSet.next()) {
                    isAdmin = true;
                }
            }

            // Authenticate as a user
            try (PreparedStatement userStatement = connection.prepareStatement(userSelectQuery)) {
                userStatement.setString(1, email);
                userStatement.setString(2, password);
                ResultSet userResultSet = userStatement.executeQuery();

                if (userResultSet.next()) {
                    isUser = true;
                }
            }

            if (isAdmin) {
                System.out.println("Admin login successful");
                HelloApplication.changeScene("addBook.fxml");
            } else if (isUser) {
                System.out.println("User login successful");
                HelloApplication.changeScene("showBooks.fxml");
            } else {
                System.out.println("Authentication failed. You are neither an admin nor a user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void goToSignup() throws Exception{
        System.out.println("Go to Signup Page");
        HelloApplication.changeScene("signupMain.fxml");
    }
}

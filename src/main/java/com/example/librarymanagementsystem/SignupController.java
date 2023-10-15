package com.example.librarymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SignupController {
    @FXML
    TextField signupName;
    @FXML
    TextField signupEmail;
    @FXML
    PasswordField signupPassword;
    @FXML
    Label createdUser;

    private static HashMap<String, String> adminCredentials = new HashMap<>();
    private static HashMap<String, String> userCredentials = new HashMap<>();

    List<Signup> signups = new ArrayList<>();
    int option = 1;

    static {
        // Initialize admin credentials
        adminCredentials.put("admin", "admin123");
        adminCredentials.put("user1", "password1");
    }

    @FXML
    public boolean signup(){
        String name = signupName.getText();
        String email = signupEmail.getText();
        String password = signupPassword.getText();

//        userCredentials = new HashMap<>();

        // Check if the username already exists.
        if (userCredentials.containsKey(email)) {
            System.out.println("Username already exists. User creation failed.");
            return false;
        }

        String jdbcUrl = "jdbc:mysql://localhost:3306/library_management";
        String username = "root";
        String dbPassword = "@iftekh@r#r@fti";

        String insertQuery = "insert into signup(name, email, password) value(?,?,?)";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            // Set values for placeholders
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            String userSelectQuery = "SELECT * FROM signup WHERE email = ? AND password = ?";
            // Authenticate as a user
            try (PreparedStatement userStatement = connection.prepareStatement(userSelectQuery)) {
                userStatement.setString(1, email);
                userStatement.setString(2, password);
                ResultSet userResultSet = userStatement.executeQuery();

                if (userResultSet.next()) {
                    option = 0;
                }
            }

            if(option == 1){
                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println(rowsAffected + " row(s) inserted successfully.");
                System.out.println("User created successfully.");
                createdUser.setText("User created successfully.");
            }else{
                System.out.println("User already exists");
                createdUser.setText("User already exists");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Add the new user to the userCredentials map.
        userCredentials.put(email, password);

        return true;
    }

    @FXML
    public void printHashMap(){
        for (HashMap.Entry<String, String> entry : userCredentials.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + ": " + value);
        }
    }

//    Get admin Credentials
    public static HashMap<String, String> getAdminCredentials() {
        return adminCredentials;
    }

//    Get User Credentials
    public static HashMap<String, String> getUserCredentials() {
        return userCredentials;
    }


    @FXML
    public void goToLoginPage() throws Exception{
        System.out.println("Go to signin page");
        HelloApplication.changeScene("login.fxml");
    }
}

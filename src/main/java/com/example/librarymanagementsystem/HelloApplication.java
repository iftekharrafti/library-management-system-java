package com.example.librarymanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage; // Set the primary stage
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("showBooks.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 820, 550);
        stage.setTitle("Library Management System!");
        stage.setScene(scene);
        stage.show();
    }

    public static void changeScene(String fxmlFileName) throws IOException {
        // Load a new scene from the specified FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFileName));
        Parent root = fxmlLoader.load();

        // Set the new scene in the primary stage
        primaryStage.setScene(new Scene(root, 820, 550));
    }

    public static void main(String[] args) {
        launch();
    }
}
package com.example.projectf.controller;

import com.example.projectf.animation.BirdAnimation;
import com.example.projectf.database.DBHandler;
import com.example.projectf.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    ChangeScreenController changeScreenController = new ChangeScreenController();
    @FXML
    private ImageView birdIcon;
    @FXML
    private TextField loginTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button signUpButton;
    @FXML
    private Button contactUsButton;
    @FXML
    private Text responseField;
    @FXML
    private Button loginButton;

    private int userId;

    @FXML
    void initialize() {
        signUpButton.setOnAction(actionEvent -> {
            changeScreenController.changeScreen(signUpButton, "/com/example/projectf/signUp.fxml");
            System.out.println("Signing UP");

        });

        contactUsButton.setOnAction(actionEvent -> {
            changeScreenController.changeScreen(contactUsButton, "/com/example/projectf/contactUs.fxml");
            System.out.println("Contact us");

        });

        loginButton.setOnAction(actionEvent -> {
            DBHandler dbHandler = new DBHandler();

            User user = new User();
            user.setUserName(loginTextField.getText().trim());
            user.setPassword(passwordTextField.getText().trim());

            ResultSet resultSet = dbHandler.getUser(user);


            try {
                if (resultSet != null && resultSet.next()) {
                    System.out.println("welcome " + resultSet.getString("firstname"));
                    userId = resultSet.getInt("id");
                    mainScreen(true);
                    System.out.println("logging in");


                } else {
                    responseField.setText("incorrect login credentials");
                    passwordTextField.clear();
                    loginTextField.clear();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });

        BirdAnimation birdAnimation = new BirdAnimation();
        birdAnimation.birdAnimation(birdIcon);


        birdIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            changeScreenController.birdCLick(birdIcon);

        });


    }//initialize

    private void mainScreen(boolean isSignedin) {
        loginButton.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/projectf/main.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);


        if (isSignedin) {
            ChangeScreenController.userID = userId;

        }
        stage.show();
    }
}//class
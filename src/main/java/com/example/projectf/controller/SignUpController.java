package com.example.projectf.controller;

import com.example.projectf.animation.BirdAnimation;
import com.example.projectf.database.DBHandler;
import com.example.projectf.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.sql.ResultSet;

public class SignUpController {
    ChangeScreenController changeScreenController = new ChangeScreenController();
    @FXML
    private ImageView birdIcon;
    @FXML
    private TextField firstNameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button contactUsButton;

    @FXML
    private Text responseField;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    void initialize() {

        //only allow letters in text field
        firstNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                firstNameTextField.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });
        lastNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                lastNameTextField.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });
        userNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                userNameTextField.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        //bird animation and logout
        BirdAnimation birdAnimation = new BirdAnimation();
        birdAnimation.birdAnimation(birdIcon);

        birdIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            changeScreenController.birdCLick(birdIcon);
        });

        //contactus change screen
        contactUsButton.setOnAction(actionEvent -> {
            changeScreenController.changeScreen(contactUsButton, "/com/example/projectf/contactUs.fxml");
        });
        DBHandler dbHandler = new DBHandler();

        signUpButton.setOnAction(actionEvent -> {
            String firstname = firstNameTextField.getText().trim();
            String lastname = lastNameTextField.getText().trim();
            String username = userNameTextField.getText().trim();
            String password = passwordTextField.getText().trim();
//            System.out.println(username);

            //verify filling the form
            if (firstname.equals("") || lastname.equals("") || username.equals("") || password.equals("")) {
                System.out.println("fill the form");
                responseField.setText("please fill the form!");
                return;
            }


            //success sign up
            User user = new User(firstname, lastname, username, password);
            dbHandler.signUpUser(user);
            System.out.println("sign up successful, user created");
            responseField.setText("sign up successful, user created");
            firstNameTextField.clear();
            lastNameTextField.clear();
            userNameTextField.clear();
            passwordTextField.clear();

        });
    }
}

package com.example.projectf.controller;

import com.example.projectf.animation.BirdAnimation;
import com.example.projectf.database.DBHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ContactUsController {
    ChangeScreenController changeScreenController = new ChangeScreenController();
    @FXML
    private ImageView birdIcon;
    @FXML
    private TextField nameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private Button signUpButton;

    @FXML
    private Text responseField;

    @FXML
    private Button submitButton;

    @FXML
    private TextArea commentTextField;

    @FXML
    void initialize() {
        BirdAnimation birdAnimation = new BirdAnimation();
        birdAnimation.birdAnimation(birdIcon);

        birdIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            changeScreenController.birdCLick(birdIcon);
        });

        signUpButton.setOnAction(actionEvent -> {
            changeScreenController.changeScreen(signUpButton, "/com/example/projectf/signUp.fxml");
        });

        submitButton.setOnAction((actionEvent -> {
            DBHandler dbHandler = new DBHandler();

            String name = nameTextField.getText().trim();
            String mail = emailTextField.getText().trim();
            String comment = commentTextField.getText().trim();

            if (mail.equals("") || comment.equals("") || name.equals("")) {
                System.out.println("fill the form");
                responseField.setText("please fill the form!");
                return;
            }
            dbHandler.contactUser(name, mail, comment);

            responseField.setText("An email of confirmation will be sent to " + mail + " shortly.");

            nameTextField.clear();
            emailTextField.clear();
            commentTextField.clear();

        }));

    }


}

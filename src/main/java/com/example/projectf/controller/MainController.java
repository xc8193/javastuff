package com.example.projectf.controller;

import com.example.projectf.animation.BirdAnimation;
import com.example.projectf.database.DBHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.util.Random;

public class MainController {
    public static Double currentInterest;
    public static Double balance;
    public static Integer accountNumber;
    ChangeScreenController changeScreenController = new ChangeScreenController();
    DBHandler dbHandler = new DBHandler();
    @FXML
    private ImageView birdIcon;
    @FXML
    private TextField accountNumberTextField;
    @FXML
    private Button contactUsButton;
    @FXML
    private Button withdrawButton;
    @FXML
    private TextField balanceTextField;
    @FXML
    private TextField controlScreenTextField;
    @FXML
    private Button depositButton;
    @FXML
    private Button createAccountButton;
    @FXML
    private Text responseField;


    @FXML
    private Button getAccountButton;

    public static Double getCurrentInterest() {
        return currentInterest;
    }

    public void setCurrentInterest(Double currentInterest) {
        MainController.currentInterest = currentInterest;
    }

    public static Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        MainController.balance = balance;
    }

    public static Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        MainController.accountNumber = accountNumber;
    }


    @FXML
    void initialize() {
        //only allow numbers in text field
        controlScreenTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                controlScreenTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        BirdAnimation birdAnimation = new BirdAnimation();
        birdAnimation.birdAnimation(birdIcon);

        birdIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            changeScreenController.birdCLick(birdIcon);
        });

        createAccountButton.setOnAction(actionEvent -> {
            int oldAccountNum = 0;
            oldAccountNum = dbHandler.getAccountNumber(ChangeScreenController.userID);
            if (oldAccountNum == 0) {

                currentInterest = 0.15;
                balance = 0.0;
                Random numR = new Random();
                accountNumber = numR.nextInt(1000, 9999);
                dbHandler.createAccount(ChangeScreenController.userID);
                responseField.setText("Account created");
                accountNumberTextField.setText(dbHandler.getAccountNumber(ChangeScreenController.userID) + "");
                balanceTextField.setText(dbHandler.getBalance(ChangeScreenController.userID) + "");
            } else {
                responseField.setText("existing account found, Get Account instead");
            }

        });

        depositButton.setOnAction(actionEvent -> {

            if (responseField.getText().trim().equals("")) {
                responseField.setText("create or get account first");
                System.out.println("create or get account first");
                return;
            }

            if (controlScreenTextField.getText().equals("")) {
                System.out.println("enter a number");
                responseField.setText("enter a number");
                return;
            }

            Double newBalance;
            newBalance = dbHandler.getBalance(ChangeScreenController.userID) + Double.parseDouble(controlScreenTextField.getText().trim());
            balanceTextField.setText(newBalance + "");
            responseField.setText("new balance " + newBalance + "$");
            controlScreenTextField.clear();
            dbHandler.updateBalance(ChangeScreenController.userID, newBalance);

        });
        withdrawButton.setOnAction(actionEvent -> {
            if (responseField.getText().trim().equals("")) {
                responseField.setText("create or get account first");
                System.out.println("create or get account first");
                return;
            }

            if (controlScreenTextField.getText().equals("")) {
                System.out.println("enter a number");
                responseField.setText("enter a number");
                return;
            }

            if (dbHandler.getBalance(ChangeScreenController.userID) < Double.parseDouble(controlScreenTextField.getText().trim())) {
                responseField.setText("you do not have enough in your account to withdraw " + Double.parseDouble(controlScreenTextField.getText().trim()) + "$");
            } else {
                Double newBalance = null;
                newBalance = dbHandler.getBalance(ChangeScreenController.userID) - Double.parseDouble(controlScreenTextField.getText().trim());
                responseField.setText("you have withdrawn " + Double.parseDouble(controlScreenTextField.getText().trim()) +
                        " your new balance is " + newBalance + "$");
                balanceTextField.setText(newBalance + "");
                dbHandler.updateBalance(ChangeScreenController.userID, newBalance);
            }

            controlScreenTextField.clear();


        });
        getAccountButton.setOnAction(actionEvent -> {
            accountNumberTextField.setText(dbHandler.getAccountNumber(ChangeScreenController.userID) + "");
            balanceTextField.setText(dbHandler.getBalance(ChangeScreenController.userID) + "");
            responseField.setText("Account found");

        });
        contactUsButton.setOnAction(actionEvent -> {
            changeScreenController.changeScreen(contactUsButton,"/com/example/projectf/contactUs.fxml");
        });
    }
}

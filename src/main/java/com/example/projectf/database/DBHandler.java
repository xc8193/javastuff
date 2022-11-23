package com.example.projectf.database;

import com.example.projectf.controller.ChangeScreenController;
import com.example.projectf.controller.MainController;
import com.example.projectf.controller.SignUpController;
import com.example.projectf.model.User;

import java.sql.*;

public class DBHandler extends Config {
    Connection dbConnection;
    User user = new User();

    public Connection getDBConnection() throws SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPassword);
        System.out.println("connected to the Database");
        return dbConnection;
    }

    public void signUpUser(User user) {
        String query = "INSERT INTO " + Const.USERS_table + "(" +
                Const.USERS_FIRSTNAME + "," +
                Const.USERS_LASTNAME + "," +
                Const.USERS_USERNAME + "," +
                Const.USERS_PASSWORD + ") VALUES(?,?,?,?)";


        PreparedStatement preparedStatement;
        try {
            preparedStatement = getDBConnection().prepareStatement(query);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getPassword());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Change your username!.");
            throw new RuntimeException(e);
        } ;
    }




    public void contactUser(String name, String email, String comment) {
        String query = "INSERT INTO " + Const.CONTACT_table + "(" +
                Const.CONTACT_NAME + "," +
                Const.CONTACT_EMAIL + "," +
                Const.CONTACT_COMMENT + ") VALUES(?,?,?)";


        PreparedStatement preparedStatement;
        try {
            preparedStatement = getDBConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, comment);

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public ResultSet getUser(User user) {
        ResultSet resultSet = null;
        if (!user.getUserName().equals("") && !user.getPassword().equals("")) {
            String query = "SELECT * FROM " + Const.USERS_table + " WHERE " + Const.USERS_USERNAME + "=?" + " AND " + Const.USERS_PASSWORD + "=?";


            try {
                PreparedStatement preparedStatement = getDBConnection().prepareStatement(query);
                preparedStatement.setString(1, user.getUserName());
                preparedStatement.setString(2, user.getPassword());
                resultSet = preparedStatement.executeQuery();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("please enter your credentials");
        }

        return resultSet;


    }

    public void createAccount(int userID) {
        String query = "INSERT INTO " + Const.BALANCE_table + "(" +
                Const.BALANCE_ID + "," +
                Const.BALANCE_BALANCE + "," +
                Const.BALANCE_INTEREST + "," +
                Const.BAlANCE_ACCOUNT + ") VALUES(?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getDBConnection().prepareStatement(query);
            preparedStatement.setInt(1, ChangeScreenController.userID);
            preparedStatement.setDouble(2, MainController.getBalance());
            preparedStatement.setDouble(3, MainController.getCurrentInterest());
            preparedStatement.setInt(4, MainController.getAccountNumber());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public int getAccountNumber(int userID) {
        String query = "SELECT * FROM " + Const.BALANCE_table + " WHERE " + Const.BALANCE_ID + "=?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = getDBConnection().prepareStatement(query);
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(4);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public double getBalance(int userID) {
        String query = "SELECT * FROM " + Const.BALANCE_table + " WHERE " + Const.BALANCE_ID + "=?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = getDBConnection().prepareStatement(query);
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble(2);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public double updateBalance(int userID, Double newBalance) {
        String query = "UPDATE " + Const.BALANCE_table + " SET " +
                Const.BALANCE_BALANCE + " = " +
                newBalance + " WHERE " +
                Const.BALANCE_ID
                + "=?";


        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = getDBConnection().prepareStatement(query);
            preparedStatement.setInt(1, userID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }


}

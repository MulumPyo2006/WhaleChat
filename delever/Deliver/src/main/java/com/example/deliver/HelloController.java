package com.example.deliver;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class HelloController {

    @FXML
    private TextField userID;

    @FXML
    private TextField hiddenPW;

    @FXML
    private PasswordField userPW;

    @FXML
    private CheckBox showPW;

    @FXML
    private Button login;

    @FXML
    private Button signUp;

    public void singIn() {
        Parent nextScene = null;
        try {
            nextScene = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene = new Scene(nextScene);
            Stage primaryStage = (Stage) login.getScene().getWindow();
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showPassWord() {
        showPW.isSelected();
        if (showPW.isSelected()) {
            hiddenPW.setText(userPW.getText());
            userPW.setVisible(false);
            hiddenPW.setVisible(true);
        } else {
            userPW.setText(hiddenPW.getText());
            userPW.setVisible(true);
            hiddenPW.setVisible(false);
        }
    }

}
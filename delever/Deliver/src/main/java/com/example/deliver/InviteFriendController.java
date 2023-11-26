package com.example.deliver;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InviteFriendController {

    @FXML
    private Button inviteExit;

    public void inviteFriendExit() {
        try {
            Parent nextScene = FXMLLoader.load(getClass().getResource("chattingRoom.fxml"));
            Scene scene = new Scene(nextScene);
            Stage primaryStage = (Stage) inviteExit.getScene().getWindow();
            primaryStage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

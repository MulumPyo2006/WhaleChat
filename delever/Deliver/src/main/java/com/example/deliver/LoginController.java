package com.example.deliver;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;


import java.sql.*;

public class LoginController {


    @FXML
    public Button changeBtn;
    @FXML
    public TextField id;
    @FXML
    public TextField pw;
    @FXML
    public PasswordField pwTwo;
    @FXML
    public CheckBox pwCheckBox;


    public void initialize() {
        pwCheckBox.setSelected(true);
    }

    @FXML
    public void selectMember() {

        try {
            DBUtil db = new DBUtil();
            Connection conn = db.getConnection();

            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT * from users";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            String checkId = id.getText();
            String checkPw = pw.getText();
            String checkPwTwo = pwTwo.getText();


            while (rs.next()) {
                if ((checkId.equals(rs.getString("id")) || checkPwTwo.equals(rs.getString("pw"))) && checkPw.equals(rs.getString("pw"))) {

                    Parent nextScene = FXMLLoader.load(getClass().getResource("cha.fxml"));
                    Scene scene = new Scene(nextScene);
                    Stage primaryStage = (Stage) changeBtn.getScene().getWindow();
                    primaryStage.setScene(scene);


                } else {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("[로그인]");
                    alert.setHeaderText("로그인에 실패했습니다.");
                    alert.setContentText("아이디 혹은, 비밀번호가 틀렸습니다.");
                    alert.show();
                }
            }


        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    public void showPassWord() {

        pwCheckBox.isSelected();

        if (pwCheckBox.isSelected()) {

            pwTwo.setText(pw.getText());

            pw.setVisible(false);
            pwTwo.setVisible(true);

        } else {
            pw.setText(pwTwo.getText());

            pw.setVisible(true);
            pwTwo.setVisible(false);

        }
    }


    @FXML
    public void changeScene() {
        try {

            Parent nextScene = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
            Scene scene = new Scene(nextScene);
            Stage primaryStage = (Stage) changeBtn.getScene().getWindow();
            primaryStage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

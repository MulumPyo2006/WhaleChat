package com.example.deliver;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.*;

public class SignUpController {

    @FXML
    private TextField id;
    @FXML
    private TextField pw;
    @FXML
    private TextField name;
    @FXML
    private TextField chat;

    @FXML
    private Pane paneOne;
    @FXML
    private Pane paneTwo;
    @FXML
    private Pane paneThree;
    @FXML
    private Pane paneFour;
    @FXML
    private Pane paneFive;

    @FXML
    private Text textOne;
    @FXML
    private Text textTwo;
    @FXML
    private Text textThree;

    @FXML
    private Button changeBtn;
    @FXML
    private Button gameOffBtn;

    private int i = 1;

    Alert alert = new Alert(Alert.AlertType.WARNING);

    //채팅창 텍스트 입력 요구 문구
    public void initialize() {
        chat.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (chat.getText().equals("텍스트를 입력해주세요."))
                    chat.clear();
            }
        });
    }

    //회원가입
    @FXML
    public void insertMember() throws SQLException {

        try {
            //Enter키 누를 때
            chat.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode().equals(KeyCode.ENTER)) {

                        //SQL DB
                        DBUtil db = new DBUtil();
                        Connection conn = db.getConnection();
                        PreparedStatement pstmt = null;
                        PreparedStatement pstmtTwo = null;
                        ResultSet rs = null;

                        String sql = "INSERT INTO users(id, pw, name) VALUES(?,?,?)";
                        String sqlTwo = "SELECT * FROM `users` WHERE id = ?";

                        try {

                            pstmtTwo = conn.prepareStatement(sqlTwo);
                            pstmtTwo.setString(1, chat.getText());
                            rs = pstmtTwo.executeQuery();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        //ID 입력
                        if (i == 1) {

                            try {
                                if (rs.next()) {
                                    alert.setTitle("[회원가입]");
                                    alert.setHeaderText("아이디가 중복되었습니다.");
                                    alert.setContentText("다른 아이디를 입력해주세요.");
                                    alert.show();

                                } else {

                                    if (chat.getLength() <= 12 & chat.getLength() >= 6) {
                                        paneOne.setOpacity(1);
                                        paneTwo.setOpacity(1);
                                        id.setText(chat.getText());
                                        textOne.setText(chat.getText());
                                        chat.clear();
                                        i++;

                                    } else if (chat.getLength() > 12 | chat.getLength() < 6) {
                                        alert.setTitle("[회원가입]");
                                        alert.setHeaderText("아이디의 글자 수를 잘못 입력하셨습니다.");
                                        alert.setContentText("6글자 이상, 12글자 이하로 바꿔주세요.");
                                        alert.show();

                                        chat.clear();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //PW 입력
                        } else if (i == 2) {
                            if (chat.getLength() <= 15 & chat.getLength() >= 8) {

                                paneThree.setOpacity(1);
                                paneFour.setOpacity(1);
                                pw.setText(chat.getText());
                                textTwo.setText(chat.getText());
                                chat.clear();
                                i++;

                            } else if (chat.getLength() > 15 | chat.getLength() < 8) {
                                alert.setTitle("[회원가입]");
                                alert.setHeaderText("비밀번호의 글자 수를 잘못 입력하셨습니다.");
                                alert.setContentText("8글자 이상, 15글자 이하로 바꿔주세요.");
                                alert.show();

                                chat.clear();


                            }
                            //NAME 입력
                        } else if (i == 3) {
                            if (chat.getLength() <= 10 & chat.getLength() >= 2) {

                                paneFive.setOpacity(1);
                                name.setText(chat.getText());
                                textThree.setText(chat.getText());
                                chat.clear();


                                try {
                                    pstmt = conn.prepareStatement(sql);
                                    pstmt.setString(1, id.getText());
                                    pstmt.setString(2, pw.getText());
                                    pstmt.setString(3, name.getText());
                                    pstmt.executeUpdate();

                                    Parent nextScene = FXMLLoader.load(getClass().getResource("Login.fxml"));
                                    Scene scene = new Scene(nextScene);
                                    Stage primaryStage = (Stage) changeBtn.getScene().getWindow();
                                    primaryStage.setScene(scene);

                                    alert.setTitle("[회원가입]");
                                    alert.setHeaderText("회원가입을 성공했습니다!");
                                    alert.setContentText("축하드립니다.");
                                    alert.show();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else if (chat.getLength() > 10 | chat.getLength() < 2) {
                                alert.setTitle("[회원가입]");
                                alert.setHeaderText("닉네임의 글자 수를 잘못 입력하셨습니다.");
                                alert.setContentText("2글자 이상, 10글자 이하로 바꿔주세요.");
                                alert.show();

                                chat.setText("");
                            }
                        }

                    }
                }
            });
            //오류 발생시 자동 꺼짐
        } catch (Exception e) {
            e.printStackTrace();
            Stage stage = (Stage) gameOffBtn.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    public void changeScene() {
        try {

            Parent nextScene = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(nextScene);
            Stage primaryStage = (Stage) changeBtn.getScene().getWindow();
            primaryStage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

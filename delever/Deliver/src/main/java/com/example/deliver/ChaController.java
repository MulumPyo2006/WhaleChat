package com.example.deliver;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ChaController implements Initializable {


    @FXML
    private Button changBtn;

    @FXML
    private Button changBtn2;

    @FXML
    private TableView<ChatBuildVO> chatBuildTableView;
    @FXML
    private TableColumn<ChatBuildVO, String> nameCol;
    @FXML
    private TableColumn<ChatBuildVO, String> roomContentCol;

    ObservableList<ChatBuildVO> chattingRoomList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    public void loadData() {
        selectChattingRoom();
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        roomContentCol.setCellValueFactory(new PropertyValueFactory<>("roomContent"));
    }

    public void selectChattingRoom() {
        DBUtil db = new DBUtil();
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            chattingRoomList.clear();
            pstmt = conn.prepareStatement("SELECT name, roomContent FROM build");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                chattingRoomList.add(new ChatBuildVO(rs.getString("name"), rs.getString("roomContent")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        chatBuildTableView.setItems(chattingRoomList);
    }




    public void changeScene() {
        try {
            Parent nextScene
                    = FXMLLoader.load(getClass().getResource("chattingRoom.fxml"));
            Scene scene = new Scene(nextScene);
            Stage primaryStage = (Stage) changBtn.getScene().getWindow();
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeScene2() {
        try {
            Parent nextScene
                    = FXMLLoader.load(getClass().getResource("chabulid.fxml"));
            Scene scene = new Scene(nextScene);
            Stage primaryStage = (Stage) changBtn2.getScene().getWindow();
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

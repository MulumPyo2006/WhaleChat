package com.example.deliver;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;


import javax.swing.text.TableView;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Chat {

    @FXML
    private javafx.scene.control.TextField roomName;

    @FXML
    private javafx.scene.control.TextField roomPW;

    @FXML
    private CheckBox hide;

    @FXML
    private Button make;

    @FXML
    private javafx.scene.control.Label pwLa;

    public void hideRoom() {
        if (hide.isSelected()) {
            roomPW.setVisible(true);
            pwLa.setVisible(true);
        } else {
            roomPW.setVisible(false);
            pwLa.setVisible(false);
        }
    }

    public void insert() {

        DBUtil db = new DBUtil();
        Connection conn = db.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("INSERT INTO chat VALUES (?,?,?)");
            //pstmt.setString(1, );
            pstmt.setString(2, roomName.getText());
            pstmt.setString(3, roomPW.getText());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

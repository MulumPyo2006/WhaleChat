package com.example.deliver;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ChattingRoomController implements Initializable {
    @FXML
    private ToggleButton friendSearch;

    @FXML
    private Pane pane;

    @FXML
    private Label chattingRoom;

    @FXML
    private TextField chatInput;

    @FXML
    private Button inviteGO;

    @FXML
    private Button chattingExit;

    @FXML
    private ImageView imgView;

    @FXML
    private TableView<ChatVO> chatView;

    @FXML
    private TableColumn<ChatVO, String> nameCol;

    @FXML
    private TableColumn<ChatVO, String> chatContentsCol;

    @FXML
    private TableColumn<ChatVO, Time> timeCol;

    String find;

    LocalTime now = LocalTime.now();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH시 mm분 ss초");

    String formatedNow = now.format(formatter);

    ObservableList<ChatVO> chatList = FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDate1();
    }

    public void loadDate1() {
        select();
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        chatContentsCol.setCellValueFactory(new PropertyValueFactory<>("chatContent"));
        timeCol.setCellValueFactory((new PropertyValueFactory<>("chatTime")));
    }

    public void select() {
        DBUtil db = new DBUtil();
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            chatList.clear();
            pstmt = conn.prepareStatement("SELECT name, chatContent, chatTime FROM chat WHERE chattingRoomName = ? AND id = ?");
            pstmt.setString(1, chattingRoom.getText());
            //pstmt.setString(2, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                chatList.add(new ChatVO(rs.getString("name"), rs.getString("chatContent"), rs.getTime("chatTime")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        chatView.setItems(chatList);
    }

    public void send() {
        DBUtil db = new DBUtil();
        Connection conn = db.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("INSERT INTO chat VALUES (?,?,?,?,?)");
            //pstmt.setString(1, name);
            if (chatInput.equals("")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("공백오류");
                alert.show();
            } else {
                if (chatInput.getText().matches("/*") && !chatInput.getText().trim().equals("/")) {
                    find = chatInput.getText().substring(1);
                    search();
                    pstmt.setString(2, find);
                } else {
                    pstmt.setString(2, chatInput.getText());
                }
            }
            pstmt.setTime(3, Time.valueOf(formatedNow));
            //pstmt.setString(4, id);
            pstmt.setString(5, chattingRoom.getText());

            pstmt.executeUpdate();
            select();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fileImageChoose() {
        FileChooser fc = new FileChooser();
        fc.setTitle("이미지 선택");
        fc.setInitialDirectory(new File("C:/"));
        FileChooser.ExtensionFilter imgType = new FileChooser.ExtensionFilter("image file", "*.jpg", "*.jpge", "*.gif", "*.png");
        FileChooser.ExtensionFilter videoType = new FileChooser.ExtensionFilter("video file", "*.avi", "*.wmv", "*.mp4", "*.mpg", "*.mp2", "*.mpeg,");
        fc.getExtensionFilters().addAll(imgType,videoType);

        File selectedFile =  fc.showOpenDialog(null);
        System.out.println(selectedFile);
        try {
            FileInputStream fis = new FileInputStream(selectedFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            Image img = new Image(bis);
            imgView.setImage(img);
        } catch (NullPointerException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void inviteFriendGO() {
        try {
            Parent nextScene = FXMLLoader.load(getClass().getResource("inviteFriend.fxml"));
            Scene scene = new Scene(nextScene);
            Stage primaryStage = (Stage) inviteGO.getScene().getWindow();
            primaryStage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chattingRoomExit() {
        try {
            Parent nextScene = FXMLLoader.load(getClass().getResource("cha.fxml"));
            Scene scene = new Scene(nextScene);
            Stage primaryStage = (Stage) chattingExit.getScene().getWindow();
            primaryStage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pane() {
        pane.setDisable(false);
        if (friendSearch.isSelected()) {
            pane.setDisable(true);
        }
    }

    public void search() {
        String clientId = "3vbAs3kmMdj7Lr7HZNHK"; //애플리케이션 클라이언트 아이디
        String clientSecret = "cSen26uwDO"; //애플리케이션 클라이언트 시크릿


        String text = null;
        try {
            text = URLEncoder.encode("find", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }


        String apiURL = "https://openapi.naver.com/v1/search/encyc?query=" + text;    // JSON 결과
        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // XML 결과


        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL, requestHeaders);

        find = responseBody;



       //System.out.println(responseBody);
    }


    private static String get(String apiUrl, Map<String, String> requestHeaders) {
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
}

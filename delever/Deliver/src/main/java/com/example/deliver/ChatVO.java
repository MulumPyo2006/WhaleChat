package com.example.deliver;

import java.sql.Time;

public class ChatVO {
    private String chattingRoomName;
    private String id;
    private String name;
    private String chatContent;
    private Time chatTime;

    public ChatVO(String chattingRoomName, String id, Time chatTime) {
        this.chattingRoomName = chattingRoomName;
        this.id = id;
        this.name = name;
        this.chatContent = chatContent;
        this.chatTime = chatTime;
    }

    public String getChattingRoomName() {
        return chattingRoomName;
    }

    public void setChattingRoomName(String chattingRoomName) {
        this.chattingRoomName = chattingRoomName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContents) {
        this.chatContent = chatContent;
    }

    public Time getChatTime() {
        return chatTime;
    }

    public void setChatTime(Time chatTime) {
        this.chatTime = chatTime;
    }
}

package com.example.deliver;

public class ChatBuildVO {
    private String name;
    private String roomContent;

    public ChatBuildVO(String name, String roomContent) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomContent() {
        return roomContent;
    }

    public void setRoomContent(String roomContent) {
        this.roomContent = roomContent;
    }
}

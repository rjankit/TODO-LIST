package com.npdevs.project2;

public class Todo {
    private String title;
    private String description;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;

    public String getTitle() {
        return title;
    }

    public Todo() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Todo(String title, String description,String time) {
        this.title = title;
        this.description = description;
        this.time=time;
    }
}

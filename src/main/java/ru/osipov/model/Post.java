package ru.osipov.model;

public class Post {
    private long id;
    private String content;
    private boolean isRemoved;

    public Post() {
    }

    public Post(long id, String content) {
        this.id = id;
        this.content = content;
        this.isRemoved = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void removePost() {
        this.isRemoved = true;
    }

    public boolean isRemoved() {
        return isRemoved;
    }
}
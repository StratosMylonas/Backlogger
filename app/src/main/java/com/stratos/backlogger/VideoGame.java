package com.stratos.backlogger;

public class VideoGame {
    private int id;
    private String title;
    private String genre;
    private String platform;
    private int yearOfRelease;
    private int durationHours;

    public VideoGame(int id, String title, String genre, String platform, int yearOfRelease, int durationHours) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.platform = platform;
        this.yearOfRelease = yearOfRelease;
        this.durationHours = durationHours;
    }

    public void setId(int id) { this.id = id; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public void setDurationHours(int durationHours) {
        this.durationHours = durationHours;
    }

    public int getId() { return id; }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getPlatform() {
        return platform;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public int getDurationHours() {
        return durationHours;
    }
}

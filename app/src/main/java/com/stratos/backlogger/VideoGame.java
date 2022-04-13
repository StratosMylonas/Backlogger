package com.stratos.backlogger;

public class VideoGame {
    private String title;
    private String genre;
    private String platform;
    private int yearOfRelease;
    private int durationHours;

    public VideoGame(String title, String genre, String platform, int yearOfRelease, int durationHours) {
        this.title = title;
        this.genre = genre;
        this.platform = platform;
        this.yearOfRelease = yearOfRelease;
        this.durationHours = durationHours;
    }

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

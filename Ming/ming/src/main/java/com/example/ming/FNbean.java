package com.example.ming;

public class FNbean {
    private String folder;
    private String live;

    public FNbean() {
    }

    public FNbean(String folder, String live) {
        this.folder = folder;
        this.live = live;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getLive() {
        return live;
    }

    public void setLive(String name) {
        this.live = name;
    }

    @Override
    public String toString() {
        return "FNbean{" +
                "folder='" + folder + '\'' +
                ", name='" + live + '\'' +
                '}';
    }
}

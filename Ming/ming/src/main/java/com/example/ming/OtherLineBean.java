package com.example.ming;

public class OtherLineBean {
   /* <OTHER_Line help="其他线路" type="CDN" folder="test">
        <V>sun01.acc77.net/385214775</V>
    </OTHER_Line>*/

    private String type;
    private String folder;
    private String v;

    public OtherLineBean(String type, String folder, String v) {
        this.type = type;
        this.folder = folder;
        this.v = v;
    }

    public OtherLineBean(){}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    /*@Override
    public String toString() {
        return "help = " + help + " type = " + type + " folder = " + folder + " v = " + v;
    }*/

    @Override
    public String toString() {
        return "OtherLineBean{" +
                ", type='" + type + '\'' +
                ", folder='" + folder + '\'' +
                ", v='" + v + '\'' +
                '}';
    }
}

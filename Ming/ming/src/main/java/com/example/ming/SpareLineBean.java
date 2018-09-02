package com.example.ming;

import java.util.List;

public class SpareLineBean {
//    <SPARE_Line help="备用线路" type="NO" folder="test">
//        <V>jv2.tp33.net:66</V>
//        <V>wv5.tp33.net:66</V>
//        <V>hv1.tp33.net:66</V>
//        <V>hkvd1.tp33.net:66</V>
//        <V>svd1.tp33.net:66</V>
//    </SPARE_Line>

    private String type;
    private String folder;
    private List<String> vBean;

    public SpareLineBean() {
    }

    public SpareLineBean(String type, String folder, List<String> vBean) {
        this.type = type;
        this.folder = folder;
        this.vBean = vBean;
    }

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

    public List<String> getvBean() {
        return vBean;
    }

    public void setvBean(List<String> vBean) {
        this.vBean = vBean;
    }

    @Override
    public String toString() {
        return "SpareLineBean{" +
                ", type='" + type + '\'' +
                ", folder='" + folder + '\'' +
                ", vBean=" + vBean +
                '}';
    }
}

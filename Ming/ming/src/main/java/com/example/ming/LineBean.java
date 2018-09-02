package com.example.ming;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xyzing on 2018/5/31.
 */

public class LineBean implements Serializable {
    //每个线路下的<V>节点的值 +  “/”  +   “folder”的值，     就是对应线路的路径
    private String type;
    private String folder;
    private List<String> space_line_list;

    public LineBean(String type, String folder, List<String>space_line_list) {
        this.type = type;
        this.folder = folder;
        this.space_line_list = space_line_list;
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

    public List<String> getSpace_line_list() {
        return space_line_list;
    }

    public void setSpace_line_list(List<String> space_line_list) {
        this.space_line_list = space_line_list;
    }

    @Override
    public String toString() {
        return "LineBean{" +
                "type='" + type + '\'' +
                ", folder='" + folder + '\'' +
                ", space_line_list=" + space_line_list +
                '}';
    }
}

package com.example.ming;

import java.util.List;

public class GameVideoBean {
    private OtherLineBean otherLineBean;
    private SpareLineBean spareLineBean;
    private List<TypeBean> typeBeanList;

    public GameVideoBean() {
    }

    public OtherLineBean getOtherLineBean() {
        return otherLineBean;
    }

    public void setOtherLineBean(OtherLineBean otherLineBean) {
        this.otherLineBean = otherLineBean;
    }

    public SpareLineBean getSpareLineBean() {
        return spareLineBean;
    }

    public void setSpareLineBean(SpareLineBean spareLineBean) {
        this.spareLineBean = spareLineBean;
    }

    public List<TypeBean> getTypeBeanList() {
        return typeBeanList;
    }

    public void setTypeBeanList(List<TypeBean> typeBeanList) {
        this.typeBeanList = typeBeanList;
    }

    @Override
    public String toString() {
        return "GameVideoBean{" +
                "otherLineBean=" + otherLineBean +
                ", spareLineBean=" + spareLineBean +
                ", typeBeanList=" + typeBeanList +
                '}';
    }

}

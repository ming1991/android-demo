package com.example.ming;

import java.util.List;

public class TypeBean {
    /*<Type name="LP" gameFQ="1" line="5" liveFQ="*/

    private String name;
    private List<FNbean> liveFQ;

    public TypeBean(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FNbean> getLiveFQ() {
        return liveFQ;
    }

    public void setLiveFQ(List<FNbean> liveFQ) {
        this.liveFQ = liveFQ;
    }

    @Override
    public String toString() {
        return "TypeBean{" +
                "name='" + name + '\'' +"\n"+
                ", liveFQ=" + liveFQ +
                '}'+"\n";
    }
}

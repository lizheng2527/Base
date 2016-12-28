
package com.zdhx.androidbase.entity;

import java.io.Serializable;

/**
 * 互动交流动态类
 */
public class ScroListBean implements Serializable {

    private int ranking;

    private String name;

    private String scro;

    private String upNumbers;

    private String downNumbers;

    public ScroListBean() {
    }

    public ScroListBean(int ranking, String name, String scro, String upNumbers, String downNumbers) {
        this.ranking = ranking;
        this.name = name;
        this.scro = scro;
        this.upNumbers = upNumbers;
        this.downNumbers = downNumbers;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScro() {
        return scro;
    }

    public void setScro(String scro) {
        this.scro = scro;
    }

    public String getUpNumbers() {
        return upNumbers;
    }

    public void setUpNumbers(String upNumbers) {
        this.upNumbers = upNumbers;
    }

    public String getDownNumbers() {
        return downNumbers;
    }

    public void setDownNumbers(String downNumbers) {
        this.downNumbers = downNumbers;
    }
}

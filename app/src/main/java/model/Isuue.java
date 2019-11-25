package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Isuue implements Serializable {

    @SerializedName("ID")
    @Expose
    private int id;
    @SerializedName("Name")
    @Expose
    private String name ;
    @SerializedName("ProfName")
    @Expose
    private String ProfName;
    @SerializedName("IcdName")
    @Expose
    private String icdName;
    @SerializedName("Accuracy")
    @Expose
    private double accuracy;

    @SerializedName("Ranking")
    @Expose
    private int ranking;


    public Isuue(int id, String name, String profName, String icdName, double accuracy, int ranking) {
        this.id = id;
        this.name = name;
        ProfName = profName;
        this.icdName = icdName;
        this.accuracy = accuracy;
        this.ranking = ranking;
    }

    public Isuue(int id, String name, String profName, String icdName, int accuracy) {
        this.id = id;
        this.name = name;
        ProfName = profName;
        this.icdName = icdName;
        this.accuracy = accuracy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfName() {
        return ProfName;
    }

    public void setProfName(String profName) {
        ProfName = profName;
    }

    public String getIcdName() {
        return icdName;
    }

    public void setIcdName(String icdName) {
        this.icdName = icdName;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }
    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
}

package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class diagnosis {
    @SerializedName("Specialisation")
    @Expose
    List<specializationSpecifics> specialization;
    @SerializedName("Issue")
    @Expose
    Isuue issue;

    public diagnosis( Isuue issue , List<specializationSpecifics> specialization) {
       // this.specialization = specialization;
        this.issue = issue;
        this.specialization= specialization;
    }

    public List<specializationSpecifics> getSpecialization() {
        return specialization;
    }

    public void setSpecialization(List<specializationSpecifics> specialization) {
        this.specialization = specialization;
    }

    public Isuue getIssue() {
        return issue;
    }

    public void setIssue(Isuue issue) {
        this.issue = issue;
    }
}

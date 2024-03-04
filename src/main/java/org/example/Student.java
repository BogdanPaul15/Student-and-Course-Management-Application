package org.example;

import java.util.ArrayList;

public class Student {
    private String nume;
    private double medie;

    private ArrayList<String> preferinte;

    private String curs;

    public Student(String nume) {
        this.nume = nume;
        this.medie = 0.0;
    }

    public String getNume() {
        return nume;
    }

    public double getMedie() {
        return medie;
    }

    public void setMedie(double medie) {
        this.medie = medie;
    }

    public void setPreferinte(ArrayList<String> preferinte) {
        this.preferinte = preferinte;
    }

    public ArrayList<String> getPreferinte() {
        return preferinte;
    }

    public String getCurs() {
        return curs;
    }

    public void setCurs(String curs) {
        this.curs = curs;
    }
}

class StudentLicenta extends Student {
    public StudentLicenta(String nume) {
        super(nume);
    }
}
class StudentMaster extends Student {
    public StudentMaster(String nume) {
        super(nume);
    }
}

package org.example;

import java.util.ArrayList;

public class Curs<T extends Student> {
    private String denumire;
    private int capacitateMaxima;
    private ArrayList<T> studenti;

    public Curs(String denumire, int capacitateMaxima) {
        this.denumire = denumire;
        this.capacitateMaxima = capacitateMaxima;
        this.studenti = new ArrayList<>();
    }

    public String getDenumire() {
        return denumire;
    }

    public int getCapacitateMaxima() {
        return capacitateMaxima;
    }

    public ArrayList<T> getStudenti() {
        return studenti;
    }

    public void adaugaStudent(T student) {
        studenti.add(student);
    }
    public double getLastStudentMedie() {
        if (studenti.isEmpty()) {
            return 0.0;
        }
        double ceaMaiMicaMedie = studenti.get(0).getMedie();
        for (T student: studenti) {
            if (student.getMedie() < ceaMaiMicaMedie) {
                ceaMaiMicaMedie = student.getMedie();
            }
        }
        return ceaMaiMicaMedie;
    }
}

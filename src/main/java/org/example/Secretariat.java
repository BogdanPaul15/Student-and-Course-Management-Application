package org.example;

import java.io.*;
import java.util.*;

class NumeDuplicatException extends Exception {
    public NumeDuplicatException(String nume) {
        super("Student duplicat: " + nume);
    }
}

public class Secretariat {

    private final ArrayList<Student> studenti;
    private final HashMap<String, Curs<?>> cursuri;
    public Secretariat() {
        this.studenti = new ArrayList<>();
        this.cursuri = new HashMap<>();
    }

    public String proceseazaTest(String path) {
        return "src/main/resources/" + path + "/";
    }
    public void executaComenzi(String path, String nume) {
        String fisier_in = path + nume + ".in";
        String fisier_out = path + nume + ".out";
        try {
            File myObj = new File(fisier_in);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] params = data.split(" - ");
                switch (params[0]) {
                    case "adauga_student":
                        try {
                            this.adaugaStudent(params[2], params[1]);
                        } catch (NumeDuplicatException e) {
                            this.writeToFile(e.getMessage(), fisier_out);
                        }
                        break;
                    case "citeste_mediile":
                        this.citesteMediile(path);
                        break;
                    case "posteaza_mediile":
                        this.posteazaMediile(fisier_out);
                        break;
                    case "contestatie":
                        this.actualizeazaMediaStudent(params[1], Double.parseDouble(params[2]));
                        break;
                    case "adauga_curs":
                        this.adaugaCurs(params[1], params[2], Integer.parseInt(params[3]));
                        break;
                    case "adauga_preferinte":
                        this.adaugaPreferinte(params[1], Arrays.copyOfRange(params, 2, params.length));
                        break;
                    case "repartizeaza":
                        this.repartizeaza();
                        break;
                    case "posteaza_curs":
                        this.posteazaCurs(params[1], fisier_out);
                        break;
                    case "posteaza_student":
                        this.posteazaStudent(params[1], fisier_out);
                        break;
                    default:
                        System.out.println("Comandă necunoscută. Eroare.");
                        break;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void adaugaStudent(String nume, String programStudii) throws NumeDuplicatException {
        for (Student student : studenti) {
            if (student.getNume().equals(nume)) {
                throw new NumeDuplicatException(nume);
            }
        }
        Student student;
        switch (programStudii) {
            case "licenta":
                student = new StudentLicenta(nume);
                break;
            case "master":
                student = new StudentMaster(nume);
                break;
            default:
                student = null;
                break;
        }
        studenti.add(student);
    }

    public void citesteMediile(String path) {
        File folder = new File(path);
        File[] listaFisiere = folder.listFiles((dir, name) -> name.startsWith("note_"));
        if (listaFisiere != null) {
            for (File fisier: listaFisiere) {
                try {
                    File myObj = new File(String.valueOf(fisier));
                    Scanner myReader = new Scanner(myObj);
                    while (myReader.hasNextLine()) {
                        String data = myReader.nextLine();
                        String[] params = data.split(" - ");
                        actualizeazaMediaStudent(params[0], Double.parseDouble(params[1]));
                    }
                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        }
    }

    private void actualizeazaMediaStudent(String numeStudent, double mediaGenerala) {
        for (Student student: studenti) {
            if (Objects.equals(student.getNume(), numeStudent)) {
                student.setMedie(mediaGenerala);
            }
        }
    }

    public void posteazaMediile(String path) {
        StringBuilder content = new StringBuilder();
        studenti.sort(Comparator.comparing(Student::getMedie)
                .reversed()
                .thenComparing(Student::getNume));
        for (Student student: studenti) {
            content.append(student.getNume()).append(" - ").append(student.getMedie()).append("\n");
        }
        content.deleteCharAt(content.length() - 1);
        writeToFile(content.toString(), path);
    }

    public void adaugaCurs(String programStudii, String denumire, Integer numarMaxim) {
        if (Objects.equals(programStudii, "licenta")) {
            Curs<StudentLicenta> curs = new Curs<>(denumire, numarMaxim);
            cursuri.put(denumire, curs);
        } else if (Objects.equals(programStudii, "master")) {
            Curs<StudentMaster> curs = new Curs<>(denumire, numarMaxim);
            cursuri.put(denumire, curs);
        }
    }

    public void adaugaPreferinte(String nume, String[] preferinte) {
        for (Student student: studenti) {
            if (Objects.equals(nume, student.getNume())) {
                student.setPreferinte(new ArrayList<>(Arrays.asList(preferinte)));
                break;
            }
        }
    }
    public Student gasesteStudent(String nume) {
        for (Student student: studenti) {
            if (Objects.equals(nume, student.getNume())) {
                return student;
            }
        }
        return null;
    }

    public void repartizeaza() {
        studenti.sort(Comparator.comparing(Student::getMedie)
                .reversed()
                .thenComparing(Student::getNume));
        for (Student student : studenti) {
            for (String preferinta: student.getPreferinte()) {
                Curs<? extends Student> curs = cursuri.get(preferinta);
                if (curs != null && curs.getStudenti().size() < curs.getCapacitateMaxima()) {
                    if (asigneazaCurs(student, preferinta, curs)) break;
                } else if (curs != null && curs.getStudenti().size() >= curs.getCapacitateMaxima()) {
                    if (student.getMedie() == curs.getLastStudentMedie()) {
                        if (asigneazaCurs(student, preferinta, curs)) break;
                        if (asigneazaCurs(student, preferinta, curs)) break;
                    }
                }
            }
            if (student.getCurs() == null) {
                for (Curs<?> curs: cursuri.values()) {
                    if (curs.getStudenti().size() < curs.getCapacitateMaxima()) {
                        asigneazaCurs(student, curs.getDenumire(), curs);
                    }
                }
            }
        }
    }

    public boolean asigneazaCurs(Student student, String preferinta, Object curs) {
        if (student instanceof StudentLicenta) {
            ((Curs<StudentLicenta>) curs).adaugaStudent((StudentLicenta) student);
            student.setCurs(preferinta);
            return true;
        } else if (student instanceof StudentMaster) {
            ((Curs<StudentMaster>) curs).adaugaStudent((StudentMaster) student);
            student.setCurs(preferinta);
            return true;
        }
        return false;
    }

    public void posteazaCurs(String denumire, String path) {
        Curs<? extends Student> curs = cursuri.get(denumire);
        StringBuilder content = new StringBuilder();
        curs.getStudenti().sort(Comparator.comparing(Student::getNume));
        content.append(curs.getDenumire()).append(" (").append(curs.getCapacitateMaxima()).append(")\n");
        for (Student student: curs.getStudenti()) {
            content.append(student.getNume()).append(" - ").append(student.getMedie()).append("\n");
        }
        content.deleteCharAt(content.length() - 1);
        writeToFile(content.toString(), path);
    }
    public void posteazaStudent(String nume, String path) {
        Student student = gasesteStudent(nume);
        StringBuilder content = new StringBuilder();
        if (student instanceof StudentMaster) {
            content.append("Student Master: ");
        } else if (student instanceof StudentLicenta) {
            content.append("Student Licenta: ");
        }
        content.append(student.getNume()).append(" - ").append(student.getMedie()).append(" - ").append(student.getCurs());
        writeToFile(content.toString(), path);
    }
    public void writeToFile(String message, String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.append("***\n").append(message).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

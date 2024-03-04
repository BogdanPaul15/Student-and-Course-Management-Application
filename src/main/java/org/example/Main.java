package org.example;

public class Main {
    public static void main(String[] args){
        Secretariat secretariat = new Secretariat();
        String folder = secretariat.proceseazaTest(args[0]);
        secretariat.executaComenzi(folder, args[0]);
    }
}

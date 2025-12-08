package hu.dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jatekos {
    private final String nev;
    private final List<String> dobasElozmenyek;

    public Jatekos(String nev) {
        this.nev = nev;
        this.dobasElozmenyek = new ArrayList<>();
    }

    public String getNev() {
        return nev;
    }

    public List<String> getDobasElozmenyek() {
        return dobasElozmenyek;
    }

    public void logDobas(String kifejezes, int eredmeny) {
        dobasElozmenyek.add(kifejezes + " → " + eredmeny);
    }

    public void listazDobasok() {
        System.out.println("\nDobások előzményei - " + nev + ":");
        if (dobasElozmenyek.isEmpty()) {
            System.out.println("  Nincs még dobás.");
        } else {
            for (String d : dobasElozmenyek) {
                System.out.println("  " + d);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("🎲 DnD Dice Roller");
        System.out.print("Add meg a játékos nevét: ");
        String nev = scanner.nextLine().trim();

        Jatekos jatekos = new Jatekos(nev);

        boolean folytat = true;

        while (folytat) {
            System.out.print("\nÍrj be egy dobást (pl. 1d20, 2d6+3): ");
            String input = scanner.nextLine().trim();

            try {
                int result = Dobas.rollDice(input);
                System.out.println(jatekos.getNev() + " dobása: " + result);
                jatekos.logDobas(input, result);
            } catch (IllegalArgumentException e) {
                System.out.println(" Hibás formátum! Próbáld újra (pl. 2d6+1)");
                continue;
            }

            System.out.print("\nSzeretnél újra dobni? (i/n): ");
            String valasz = scanner.nextLine().trim().toLowerCase();

            if (valasz.equals("n")) {
                folytat = false;
            }
        }

        System.out.println("\nKilépés...");
        jatekos.listazDobasok();

        scanner.close();
    }
}

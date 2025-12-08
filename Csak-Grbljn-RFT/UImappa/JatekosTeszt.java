package UImappa;

import java.util.*;
import java.io.*;

public class JatekosTeszt {

    private static final String NEVFAJL = "nevek.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        Set<String> hasznaltNevek = beolvasNeveket();

        System.out.println("🎲 DnD Dice Roller");

        System.out.print("Add meg a neved: ");
        String nev = sc.nextLine().trim().toLowerCase();

        if (hasznaltNevek.contains(nev)) {
            throw new IllegalArgumentException("Ezzel a névvel már játszottál! Válassz másikat.");
        } else {
            hasznaltNevek.add(nev);
            mentsNevet(nev);
            System.out.println("Üdv, " + nev + "!");
        }

        boolean folytat = true;

        while (folytat) {
            System.out.print("\nAdj meg egy dobást (pl. 2d6+3): ");
            String dob = sc.nextLine().trim().toLowerCase().replace(" ", "");

            int szam = 0, lap = 0, mod = 0;

            try {
                if (!dob.matches("\\d+d\\d+([+-]\\d+)?")) {
                    throw new IllegalArgumentException("Hibás formátum!");
                }

                String[] parts = dob.split("d");
                szam = Integer.parseInt(parts[0]);

                if (parts[1].contains("+")) {
                    String[] sub = parts[1].split("\\+");
                    lap = Integer.parseInt(sub[0]);
                    mod = Integer.parseInt(sub[1]);
                } else if (parts[1].contains("-")) {
                    String[] sub = parts[1].split("-");
                    lap = Integer.parseInt(sub[0]);
                    mod = -Integer.parseInt(sub[1]);
                } else {
                    lap = Integer.parseInt(parts[1]);
                }

                int osszeg = 0;
                System.out.println("Dobások eredménye:");
                for (int i = 1; i <= szam; i++) {
                    int eredmeny = rand.nextInt(lap) + 1;
                    osszeg += eredmeny;
                    System.out.println("Dobás " + i + ": " + eredmeny);
                }

                osszeg += mod;
                System.out.println("Összesített eredmény: " + osszeg);

            } catch (Exception e) {
                System.out.println("Hibás dobásformátum! Használj pl. 2d6+3");
            }

            System.out.print("\nSzeretnél még dobni? (i/n): ");
            String valasz = sc.nextLine().trim().toLowerCase();
            if (!valasz.equals("i")) {
                folytat = false;
            }
        }

        System.out.println("\nKöszönöm a játékot, " + nev + "!");
        sc.close();
    }


    private static Set<String> beolvasNeveket() {
        Set<String> nevek = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(NEVFAJL))) {
            String sor;
            while ((sor = br.readLine()) != null) {
                nevek.add(sor.trim().toLowerCase());
            }
        } catch (IOException e) {
            // fájl még nem létezik, ez nem hiba
        }
        return nevek;
    }


    private static void mentsNevet(String nev) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NEVFAJL, true))) {
            bw.write(nev);
            bw.newLine();
        } catch (IOException e) {
            System.out.println(" Nem sikerült menteni a nevet: " + e.getMessage());
        }

    }
}

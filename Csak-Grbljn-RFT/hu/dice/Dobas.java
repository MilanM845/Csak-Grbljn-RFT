package hu.dice;

import java.util.Random;
import java.util.Scanner;

public class Dobas {
    private static final Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("DnD Dice Roller");
        System.out.println("Írj be egy dobásformátumot (pl. 1d20, 2d6+3, q a kilépéshez):");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                System.out.println("Kilépés...");
                break;
            }

            try {
                int result = rollDice(input);
                System.out.println("Eredmény: " + result);
            } catch (IllegalArgumentException e) {
                System.out.println("Hibás formátum! Próbáld újra (pl. 2d6+1)");
            }
        }
        scanner.close();
    }

    public static int rollDice(String expression) {
        expression = expression.toLowerCase().replace(" ", "");

        if (!expression.matches("\\d+d\\d+([+-]\\d+)?")) {
            throw new IllegalArgumentException("Érvénytelen formátum: " + expression);
        }

        String[] parts = expression.split("d");
        int numberOfDice = Integer.parseInt(parts[0]);

        int modifier = 0;
        int diceSides;
        if (parts[1].contains("+")) {
            String[] subParts = parts[1].split("\\+");
            diceSides = Integer.parseInt(subParts[0]);
            modifier = Integer.parseInt(subParts[1]);
        } else if (parts[1].contains("-")) {
            String[] subParts = parts[1].split("-");
            diceSides = Integer.parseInt(subParts[0]);
            modifier = -Integer.parseInt(subParts[1]);
        } else {
            diceSides = Integer.parseInt(parts[1]);
        }

        int total = 0;
        for (int i = 0; i < numberOfDice; i++) {
            total += random.nextInt(diceSides) + 1;
        }

        return total + modifier;
    }
}

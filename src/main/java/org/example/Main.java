package org.example;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        int rondes = 5;

        Spelverdeling solver = new Spelverdeling(6, 5, 2, rondes);

        Optional<String[][]> solution = solver.solve(6, 5, 2, rondes);

        int spelletjes=5;
        if (solution.isEmpty()) {
            System.out.println("Geen oplossing");
        } else {
            String[][] spelverdeling = solution.get();

            // Print game numbers on the top
            System.out.print("          ");
            for (int s = 0; s < spelletjes; s++) {
                System.out.printf("Spel %d  ", s + 1);
            }
            System.out.println();

            // Print the grid with round numbers on the side
            for (int r = 0; r < rondes; r++) {
                System.out.printf("Ronde %d:  ", r + 1);
                for (int s = 0; s < spelletjes; s++) {
                    System.out.printf("%-8s  ", spelverdeling[r][s]);
                }
                System.out.println();
            }
        }

    }


}


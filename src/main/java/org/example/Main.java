package org.example;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Spelverdeling solver = new Spelverdeling(6,5,2,5);

        Optional<String[][]> solution = solver.solve(6,5,2,5);
        if (solution.isEmpty()) {
            System.out.println("Geen oplossing");
        }
        else {
            /*
            String[][] oplossing = solution.get();
            for (String[]  rondes: oplossing) {
                System.out.println(rondes);
                */

            }
        }

    }

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
            for (int r = 0; r < rondes; r++) {
                System.out.printf("Ronde %d:\n", r + 1);
                for (int s = 0; s < spelletjes; s++) {
                    System.out.printf("  Spel %d: %s\n", s + 1, spelverdeling[r][s]);
                }
            }
            }
        }


    }

/*
    
    weet niet of dit werkt ------------------------------------------------------------------------------------------
        
        private static boolean backtrack(String[][] result, int ploegen, int spelletjes, int dubbels, int rondes, int ronde) {
        if (ronde == rondes) {
            return true;
        }

        for (int spel = 0; spel < spelletjes; spel++) {
            String[] deelnemers = new String[2];

            for (int i = 0; i < 2; i++) {
                boolean deelnemerGevonden = false;

                for (int ploeg = 0; ploeg < ploegen && !deelnemerGevonden; ploeg++) {
                    if (ploegKanSpelen(result, spel, ploeg, ronde, i, dubbels)) {
                        deelnemers[i] = "" + (char) ('A' + ploeg);
                        deelnemerGevonden = true;
                    }
                }

                if (!deelnemerGevonden) {
                    return false; 
                }
            }

            result[ronde][spel] = deelnemers[0] + "-" + deelnemers[1];

            if (backtrack(result, ploegen, spelletjes, dubbels, rondes, ronde + 1)) {
                return true; 
            }

            result[ronde][spel] = null; 
        }

        return false; 
    }
    
    */
    


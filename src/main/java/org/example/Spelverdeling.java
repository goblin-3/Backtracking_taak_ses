package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Spelverdeling {

    private int rondesT;
    private int ploegenT;
    private int spellenT;
    private int dubbelsT;


    private ArrayList<Character> TeamList = new ArrayList<>();
    private int[][] gamesPlayed;
    private int[][] SpelGespeeld;
    private boolean[][] playedCombinations;

    private int currentGame = 0;
    private int team1 = -1;
    private int team2 = -1;


    public Spelverdeling(int ploegenT, int spellenT, int dubbelsT, int rondesT) {
        this.rondesT = rondesT;
        this.ploegenT = ploegenT;
        this.spellenT = spellenT;
        this.dubbelsT = dubbelsT;
    }

    public Optional<String[][]> solve(int ploegen, int spellen, int dubbels, int rondes) {
        List<String> combinations = setupAllOptions(ploegen);
        String[][] solutionList = new String[rondes][spellen];
        gamesPlayed = new int[ploegen][ploegen];
        SpelGespeeld = new int[ploegen][spellen];
        playedCombinations = new boolean[ploegen][ploegen];

        Teamlist();
        //  int [][] availablespellen= new int[rondes][spellen];


        return solve(ploegen, 0, dubbels, 0, combinations, solutionList);
    }

    private Optional<String[][]> solve(int ploegen, int currentSpellen, int dubbels, int currentRonde, List<String> combinations, String[][] solution) {
        if (currentRonde == rondesT) {
            if (currentSpellen == spellenT) {

                return Optional.of(solution);


            } else {
                return solve(ploegen, currentSpellen + 1, dubbels, currentRonde, combinations, solution);
            }
        }

        if (currentSpellen == spellenT) {
            return solve(ploegen, 0, dubbels, currentRonde + 1, combinations, solution);
        }

        if (IsAtEndOfSpellen(solution,currentSpellen,currentRonde)){
            List<String> Recombinations = setupAllOptions(ploegen);
            return solve(ploegen, 0, dubbels, currentRonde + 1, Recombinations, solution);
        }

        if(combinations.isEmpty()){
            //combinations = setupAllOptions(ploegen);
            return solve(ploegen, currentSpellen + 1, dubbels, currentRonde, setupAllOptions(ploegen), solution);
        }
        String combination = combinations.get(0);


        // Check if the current combination is valid
        if (isCombinationValid(combination, currentRonde, currentSpellen, solution)) {
            // Update the solution with the current combination
            solution[currentRonde][currentSpellen] = combination;

            // Mark the teams and games as played
            markTeamsAndGamesAsPlayed(combination, currentRonde, currentSpellen);

            // Recursively solve the subproblem by moving to the next game
            Optional<String[][]> result = solve(ploegen, currentSpellen + 1, dubbels, currentRonde, combinations, solution);

            // If a valid solution is found, return it
            if (result.isPresent()) {
                return result;
            }

            // Backtrack by removing the current combination and unmarking teams/games as played
            solution[currentRonde][currentSpellen] = null;
            unmarkTeamsAndGamesAsPlayed(combination, currentRonde, currentSpellen);
        }
        if (combinations.isEmpty()) {
            return Optional.empty();
        }
        // Recursively solve the subproblem by trying the remaining combinations


        combinations.remove(0);

        return solve(ploegen, currentSpellen, dubbels, currentRonde, combinations, solution);
    }


    private boolean isCombinationValid(String combination, int currentRonde, int currentSpellen, String[][] solution) {
        // Check if the teams in the combination have already played the maximum number of games
        String[] teams = combination.split(" - ");
        int teamA = teams[0].charAt(0) - 'A';
        int teamB = teams[1].charAt(0) - 'A';

        if (gamesPlayed[teamA][teamB] >= dubbelsT || gamesPlayed[teamB][teamA] >= dubbelsT) {
            return false;
        }

        // Check if the current game has already been played in a previous round
        for (int r = 0; r < currentRonde; r++) {
            if (solution[r][currentSpellen] != null && solution[r][currentSpellen].equals(combination)) {
                return false;
            }
        }

        // Check if any of the teams in the current game have already played the maximum number of games in this round
        if (SpelGespeeld[teamA][currentSpellen] >= 1 || SpelGespeeld[teamB][currentSpellen] >= 1) {
            return false;
        }

        // Check if any of the teams in the current game have played in the current round before
        for (int s = 0; s < currentSpellen; s++) {
            if (solution[currentRonde][s] != null) {
                String[] previousTeams = solution[currentRonde][s].split(" - ");
                if (previousTeams[0].equals(teams[0]) || previousTeams[0].equals(teams[1]) ||
                        previousTeams[1].equals(teams[0]) || previousTeams[1].equals(teams[1])) {
                    return false;
                }
            }
        }

        if (playedCombinations[teamA][teamB] || playedCombinations[teamB][teamA]) {
            return false;
        }


        return true;
    }

    private void markTeamsAndGamesAsPlayed(String combination, int currentRonde, int currentSpellen) {
        String[] teams = combination.split(" - ");
        int teamA = teams[0].charAt(0) - 'A';
        int teamB = teams[1].charAt(0) - 'A';

        gamesPlayed[teamA][teamB]++;
        gamesPlayed[teamB][teamA]++;

        SpelGespeeld[teamA][currentSpellen]++;
        SpelGespeeld[teamB][currentSpellen]++;
        playedCombinations[teamA][teamB] = true;
        playedCombinations[teamB][teamA] = true;


    }


    private void unmarkTeamsAndGamesAsPlayed(String combination, int currentRonde, int currentSpellen) {
        String[] teams = combination.split(" - ");
        int teamA = teams[0].charAt(0) - 'A';
        int teamB = teams[1].charAt(0) - 'A';

        gamesPlayed[teamA][teamB]--;
        gamesPlayed[teamB][teamA]--;

        SpelGespeeld[teamA][currentSpellen]--;
        SpelGespeeld[teamB][currentSpellen]--;
        playedCombinations[teamA][teamB] = false;
        playedCombinations[teamB][teamA] = false;



    }

    public static List<String> setupAllOptions(int numberOfTeams) {
        List<String> rounds = new ArrayList<>();

        // Controleer of het aantal teams geldig is
        // Maak een array van letters om teams voor te stellen (A, B, C, ...)
        char[] teamLetters = new char[numberOfTeams];
        for (int i = 0; i < numberOfTeams; i++) {
            teamLetters[i] = (char) ('A' + i);
        }

        // Genereer de combinaties van rondes
        for (int i = 0; i < numberOfTeams - 1; i++) {
            for (int j = i + 1; j < numberOfTeams; j++) {
                String round = teamLetters[i] + " - " + teamLetters[j];
                rounds.add(round);
            }
        }

        return rounds;
    }

    public void Teamlist() {
        TeamList.clear(); // Clear the existing team list

        for (int i = 0; i < ploegenT; i++) {
            TeamList.add((char) ('A' + i));
        }
    }


    public boolean IsAtEndOfSpellen(String[][] solution, int currentSpellen, int currentRonde) {
        // Check if all teams have played exactly one game in each round
        int gamesPlayedCount = 0;
        for (char team : TeamList) {
            int gamesPlayed = 0;
            for (int r = 0; r <= currentRonde; r++) {
                for (int s = 0; s < currentSpellen; s++) {
                    if (solution[r][s] != null) {
                        String[] teams = solution[r][s].split(" - ");
                        if (teams[0].charAt(0) == team || teams[1].charAt(0) == team) {
                            gamesPlayed++;
                        }
                    }
                }
            }
            if (gamesPlayed == currentRonde + 1) {
                gamesPlayedCount++;
            }
        }
        return gamesPlayedCount == TeamList.size();
    }


}





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
    private int [][] gamesPlayed;
    private int [][] SpelGespeeld;
    private int currentGame = 0;
    private int team1=-1;
    private int team2=-1;


    public Spelverdeling(int ploegenT, int spellenT, int dubbelsT, int rondesT) {
        this.rondesT = rondesT;
        this.ploegenT = ploegenT;
        this.spellenT = spellenT;
        this.dubbelsT = dubbelsT;
    }

    public Optional<String[][]>solve(int ploegen, int spellen, int dubbels, int rondes){
        setupTeamList(ploegen);
        String[][] temporarySolution = new String[rondes][spellen];
        gamesPlayed = new int[ploegen][ploegen];
        SpelGespeeld = new int[ploegen][spellen];
        List<Integer> availablespellen = new ArrayList<>();
        for (int i=0;i<spellen;i++){
            availablespellen.add(i);
        }

        return solve(ploegen,0,dubbels,0,temporarySolution,availablespellen);
    }

    private Optional<String[][]>solve(int ploegen, int currentspellen, int dubbels, int currentronde, String[][] solution, List<Integer> previousattemps){

        if (currentronde == rondesT){
            return Optional.of(solution);
        }
        if (rondesT == currentronde+1){
            if(currentspellen == spellenT){
                return Optional.empty();
            }


          int  huidigeRonde = currentronde -1;
          int huidigspel =currentspellen;
            return solve(ploegen, huidigspel, dubbels, huidigeRonde, solution,previousattemps);
        }
        if (currentGame!= spellenT){
            return Optional.empty();
        }
        int huidigeRonde =currentronde;
        int huidigSpel = currentspellen;

        for (int i = 0; i < ploegenT; i++) {
            for (int j = i + 1; j < ploegenT; j++) {
                if (getGamesPlayed(i, j) < dubbels && !previousattemps.contains(i * 100 + j)) {
                    team1 = i;
                    team2 = j;
                    break;
                }
            }
            if (team1 != -1 && team2 != -1) {
                break;
            }
        }





        for (int i = 0; i < spellenT; i++) {
            if (!getSpelGespeeld(team1, i) && !getSpelGespeeld(team2, i)) {
                solution[currentronde][currentspellen] = TeamList.get(team1) + "-" + TeamList.get(team2) ;
                addGame(team1, team2);
                addSpelGespeeld(team1, i);
                addSpelGespeeld(team2, i);
                previousattemps.add(team1 * 100 + team2);
                Optional<String[][]> result = solve(ploegen, currentspellen + 1, dubbels, huidigeRonde, solution, previousattemps);
                if (result.isPresent()) {
                    return result;
                }
                removeGame(team1, team2);
                removeSpelGespeeld(team1, i);
                removeSpelGespeeld(team2, i);
                previousattemps.remove(previousattemps.size() - 1);
            }
        }
    return solve(ploegen,0,dubbels,currentronde-1,solution,previousattemps);

    }

public void addGame(int team1,int team2){
        gamesPlayed[team1][team2]++;
        gamesPlayed[team2][team1]++;
}
public void removeGame(int team1,int team2){
    gamesPlayed[team1][team2]--;
    gamesPlayed[team2][team1]--;
}
    public int getGamesPlayed(int team1,int team2){
        return gamesPlayed[team1][team2];
    }
    public boolean isNotOverDublesLimit(int team1,int team2,int dubbels){
        int amount = getGamesPlayed(team1,team2);
        if(amount >= dubbels){
            return false;
        }else{
            return true;
        }
    }
    public void setupTeamList(int ploegen){
        for (int i=0 ; i<ploegen;i++) {
            for (char ch = 'A'; ch <= 'Z'; ch++) {
                TeamList.add(ch);
            }
        }
    }
public void addSpelGespeeld(int team,int spel){
        SpelGespeeld[team][spel]++;
}
public void removeSpelGespeeld(int team,int spel){
    SpelGespeeld[team][spel]--;
}
public boolean getSpelGespeeld(int team,int spel){
        if (SpelGespeeld[team][spel]==1){
            return true;
        }else{
            return false;
        }
}



}

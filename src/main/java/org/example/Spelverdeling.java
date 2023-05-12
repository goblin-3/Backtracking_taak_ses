package org.example;

import java.util.ArrayList;
import java.util.Optional;

public class Spelverdeling {

    private int rondesT;
    private int ploegenT;
    private int spellenT;
    private int dubbelsT;



    private ArrayList<Character> TeamList ;
    private int [][] gamesPlayed;

    public Spelverdeling(int ploegenT, int spellenT, int dubbelsT, int rondesT) {
        this.rondesT = rondesT;
        this.ploegenT = ploegenT;
        this.spellenT = spellenT;
        this.dubbelsT = dubbelsT;
    }

    public Optional<String[][]>solve(int ploegen, int spellen, int dubbels, int rondes){
        setupTeamList(ploegen);
        String[][] temporarySolution = new String[rondes][spellen];
        return solve(ploegen,spellen,dubbels,0,temporarySolution);
    }

    private Optional<String[][]>solve(int ploegen, int spellen, int dubbels, int currentronde, String[][] solution){



            gamesPlayed = new int[ploegen][ploegen];






        if (currentronde == rondesT){
            return Optional.of(solution);
        }
        if (rondesT == currentronde+1){
          int  huidigeRonde = currentronde -1;





            return solve(ploegen, spellen, dubbels, huidigeRonde, solution);
        }




        return Optional.empty();


    }

public void addGame(int team1,int team2){
        gamesPlayed[team1][team2]++;
        gamesPlayed[team2][team1]++;
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
}

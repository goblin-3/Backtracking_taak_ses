package org.example;

import java.util.ArrayList;
import java.util.Optional;

public class Spelverdeling {

    private ArrayList<Character> TeamList ;
    private int [][] gamesPlayed;
    /*
    public Spelverdeling(int ploegen, int spellen, int dubbels, int rondes) {
        this.ploegen = ploegen;
        this.spellen = spellen;
        this.dubbels = dubbels;
        this.rondes = rondes;
    }

*/


    public Optional<String[][]>solve(int ploegen, int spellen, int dubbels, int rondes){



        for (int i=0 ; i<ploegen;i++) {
            for (char ch = 'A'; ch <= 'Z'; ch++) {
                TeamList.add(ch);
            }
        }
            gamesPlayed = new int[ploegen][ploegen];

        String[][] temporarySolution = new String[rondes][spellen];

        int currentronde = 0;


        if (currentronde == rondes){
            return Optional.of(temporarySolution);
        }




temporarySolution[0][0] = "A-B";
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




}

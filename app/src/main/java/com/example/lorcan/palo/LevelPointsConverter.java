package com.example.lorcan.palo;

public class LevelPointsConverter {

    public String convertPointsToLevel(int points){

        String level = "";
        if(points == 0){
            level = "1";
        }
        if(points > 0 && points < 10){
            level = "2";
        }
        if(points > 9 && points < 25) {
            level = "3";
        }
        if(points > 24 && points < 50) {
            level = "4";
        }
        if(points > 49 && points < 100) {
            level = "5";
        }
        if(points > 99 && points < 200) {
            level = "6";
        }
        if(points > 199 && points < 500) {
            level = "7";
        }
        if(points > 499 && points < 1000) {
            level = "8";
        }
        if(points > 999 && points < 2000) {
            level = "9";
        }
        if(points > 1999) {
            level = "10";
        }

        return level;

    }
}

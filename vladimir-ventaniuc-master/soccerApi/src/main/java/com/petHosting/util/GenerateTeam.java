package com.petHosting.util;

import com.petHosting.entity.Player;
import com.petHosting.entity.Team;
import com.petHosting.enums.PlayerPosition;

import java.util.*;

public class GenerateTeam {
    private static final Integer GOALKEEPERS_NUMBER = 3;
    private static final Integer DEFENDERS_NUMBER = 6;
    private static final Integer MILDFILDERS_NUMBER = 6;
    private static final Integer ATTAKCKERS_NUMBER = 5;

    public static List<Player> generate(Team team){
        List<Player> players = new LinkedList<>();
        for (int i = 0; i < GOALKEEPERS_NUMBER; i++) {
            players.add(GeneratePlayer.generatePlayerByPosition(team, PlayerPosition.GOALKEEPER));
        }
        for (int i = 0; i < DEFENDERS_NUMBER; i++) {
            players.add(GeneratePlayer.generatePlayerByPosition(team, PlayerPosition.DEFENDER));
        }
        for (int i = 0; i < MILDFILDERS_NUMBER; i++) {
            players.add(GeneratePlayer.generatePlayerByPosition(team, PlayerPosition.MIDFIELDER));
        }
        for (int i = 0; i < ATTAKCKERS_NUMBER; i++) {
            players.add(GeneratePlayer.generatePlayerByPosition(team, PlayerPosition.ATTACKER));
        }
        return players;
    }
}

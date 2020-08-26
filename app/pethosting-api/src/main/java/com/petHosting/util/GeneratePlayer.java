package com.petHosting.util;

import com.github.javafaker.Faker;
import com.petHosting.entity.Player;
import com.petHosting.entity.Team;
import com.petHosting.enums.PlayerPosition;

import java.util.Random;

public class GeneratePlayer {
    private static final Double VALUE = 1000000d;
    private static final Integer PLAYER_MIN_AGE = 18;
    private static final Integer PLAYER_MAX_AGE = 40;


    public static Player generatePlayerByPosition(Team team, PlayerPosition position) {
        Faker faker = new Faker();

        Player player = new Player();
        player.setPosition(position);
        player.setValue(VALUE);
        player.setFirstName(faker.name().firstName());
        player.setLastName(faker.name().lastName());
        player.setCountry(faker.address().country());
        player.setAge(generateAge());
        player.setTeam(team);
        return player;
    }

    private static  int generateAge() {
        Random r = new Random();
        int age = r.nextInt(PLAYER_MAX_AGE - PLAYER_MIN_AGE) + PLAYER_MIN_AGE;
        return age;
    }
}

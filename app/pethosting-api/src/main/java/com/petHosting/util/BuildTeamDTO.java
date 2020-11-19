package com.petHosting.util;

import com.petHosting.dto.PlayerDTO;
import com.petHosting.dto.ResponseUserDTO;
import com.petHosting.dto.TeamDTO;
import com.petHosting.entity.Player;
import com.petHosting.entity.Team;
import com.petHosting.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class BuildTeamDTO {
    public static TeamDTO buildTeamDTO(Team team) {
        User user = team.getUser();
        ResponseUserDTO userDTO = new ResponseUserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setId(user.getId());
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setId(team.getId());
        teamDTO.setCountry(team.getCountry());
        teamDTO.setName(team.getName());
        teamDTO.setValue(team.getValue());
        teamDTO.setPlayers(getPlayerDtos(team.getPlayers()));
        teamDTO.setBalance(team.getBalance());
        teamDTO.setUser(userDTO);
        return teamDTO;
    }
    private static List<PlayerDTO> getPlayerDtos(List<Player> players){
        List<PlayerDTO> playerDTOS = players.stream().map(player -> PlayerDTOBuilder.buildPlayerDTO(player)).collect(Collectors.toList());
        return playerDTOS;
    }
}

package com.petHosting.util;

import com.petHosting.dto.PlayerDTO;
import com.petHosting.entity.Player;

public class PlayerDTOBuilder {
    public static PlayerDTO buildPlayerDTO(Player player){
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(player.getId());
        playerDTO.setAge(player.getAge());
        playerDTO.setCountry(player.getCountry());
        playerDTO.setFirstName(player.getFirstName());
        playerDTO.setLastName(player.getLastName());
        playerDTO.setValue(player.getValue());
        playerDTO.setPosition(player.getPosition());
        playerDTO.setTeamId(player.getTeam().getId());
        playerDTO.setOnTransferList(player.getTransferList()!=null);
        playerDTO.setTeamName(player.getTeam().getName());
        return playerDTO;
    }
}

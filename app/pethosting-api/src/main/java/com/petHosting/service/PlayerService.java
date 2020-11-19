package com.petHosting.service;

import com.petHosting.dto.CreatePlayerDTO;
import com.petHosting.dto.ModifyPlayerDTO;
import com.petHosting.dto.PlayerDTO;
import com.petHosting.entity.Player;
import com.petHosting.entity.Team;
import com.petHosting.entity.TransferList;
import com.petHosting.enums.PlayerPosition;
import com.petHosting.repository.PlayerRepository;
import com.petHosting.util.PlayerDTOBuilder;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    TeamService teamService;
    @Autowired
    TransferListService transferListService;
    public Optional<Player> findByTeamAndId(Team team, Long id) {
        return playerRepository.findByTeamAndId(team, id);
    }

    public List<PlayerDTO> findAll() {
        return playerRepository.findAll().stream()
                .map(player -> PlayerDTOBuilder.buildPlayerDTO(player))
                .collect(Collectors.toList());
    }

    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public Optional<Player> findByPlayerId(Long playerId) {
        return playerRepository.findById(playerId);
    }

    public Optional<Player> findById(Long id) {
        return playerRepository.findById(id);
    }

    public PlayerDTO modifyPlayer(ModifyPlayerDTO modifyPlayerDTO) {
        Player player = this.findById(modifyPlayerDTO.getId()).get();
        if (!modifyPlayerDTO.getFirstName().equals(""))
            player.setFirstName(modifyPlayerDTO.getFirstName());
        if (!modifyPlayerDTO.getLastName().equals(""))
            player.setLastName(modifyPlayerDTO.getLastName());
        if (!modifyPlayerDTO.getCountry().equals(""))
            player.setCountry(modifyPlayerDTO.getCountry());
        if (modifyPlayerDTO.getAge() != null && modifyPlayerDTO.getAge() >= 18)
            player.setAge(modifyPlayerDTO.getAge());
        if (EnumUtils.isValidEnum(PlayerPosition.class, modifyPlayerDTO.getPlayerPosition().toUpperCase()))
            player.setPosition(PlayerPosition.valueOf(modifyPlayerDTO.getPlayerPosition().toUpperCase()));
        if (modifyPlayerDTO.getValue() != null && modifyPlayerDTO.getValue() > 0)
            player.setValue(modifyPlayerDTO.getValue());
        this.save(player);
        return PlayerDTOBuilder.buildPlayerDTO(player);
    }

    public PlayerDTO delete(Player player) {
        Optional<TransferList> transferList = transferListService.findByTeamAndPlayer(player.getTeam(),player);
        Team team = player.getTeam();
        if(transferList.isPresent())
            this.transferListService.removePlayerFromTransferList(player,player.getTeam());
        this.playerRepository.delete(player);

        this.teamService.updateValue(team.getId(), team.getValue() - player.getValue());
        return PlayerDTOBuilder.buildPlayerDTO(player);
    }

    public PlayerDTO add(Team team, CreatePlayerDTO createPlayerDTO){
        Player player = new Player();

        player.setPosition(createPlayerDTO.getPosition());
        player.setValue(createPlayerDTO.getValue());
        player.setFirstName(createPlayerDTO.getFirstName());
        player.setLastName(createPlayerDTO.getLastName());
        player.setCountry(createPlayerDTO.getCountry());
        player.setAge(createPlayerDTO.getAge());
        player.setTeam(team);

        List<Player> players = team.getPlayers();
        players.add(player);
        team.setPlayers(players);
        team.setValue(team.getValue() + player.getValue());
        teamService.save(team);

        return PlayerDTOBuilder.buildPlayerDTO(player);
    }
}

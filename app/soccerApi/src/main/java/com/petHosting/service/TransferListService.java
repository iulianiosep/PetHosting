package com.petHosting.service;

import com.petHosting.dto.PlayerDTO;
import com.petHosting.dto.SearchCriteriaDTO;
import com.petHosting.entity.Player;
import com.petHosting.entity.Team;
import com.petHosting.entity.TransferList;
import com.petHosting.repository.TransferListRepository;
import com.petHosting.util.PlayerDTOBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransferListService {
    @Autowired
    TransferListRepository transferListRepository;
    @Autowired
    PlayerService playerService;
    @Autowired
    TeamService teamService;

    public TransferList addPlayerToTransferList(Player player, Team team, Double marketValue) {

        TransferList transferList = new TransferList();
        transferList.setPlayer(player);
        transferList.setPrice(marketValue);
        transferList.setTeam(team);
        TransferList createdTransferList = transferListRepository.save(transferList);
        player.setTransferList(createdTransferList);
        playerService.save(player);
        List<TransferList> transferLists = team.getTransferList();
        transferLists.add(createdTransferList);
        teamService.save(team);

        return transferList;
    }

    public TransferList removePlayerFromTransferList(Player player, Team team) {

        TransferList transferList = player.getTransferList();
        player.setTransferList(null);
        playerService.save(player);
        team.setTransferList(team.getTransferList().stream().filter(transferListElement -> {
            return transferListElement.getId() != transferList.getId();
        }).collect(Collectors.toList()));

        teamService.save(team);
        transferListRepository.delete(transferList);

        return transferList;

    }

    public Optional<List<TransferList>> findAllByTeam(Team team) {
        return transferListRepository.findByTeam(team);
    }

    public Optional<TransferList> findByTeamAndPlayer(Team team, Player player) {
        return transferListRepository.findByTeamAndPlayer(team, player);
    }

    public List<PlayerDTO> getPlayersFromTransferList(Team team) {
        Optional<List<TransferList>> transferLists = this.findAllByTeam(team);
        if (!transferLists.isPresent())
            return new LinkedList<>();
        List<PlayerDTO> players = transferLists.get().stream().map(transferList -> {
            Player player = transferList.getPlayer();
            PlayerDTO playerDTO = PlayerDTOBuilder.buildPlayerDTO(player);
            playerDTO.setPrice(transferList.getPrice());
            return playerDTO;
        }).collect(Collectors.toList());
        return players;
    }

    public List<PlayerDTO> getPlayersFromTransferList(Team team, SearchCriteriaDTO searchCriteriaDTO) {
        Optional<List<TransferList>> transferLists = transferListRepository.findByTeamNotIn(team);
        List<PlayerDTO> playerDTOS = new LinkedList<>();
        if (!transferLists.isPresent())
            return playerDTOS;
        playerDTOS = transferLists.get().stream()
                .filter(transferList -> this.checkPlayer(transferList.getPlayer(), searchCriteriaDTO, transferList.getPrice(), transferList.getTeam().getName()))
                .map(transferList -> {
                    PlayerDTO playerDTO = PlayerDTOBuilder.buildPlayerDTO(transferList.getPlayer());
                    playerDTO.setPrice(transferList.getPrice());
                    return playerDTO;
                }).collect(Collectors.toList());
        return playerDTOS;
    }

    private boolean checkPlayer(Player p, SearchCriteriaDTO sc, Double value, String teamName) {
        if (sc.getCountry().length() > 0) {
            if (!sc.getCountry().equalsIgnoreCase(p.getCountry()))
                return false;
        }
        if (sc.getFirstName().length() > 0) {
            if (!sc.getFirstName().equalsIgnoreCase(p.getFirstName()))
                return false;
        }
        if (sc.getLastName().length() > 0) {
            if (!sc.getLastName().equalsIgnoreCase(p.getLastName()))
                return false;
        }
        if (sc.getMinValue() != null && sc.getMaxValue() != null) {
            if (sc.getMinValue() > value || sc.getMaxValue() < value)
                return false;
        }
        if (sc.getTeamName().length() > 0) {
            if (!sc.getTeamName().equalsIgnoreCase(teamName))
                return false;
        }
        return true;
    }

    public Optional<TransferList> findByPlayer(Player player){
        return transferListRepository.findByPlayer(player);
    }

    public Player buyPlayer(Team team,Player player, TransferList transferList){
        Team oldTeam = player.getTeam();
        this.removePlayerFromTransferList(player, oldTeam);
        player.setValue(this.generateNewValue(player.getValue()));
        team.setBalance(team.getBalance() - transferList.getPrice());
        oldTeam.setBalance(oldTeam.getBalance() + transferList.getPrice());
        player.setTeam(team);
        List<Player> playerLists = team.getPlayers();
        playerLists.add(player);
        team.setPlayers(playerLists);
        List<Player> oldTeamPlayerLists = oldTeam.getPlayers();
        oldTeamPlayerLists.remove(player);
        oldTeam.setPlayers(oldTeamPlayerLists);

        teamService.save(team);
        teamService.save(oldTeam);
        playerService.save(player);

        return player;
    }

    private Double generateNewValue(Double oldValue){
        Random random = new Random();
        int low = 10;
        int high = 100;
        int ratio = random.nextInt(high-low) + low;
        return oldValue + oldValue * (ratio / 100.0);
    }
    public void delete(TransferList transferList){
        this.transferListRepository.delete(transferList);
    }
}

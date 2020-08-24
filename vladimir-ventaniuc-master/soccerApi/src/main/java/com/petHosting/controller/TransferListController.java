package com.petHosting.controller;

import com.petHosting.dto.PlayerDTO;
import com.petHosting.dto.SearchCriteriaDTO;
import com.petHosting.entity.Notification;
import com.petHosting.entity.Player;
import com.petHosting.entity.Team;
import com.petHosting.entity.TransferList;
import com.petHosting.service.NotificationService;
import com.petHosting.service.PlayerService;
import com.petHosting.service.TeamService;
import com.petHosting.service.TransferListService;
import com.petHosting.util.PlayerDTOBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin()
@RestController
@RequestMapping({"/transfers"})
public class TransferListController {
    @Autowired
    TransferListService transferListService;
    @Autowired
    TeamService teamService;
    @Autowired
    PlayerService playerService;
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    NotificationService notificationService;

    private static final String TEAM_NOT_FOUND = "Team not found";
    private static final String PLAYER_NOT_FOUND = "Player not found";
    private static final String TRANSFER_LIST_NOT_FOUND = "Player is not on a transfer list";
    private static final String PLAYER_IS_PART_OF_YOUR_TEAM = "Player is part of your team";
    private static final String NOT_ENOUGH_MONEY = "Not enough money to buy player";


    @GetMapping(value = "/search/firstName={firstName}/lastName={lastName}/country={country}/teamName={teamName}/minValue={minValue}/maxValue={maxValue}", produces = "application/json")
    public ResponseEntity<?> getTeam(@PathVariable("firstName") String firstName,
                                     @PathVariable("lastName") String lastName,
                                     @PathVariable("country") String country,
                                     @PathVariable("teamName") String teamName,
                                     @PathVariable("minValue") Long minValue,
                                     @PathVariable("maxValue") Long maxValue) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Team> team = teamService.findByUserEmail(email);
        SearchCriteriaDTO searchCriteriaDTO = new SearchCriteriaDTO();
        searchCriteriaDTO.setFirstName(firstName);
        searchCriteriaDTO.setLastName(lastName);
        searchCriteriaDTO.setCountry(country);
        searchCriteriaDTO.setMinValue(minValue);
        searchCriteriaDTO.setMaxValue(maxValue);
        searchCriteriaDTO.setTeamName(teamName);
        List<PlayerDTO> players = transferListService.getPlayersFromTransferList(team.get(), searchCriteriaDTO);
        return ResponseEntity.ok(players);
    }

    @PostMapping(value = "/buy/{playerId}", produces = "application/json")
    public ResponseEntity<?> buyPlayer(@PathVariable("playerId") Long playerId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Team> team = teamService.findByUserEmail(email);
        if (!team.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TEAM_NOT_FOUND);

        Optional<Player> player = playerService.findByPlayerId(playerId);
        Long userIdToSendNotification = player.get().getTeam().getUser().getId();
        if (!player.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PLAYER_NOT_FOUND);

        Optional<TransferList> transferList = transferListService.findByPlayer(player.get());
        if (!transferList.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TRANSFER_LIST_NOT_FOUND);

        if (team.get().getId() == transferList.get().getTeam().getId())
            return ResponseEntity.status(HttpStatus.CONFLICT).body(PLAYER_IS_PART_OF_YOUR_TEAM);

        if (team.get().getBalance() < transferList.get().getPrice())
            return ResponseEntity.status(HttpStatus.CONFLICT).body(NOT_ENOUGH_MONEY);

        Player modifiedPlayer = transferListService.buyPlayer(team.get(), player.get(), transferList.get());

        StringBuilder message = new StringBuilder();
        message.append("Player ").append(player.get().getFirstName()).append(" ").append(player.get().getLastName());
        message.append(" ").append("was bought by team ").append(team.get().getName());

        Notification notification = notificationService.add(userIdToSendNotification, message.toString());
        template.convertAndSend("/topic/notification/" + userIdToSendNotification, notification);

        return ResponseEntity.ok(PlayerDTOBuilder.buildPlayerDTO(player.get()));
    }

}

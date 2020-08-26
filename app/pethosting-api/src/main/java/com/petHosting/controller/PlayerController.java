package com.petHosting.controller;

import com.petHosting.dto.TransferListDTO;
import com.petHosting.entity.Player;
import com.petHosting.entity.Team;
import com.petHosting.entity.TransferList;
import com.petHosting.service.PlayerService;
import com.petHosting.service.TeamService;
import com.petHosting.service.TransferListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin()
@RestController
@RequestMapping({"/player"})
public class PlayerController {
    @Autowired
    TeamService teamService;
    @Autowired
    PlayerService playerService;
    @Autowired
    TransferListService transferListService;

    private static final String TEAM_NOT_FOUND = "Team not found";
    private static final String PLAYER_NOT_FOUND = "Player not found";
    private static final String PLAYER_ALREADY_ON_TRANSFER_LIST = "Player is already on transfer list";
    private static final String PLAYER_ADDED_TO_TRANSFER_LIST = "Player successfully added to transfer list";
    private static final String PLAYER_IS_NOT_ON_TRANSFER_LIST = "Player is not on a transfer list";

    @PostMapping(value = "/addToTransferList")
    public ResponseEntity<?> addToTransferList(@RequestBody TransferListDTO transferListDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Team> team = teamService.findByUserEmail(email);
        if (!team.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TEAM_NOT_FOUND);
        }
        Optional<Player> player = playerService.findByTeamAndId(team.get(), transferListDTO.getPlayerId());
        if (!player.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PLAYER_NOT_FOUND);
        }
        if (player.get().getTransferList() != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(PLAYER_ALREADY_ON_TRANSFER_LIST);
        transferListService.addPlayerToTransferList(player.get(), team.get(), transferListDTO.getPlayerValue());

        return ResponseEntity.ok(transferListDTO);
    }

    @DeleteMapping(value = "/removeFromTransferList/{playerId}")
    public ResponseEntity<?> removeFromTransferList(@PathVariable Long playerId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Team> team = teamService.findByUserEmail(email);
        if (!team.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TEAM_NOT_FOUND);
        }
        Optional<Player> player = playerService.findByTeamAndId(team.get(), playerId);
        if (!player.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PLAYER_NOT_FOUND);
        }
        if (player.get().getTransferList() == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(PLAYER_IS_NOT_ON_TRANSFER_LIST);

        TransferList transferList = transferListService.removePlayerFromTransferList(player.get(), team.get());
        return ResponseEntity.ok(transferList.getId());
    }


}

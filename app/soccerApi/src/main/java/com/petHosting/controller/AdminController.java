package com.petHosting.controller;

import com.petHosting.dto.*;
import com.petHosting.entity.Player;
import com.petHosting.entity.Team;
import com.petHosting.entity.User;
import com.petHosting.service.PlayerService;
import com.petHosting.service.TeamService;
import com.petHosting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin()
@RestController
@RequestMapping({"/admin"})
public class AdminController {

    @Autowired
    TeamService teamService;
    @Autowired
    PlayerService playerService;
    @Autowired
    UserService userService;

    private static final String TEAM_NOT_FOUND = "Team not found";
    private static final String PLAYER_NOT_FOUND = "Player not found";
    private static final String USER_NOT_FOUND = "User not found";

    @PutMapping(value = "/modifyTeam")
    public ResponseEntity<?> modifyTeam(@RequestBody ModifyTeamDTO modifyTeamDTO) {
        Optional<Team> teamOptional = teamService.findById(modifyTeamDTO.getId());
        if (!teamOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TEAM_NOT_FOUND);
        return ResponseEntity.ok(teamService.modifyTeam(modifyTeamDTO));
    }

    @PutMapping(value = "/modifyPlayer")
    public ResponseEntity<?> modifyPlayer(@RequestBody ModifyPlayerDTO modifyPlayerDTO) {
        Optional<Player> playerOptional = this.playerService.findById(modifyPlayerDTO.getId());
        if (!playerOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PLAYER_NOT_FOUND);

        return ResponseEntity.ok(playerService.modifyPlayer(modifyPlayerDTO));
    }

    @GetMapping(value = "/getPlayers")
    public ResponseEntity<?> getPlayers() {
        return ResponseEntity.ok(this.playerService.findAll());
    }

    @DeleteMapping(value = "/deletePlayer/{playerId}")
    public ResponseEntity<?> deleteTeam(@PathVariable Long playerId) {
        Optional<Player> playerOptional = playerService.findById(playerId);
        if (!playerOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PLAYER_NOT_FOUND);
        }
        PlayerDTO playerDTO = playerService.delete(playerOptional.get());
        return ResponseEntity.ok(playerDTO);
    }

    @PostMapping(value = "/createPlayer")
    public ResponseEntity<?> createPlayer(@RequestBody CreatePlayerDTO createPlayerDTO) {
        Optional<Team> teamOptional = teamService.findById(createPlayerDTO.getTeamId());
        if (!teamOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TEAM_NOT_FOUND);
        PlayerDTO playerDTO = playerService.add(teamOptional.get(), createPlayerDTO);
        return ResponseEntity.ok(playerDTO);
    }

    @GetMapping(value = "/getUsers")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(this.userService.findAll());
    }
    @PutMapping(value = "/modifyUser")
    public ResponseEntity<?> modifyUser(@RequestBody ModifyUserDTO modifyUserDTO) {
        Optional<User> user = userService.findById(modifyUserDTO.getId());
        if(!user.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND);
        ResponseUserDTO responseUserDTO = userService.modifyUser(modifyUserDTO);
        return ResponseEntity.ok(responseUserDTO);
    }
    @DeleteMapping(value = "/deleteUser/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND);
        }
        ResponseUserDTO playerDTO = userService.delete(userOptional.get());
        return ResponseEntity.ok(playerDTO);
    }
}

package com.petHosting.controller;

import com.petHosting.dto.ModifyTeamDTO;
import com.petHosting.dto.TeamDTO;
import com.petHosting.entity.Team;
import com.petHosting.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin()
@RestController
@RequestMapping({"/leagueManager"})
public class LeagueManagerController {
    @Autowired
    TeamService teamService;

    private static final String TEAM_NOT_FOUND = "Team not found";

    @GetMapping(value = "/getTeams", produces = "application/json")
    public ResponseEntity<?> getTeams() {
        return ResponseEntity.ok(teamService.findAll());
    }

    @PutMapping(value = "/modifyTeam")
    public ResponseEntity<?> modifyTeam(@RequestBody ModifyTeamDTO modifyTeamDTO) {
        Optional<Team> teamOptional = teamService.findById(modifyTeamDTO.getId());
        if (!teamOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TEAM_NOT_FOUND);
        return ResponseEntity.ok(teamService.modifyTeam(modifyTeamDTO));
    }

    @DeleteMapping(value = "/deleteTeam/{teamId}")
    public ResponseEntity<?> deleteTeam(@PathVariable Long teamId) {
        Optional<Team> teamOptional = teamService.findById(teamId);
        if (!teamOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TEAM_NOT_FOUND);
        TeamDTO teamDTO = teamService.delete(teamOptional.get());
        return ResponseEntity.ok(teamDTO);
    }


}

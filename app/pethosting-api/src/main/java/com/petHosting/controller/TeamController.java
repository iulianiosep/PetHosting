package com.petHosting.controller;

import com.petHosting.dto.CreateTeamDTO;
import com.petHosting.dto.HeaderDTO;
import com.petHosting.dto.TeamDTO;
import com.petHosting.entity.Notification;
import com.petHosting.entity.Team;
import com.petHosting.service.NotificationService;
import com.petHosting.service.TeamService;
import com.petHosting.service.TransferListService;
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
@RequestMapping({"/team"})
public class TeamController {
    @Autowired
    TeamService teamService;
    @Autowired
    TransferListService transferListService;
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    NotificationService notificationService;

    private static final String TEAM_NOT_FOUND = "Team not found";
    private static final String TEAM_ALREADY_CREATED = "Team was already created";
    private static final String TEAM_NAME_AND_COUNTRY_ALREADY_USED = "Team name and country already used";
    private static final String NOTIFICATION_NOT_FOUND = "Notification not found";

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getTeam() {
        Optional<TeamDTO> team = teamService.findTeamDTOByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return team.isPresent() ? ResponseEntity.ok(team.get()) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(TEAM_NOT_FOUND);
    }

    @GetMapping(value = "/getNotifications")
    public ResponseEntity<?> getNotifications() {
        Optional<TeamDTO> team = teamService.findTeamDTOByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Long userId = team.get().getUser().getId();
        List<Notification> notifications = notificationService.findAllByUserId(userId);
        notificationService.deleteAllByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    @PostMapping(value = "/createTeam")
    public ResponseEntity<?> createTeam(@RequestBody CreateTeamDTO createTeamDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<TeamDTO> team = teamService.findTeamDTOByUserEmail(email);
        if (team.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(TEAM_ALREADY_CREATED);
        }
        if (teamService.checkIfTeamNameAndCountryIsTaken(createTeamDTO)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(TEAM_NAME_AND_COUNTRY_ALREADY_USED);
        }
        TeamDTO generatedTeam = teamService.add(createTeamDTO, email);
        return ResponseEntity.ok(generatedTeam);
    }

    @GetMapping(value = "/getBalance", produces = "application/json")
    public ResponseEntity<?> getBalance() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Team> team = teamService.findByUserEmail(email);
        Long balance = teamService.getBalance(email);
        String teamName = team.isPresent() ? team.get().getName() : "";
        HeaderDTO headerDTO = new HeaderDTO();
        headerDTO.setBalance(balance);
        headerDTO.setTeamName(teamName);

        return ResponseEntity.ok(headerDTO);
    }

    @GetMapping(value = "/getTransferList", produces = "application/json")
    public ResponseEntity<?> getTransferList() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Team> team = teamService.findByUserEmail(email);
        if (!team.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TEAM_NOT_FOUND);
        }
        return ResponseEntity.ok(transferListService.getPlayersFromTransferList(team.get()));
    }

    @DeleteMapping(value = "/deleteNotification/{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable Long id) {
        Optional<Notification> notification = notificationService.findById(id);
        if (!notification.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOTIFICATION_NOT_FOUND);
        notificationService.delete(notification.get());
        return ResponseEntity.ok(notification);
    }

}

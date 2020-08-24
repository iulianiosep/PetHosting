package com.petHosting.service;

import com.petHosting.dto.*;
import com.petHosting.entity.Player;
import com.petHosting.entity.Team;
import com.petHosting.entity.User;
import com.petHosting.repository.TeamRepository;
import com.petHosting.util.BuildTeamDTO;
import com.petHosting.util.GenerateTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {
    @Autowired
    private UserService userService;
    @Autowired
    private TeamRepository teamRepository;

    private static final Double VALUE = 20 * 1000000d;

    public boolean checkIfTeamNameAndCountryIsTaken(CreateTeamDTO teamDTO) {
        Optional<Team> team = teamRepository.findByNameAndCountry(teamDTO.getName(), teamDTO.getCountry());
        return team.isPresent() ? true : false;
    }

    public Optional<TeamDTO> findTeamDTOByUserEmail(String email) {
        User user = userService.findUserByEmail(email);

        Optional<Team> team = teamRepository.findByUserId(user.getId());
        if (team.isPresent()) {
            Team teamToBeRetrieved = team.get();
            TeamDTO teamDTO = buildTeamDTO(teamToBeRetrieved, user);
            return Optional.of(teamDTO);
        } else
            return Optional.empty();
    }

    public TeamDTO add(CreateTeamDTO teamDTO, String email) {
        User user = userService.findUserByEmail(email);
        Team team = new Team();
        team.setCountry(teamDTO.getCountry());
        team.setName(teamDTO.getName());
        team.setValue(VALUE);
        team.setUser(user);
        team.setPlayers(GenerateTeam.generate(team));
        team.setBalance(5000000d);
        teamRepository.save(team);
        return buildTeamDTO(team, user);
    }

    private TeamDTO buildTeamDTO(Team team, User user) {
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
        teamDTO.setBalance(team.getBalance());
        teamDTO.setPlayers(buildPlayerDTOS(team.getPlayers()));
        teamDTO.setUser(userDTO);
        return teamDTO;
    }

    private List<PlayerDTO> buildPlayerDTOS(List<Player> players) {
        List<PlayerDTO> playersDTO = players.stream().map(player -> {
            PlayerDTO pd = new PlayerDTO();
            pd.setId(player.getId());
            pd.setAge(player.getAge());
            pd.setCountry(player.getCountry());
            pd.setFirstName(player.getFirstName());
            pd.setLastName(player.getLastName());
            pd.setValue(player.getValue());
            pd.setPosition(player.getPosition());
            pd.setTeamId(player.getTeam().getId());
            pd.setOnTransferList(player.getTransferList() != null);
            return pd;
        }).collect(Collectors.toList());
        return playersDTO;
    }

    public Long getBalance(String email) {
        User user = userService.findUserByEmail(email);
        Optional<Long> balance = teamRepository.getBalance(user.getId());
        if (balance.isPresent())
            return balance.get();
        return 0l;
    }

    //    public Optional<Player> getPlayerFromTeam(Team team, Long playerId){
//
//    }
    public Optional<Team> findByUserEmail(String email) {
        User user = userService.findUserByEmail(email);
        return teamRepository.findByUserId(user.getId());
    }

    public Team save(Team team) {
        return teamRepository.save(team);
    }

    public List<TeamDTO> findAll() {
        List<Team> teams = this.teamRepository.findAll();
        List<TeamDTO> teamDTOs = teams.stream().map(team -> {
            return this.buildTeamDTO(team, team.getUser());
        }).collect(Collectors.toList());
        return teamDTOs;
    }

    public Optional<Team> findById(Long id) {
        return this.teamRepository.findById(id);
    }

    public TeamDTO modifyTeam(ModifyTeamDTO modifyTeamDTO) {
        Optional<Team> teamOptional = this.findById(modifyTeamDTO.getId());

        Team team = teamOptional.get();
        if (!modifyTeamDTO.getName().equals(""))
            team.setName(modifyTeamDTO.getName());
        if (!modifyTeamDTO.getCountry().equals(""))
            team.setCountry(modifyTeamDTO.getCountry());
        if (modifyTeamDTO.getBalance() != null && modifyTeamDTO.getBalance() >= 0)
            team.setBalance(modifyTeamDTO.getBalance());
        this.save(team);

        return BuildTeamDTO.buildTeamDTO(team);
    }

    public TeamDTO delete(Team team) {
        teamRepository.delete(team);
        return BuildTeamDTO.buildTeamDTO(team);
    }

    public void updateValue(Long teamId, Double value) {
        this.teamRepository.updateValue(value, teamId);
    }

    public Optional<Team> findByUser(User user) {
        return this.teamRepository.findByUserId(user.getId());
    }
}

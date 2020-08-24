package com.petHosting.repository;

import com.petHosting.entity.Player;
import com.petHosting.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByTeamAndId(Team team, Long id);
    Optional<Player> findById(Long id);
}

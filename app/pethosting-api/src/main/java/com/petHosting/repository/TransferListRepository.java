package com.petHosting.repository;

import com.petHosting.entity.Player;
import com.petHosting.entity.Team;
import com.petHosting.entity.TransferList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransferListRepository extends JpaRepository<TransferList, Long> {
//    TransferList save(Player player, )
    Optional<List<TransferList>> findByTeam(Team team);
    Optional<TransferList> findByTeamAndPlayer(Team team, Player player);
    void delete(TransferList transferList);
    Optional<List<TransferList>> findByTeamNotIn(Team team);
    Optional<TransferList> findByPlayer(Player player);
}

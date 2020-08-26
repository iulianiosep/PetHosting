package com.petHosting.repository;

import com.petHosting.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, String> {
        Optional<Team> findById(Long id);
        Optional<Team> findByUserId(Long userId);
        Team save(Team team);
        Optional<Team> findByNameAndCountry(String name, String country);
        @Query(value="select balance from teams t where t .user_id =:userId", nativeQuery=true)
        Optional<Long> getBalance(@Param("userId") Long userId);
        @Modifying
        @Transactional
        @Query(value = "UPDATE teams t set value =:value where t.id = :teamId",
                nativeQuery = true)
        void updateValue(@Param("value") Double value, @Param("teamId") Long teamId);
}

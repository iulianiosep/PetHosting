package com.petHosting.repository;

import com.petHosting.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Transactional
    @Query(value = "Select * from notifications n where n.user_id = :userId",
            nativeQuery = true)
    Collection<Notification> findAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "delete from notifications n where n.user_id = :userId",
            nativeQuery = true)
    void deleteAllByUserId(@Param("userId") Long userId);
}

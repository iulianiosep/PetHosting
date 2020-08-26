package com.petHosting.entity;

import com.petHosting.enums.PlayerPosition;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@Table(name = "players")
@EntityListeners(AuditingEntityListener.class)
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "country", nullable = false)
    private String country;
    @Column(name = "age", nullable = false)
    private Integer age;
    @Column(name = "value", nullable = false)
    private Double value;
    @Column(name = "position", nullable = false)
    private PlayerPosition position;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @OneToOne
    private TransferList transferList;

}

package com.petHosting.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@Table(name = "transfer_list")
@EntityListeners(AuditingEntityListener.class)
public class TransferList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Team team;

    @OneToOne
    Player player;

    @Column(name = "price")
    private Double price;
}

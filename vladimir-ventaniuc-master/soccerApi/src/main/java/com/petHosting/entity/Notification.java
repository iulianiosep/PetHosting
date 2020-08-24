package com.petHosting.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "message", nullable = false)
    private String message;
}

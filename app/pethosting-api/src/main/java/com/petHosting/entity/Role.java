package com.petHosting.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
@Table(name = "roles")
@EntityListeners(AuditingEntityListener.class)
public class Role {
    @Id
    @GeneratedValue
    private Long id;
//    @NaturalId
    @Column(name = "role", nullable = false)
    private String role;
    @ManyToMany(mappedBy = "roles")
    private List<User> users;
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public Role(String role){
        this.role = role;
    }
    public Role(){};

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

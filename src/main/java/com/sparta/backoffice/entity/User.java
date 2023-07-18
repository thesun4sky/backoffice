package com.sparta.backoffice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String username;


    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column(nullable = false)
    private String password;


    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();

    public User(String username, String nickname, UserRoleEnum role, String password) {
        this.username = username;
        this.nickname = nickname;
        this.role = role;
        this.password = password;
    }

}

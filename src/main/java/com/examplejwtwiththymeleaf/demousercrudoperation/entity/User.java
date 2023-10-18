package com.examplejwtwiththymeleaf.demousercrudoperation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name ="username")
    private String username;
    @Column(name ="email")
    private String email;
    @Column(name ="password")
    private String password;
    @Column(name ="role")
    private String role;
    @Column(name ="gender")
    private String gender;
    @Column(name ="enabled")
    private int enabled;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
    @JoinTable(name = "user_branch",
            joinColumns = @JoinColumn(name ="user_id"),
            inverseJoinColumns = @JoinColumn(name = "branch_id"))
    private Branch branch;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "user_question",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))
    private List<Question> questions ;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "user_survey",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "survey_id")
    )
    private List<Survey> surveys = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "user_option",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id")
    )
    private List<Options> options = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", enabled=" + enabled +
                ", branch='" + branch + '\'' +
                '}';
    }
}
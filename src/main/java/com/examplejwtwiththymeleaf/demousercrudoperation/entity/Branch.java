package com.examplejwtwiththymeleaf.demousercrudoperation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="branches")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name ="branch")
    private String branch;
    @ManyToMany
    @JoinTable(
            name = "survey_branch",
            joinColumns = @JoinColumn(name = "branch_id"),
            inverseJoinColumns = @JoinColumn(name = "survey_id")
    )
    private List<Survey>  surveys;

    @OneToMany(mappedBy = "branch",cascade = {CascadeType.REFRESH,CascadeType.MERGE})
    private List<User> users;

    public Branch(String branch){
        this.branch=branch;
    }
    @Override
    public String toString() {
        return "Branch{" +
                "id=" + id +
                ", branch='" + branch + '\'' +
                '}';
    }
}

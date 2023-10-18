package com.examplejwtwiththymeleaf.demousercrudoperation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "surveys")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "isEnable")
//    private boolean isEnable = false;
//    @Column(name = "isSurveyEnable")
    private boolean isSurveyEnable = false;
    @Column(name = "submission_time", columnDefinition = "TIMESTAMP")
    private Timestamp submissionTime;
    @Transient
    private String branch;

    @ManyToMany
    @JoinTable(
            name = "survey_branch",
            joinColumns = @JoinColumn(name = "survey_id"),
            inverseJoinColumns = @JoinColumn(name = "branch_id")
    )
    private List<Branch> branches;


    @Transient
    private Long formattedTime;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<Question> questions;

    @OneToMany(mappedBy = "surveyDto", cascade = CascadeType.ALL)
    private List<SelectQuestion> questionsDto;

    @ManyToMany
    @JoinTable(
            name = "user_survey",
            joinColumns = @JoinColumn(name = "survey_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users = new ArrayList<>();

    @Override
    public String toString() {
        return "Survey{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", submissionTime=" + submissionTime +
                '}';
    }
}

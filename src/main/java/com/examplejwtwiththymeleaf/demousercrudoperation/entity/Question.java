package com.examplejwtwiththymeleaf.demousercrudoperation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.HashSet;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name ="question")
    private String question;
//    @Column(name ="optionValue")
//    private String optionValue;

    @ManyToMany
    @JoinTable(
            name = "question_option",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id")
    )
    private List<Options> options = new ArrayList<>();

    @ManyToMany(mappedBy = "questions", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<User> users = new ArrayList<>();

    @ManyToOne
    @JoinTable(name = "question_selected_option",
            joinColumns = @JoinColumn(name ="question_id"),
            inverseJoinColumns = @JoinColumn(name ="selected_option_id"))
    private Options selectedOption;

    @ManyToOne
    @JoinTable(name = "survey_question",
    joinColumns =@JoinColumn(name ="question_id"),
    inverseJoinColumns = @JoinColumn(name="survey_id"))
    private Survey survey;

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                '}';
    }
}
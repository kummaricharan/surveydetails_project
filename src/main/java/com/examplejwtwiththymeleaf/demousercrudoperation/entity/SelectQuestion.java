package com.examplejwtwiththymeleaf.demousercrudoperation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name ="QuestionDto")
public class SelectQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name ="questionName")
    private String questionName;

    @ManyToOne
    @JoinTable(name = "questiondto_selected_option",
            joinColumns = @JoinColumn(name ="question_id"),
            inverseJoinColumns = @JoinColumn(name ="selected_option_id"))
    private Options selectedOptionWithDto;

    @ManyToOne
    @JoinTable(name = "survey_questiondto",
            joinColumns =@JoinColumn(name ="question_id"),
            inverseJoinColumns = @JoinColumn(name="survey_id"))
    private Survey surveyDto;
}
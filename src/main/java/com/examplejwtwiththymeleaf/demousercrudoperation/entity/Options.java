package com.examplejwtwiththymeleaf.demousercrudoperation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="options")
public class Options {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name ="option")
    private String option ;
    @ManyToMany
    @JoinTable(
            name = "question_option",
            joinColumns = @JoinColumn(name = "option_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> questions = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "user_option",
            joinColumns = @JoinColumn(name = "option_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User>  users = new ArrayList<>();
    public Options(String option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return "Options{" +
                "id=" + id +
                ", option='" + option + '\'' +
                '}';
    }
}

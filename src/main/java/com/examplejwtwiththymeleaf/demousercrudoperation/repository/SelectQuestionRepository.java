package com.examplejwtwiththymeleaf.demousercrudoperation.repository;

import com.examplejwtwiththymeleaf.demousercrudoperation.entity.SelectQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectQuestionRepository extends JpaRepository<SelectQuestion,Integer> {
}

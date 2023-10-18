package com.examplejwtwiththymeleaf.demousercrudoperation.service;

import com.examplejwtwiththymeleaf.demousercrudoperation.entity.SelectQuestion;
import com.examplejwtwiththymeleaf.demousercrudoperation.repository.SelectQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SelectQuestionService {
    @Autowired
    private SelectQuestionRepository selectQuestionRepository;

    public SelectQuestion save(SelectQuestion questionDto){
        return selectQuestionRepository.save(questionDto);
    }
}

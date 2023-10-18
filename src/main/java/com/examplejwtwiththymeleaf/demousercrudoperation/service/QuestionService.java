package com.examplejwtwiththymeleaf.demousercrudoperation.service;

import com.examplejwtwiththymeleaf.demousercrudoperation.entity.Question;
import com.examplejwtwiththymeleaf.demousercrudoperation.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    public Question save(Question question){
        return questionRepository.save(question);
    }
    public List<Question> findAll(){
        return questionRepository.findAll();
    }
    public Question findById(int id){
        Optional<Question> result = questionRepository.findById(id);
        Question question = null;
        if(result.isPresent()){
            question = result.get();
        }
        else{
            throw new RuntimeException("did not found id - "+id);
        }
        return  question;
    }
    public void deleteById(int id){
        questionRepository.deleteById(id);
    }
    public Question findByQuestion(String question){
        return questionRepository.findByQuestion(question);
    }
}

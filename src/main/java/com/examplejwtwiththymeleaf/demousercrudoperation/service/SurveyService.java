package com.examplejwtwiththymeleaf.demousercrudoperation.service;


import com.examplejwtwiththymeleaf.demousercrudoperation.entity.Survey;
import com.examplejwtwiththymeleaf.demousercrudoperation.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class SurveyService {
    @Autowired
    private SurveyRepository surveyRepository;


    public List<Survey> findAll(){
        return surveyRepository.findAll();
    }
    public Survey findById(int id) {
        Optional<Survey> result = surveyRepository.findById(id);
        Survey survey = null;
        if(result.isPresent()){
            survey = result.get();
        }
        else{
            throw new RuntimeException("did not found id - "+id);
        }
        return survey;
    }
    public Survey save(Survey survey) {
        return surveyRepository.save(survey);
    }
    public void deleteById(int id) {
        surveyRepository.deleteById(id);
    }
}

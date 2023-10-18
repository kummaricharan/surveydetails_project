package com.examplejwtwiththymeleaf.demousercrudoperation.service;

import com.examplejwtwiththymeleaf.demousercrudoperation.entity.Options;
import com.examplejwtwiththymeleaf.demousercrudoperation.repository.OptionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OptionService {
    @Autowired
    private OptionsRepository optionsRepository;
    public List<Options> findAll(){
        return optionsRepository.findAll();
    }
    public Options findById(int id) {
        Optional<Options> result = optionsRepository.findById(id);
        Options options = null;
        if(result.isPresent()){
            options = result.get();
        }
        else{
            throw new RuntimeException("did not found id - "+id);
        }
        return options;
    }
    public Options save(Options options) {
        return optionsRepository.save(options);
    }
    public void deleteById(int id) {
        optionsRepository.deleteById(id);
    }
    public Options findOrCreateOption(String option) {
        Optional<Options> existingOption = optionsRepository.findByOption(option);

        if (existingOption.isPresent()) {
            return existingOption.get();
        } else {
             Options newOption = new Options(option);
            return optionsRepository.save(newOption);
        }
    }
}

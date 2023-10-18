package com.examplejwtwiththymeleaf.demousercrudoperation.repository;

import com.examplejwtwiththymeleaf.demousercrudoperation.entity.Options;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OptionsRepository  extends JpaRepository<Options,Integer> {
    Optional<Options> findByOption(String option);
}

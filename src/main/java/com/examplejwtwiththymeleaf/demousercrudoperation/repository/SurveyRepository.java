package com.examplejwtwiththymeleaf.demousercrudoperation.repository;


import com.examplejwtwiththymeleaf.demousercrudoperation.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SurveyRepository extends JpaRepository<Survey,Integer> {
    @Modifying
    @Query("DELETE FROM Survey s WHERE s.id = :id")
    void deleteById(@Param("id") int id);
}

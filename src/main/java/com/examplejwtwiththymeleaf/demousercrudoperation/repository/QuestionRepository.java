package com.examplejwtwiththymeleaf.demousercrudoperation.repository;

import com.examplejwtwiththymeleaf.demousercrudoperation.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question,Integer> {
    @Query("select q from Question q where lower(q.question)=lower(:question)")
    Question findByQuestion(@Param("question") String question);
}

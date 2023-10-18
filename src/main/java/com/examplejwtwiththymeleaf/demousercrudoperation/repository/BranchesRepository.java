package com.examplejwtwiththymeleaf.demousercrudoperation.repository;

import com.examplejwtwiththymeleaf.demousercrudoperation.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public interface BranchesRepository extends JpaRepository<Branch,Integer> {
    @Query("SELECT b FROM Branch b WHERE lower(branch)=lower(:branchName)")
    Optional<Branch> findByBranch(@RequestParam("branchName") String branchName);
}

package com.examplejwtwiththymeleaf.demousercrudoperation.service;

import com.examplejwtwiththymeleaf.demousercrudoperation.entity.Branch;
import com.examplejwtwiththymeleaf.demousercrudoperation.repository.BranchesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BranchesService {
    @Autowired
    private BranchesRepository branchesRepository;
    public List<Branch> findAll(){
        return branchesRepository.findAll();
    }
    public Branch findById(int id) {
        Optional<Branch> result = branchesRepository.findById(id);
        Branch branch = null;
        if(result.isPresent()){
            branch = result.get();
        }
        else{
            throw new RuntimeException("did not found id - "+id);
        }
        return branch;
    }
    public Branch save(Branch branch) {
        return branchesRepository.save(branch);
    }
    public void deleteById(int id) {
        branchesRepository.deleteById(id);
    }
    public Branch findOrCreateBranch(String branchName) {
        Optional<Branch> existingBranch = branchesRepository.findByBranch(branchName);

        if (existingBranch.isPresent()) {
            return existingBranch.get();
        } else {
            Branch newBranch = new Branch(branchName);
            return branchesRepository.save(newBranch);
        }
    }
    public Branch findByBranch(String branchName){
        Optional<Branch> result = branchesRepository.findByBranch(branchName);
        Branch branch = null;
        if(result.isPresent()){
            branch = result.get();
        }
        else{
            throw new RuntimeException("did not found branch name with - "+branchName);
        }
        return branch;
    }
}

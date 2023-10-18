package com.examplejwtwiththymeleaf.demousercrudoperation.security;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ModelAndView handleCustomException(CustomException ex){
        ModelAndView modelAndView = new ModelAndView("survey/error");
        modelAndView.addObject("errorMessage",ex.getMessage());
        modelAndView.addObject("errorMessage",ex.getMessage());
        return modelAndView;
    }
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex){
        ModelAndView modelAndView = new ModelAndView("survey/error");
        modelAndView.addObject("errorMessage",ex.getMessage());
        return modelAndView;
    }
}


//    @ExceptionHandler(CustomException.class)
//    public ModelAndView handleCustomException(CustomException ex) {
//        ModelAndView modelAndView = new ModelAndView("error");
//        modelAndView.addObject("errorMessage", ex.getMessage());
//        return modelAndView;
//    }
//
//    @ExceptionHandler(Exception.class) // Handle generic exceptions
//    public ModelAndView handleException(Exception ex) {
//        ModelAndView modelAndView = new ModelAndView("error");
//        modelAndView.addObject("errorMessage", "An error occurred.");
//        return modelAndView;
//    }
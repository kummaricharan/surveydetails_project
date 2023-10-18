package com.examplejwtwiththymeleaf.demousercrudoperation.controller;

import com.examplejwtwiththymeleaf.demousercrudoperation.entity.*;
import com.examplejwtwiththymeleaf.demousercrudoperation.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.*;

@Controller
@RequestMapping("/survey")
public class SurveyController {
    @Autowired
    private SurveyService surveyService;
    @Autowired
    private OptionService optionService;
    @Autowired
    private BranchesService branchesService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;
    @Autowired
    private SelectQuestionService selectQuestionService;
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping("/home")
    public String home(Model model){
        try{
            List<Survey> surveys = surveyService.findAll();
//        for(Survey survey:surveys){
//            System.out.println(survey.getTitle()+" "+survey.getQuestionsDto());
//            for(QuestionDto questionDto:survey.getQuestionsDto()){
//                System.out.println(questionDto.getQuestionName()+" "+questionDto.getSelectedOptionWithDto().getOption());
//            }
//        }
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = null;

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                user = userService.findByMail(userDetails.getUsername());
            }
            for (Survey survey : surveys) {
                survey.setFormattedTime(survey.getSubmissionTime().getTime());
            }
            List<User> users = userService.findAll();
            int totalUsers = 0;
            if(!surveys.isEmpty()){
                totalUsers = users.size();
            }
            int totalSurveys = 0;
            if(!surveys.isEmpty()){
                totalSurveys = surveys.size();
            }
            model.addAttribute("totalUsers",totalUsers );
            model.addAttribute("totalSurveys",totalSurveys );
            model.addAttribute("user",user);
            model.addAttribute("surveys",surveys);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "survey/home";
    }

    @GetMapping("/createSurvey")
    public String createSurvey(Model model){
       try{
           Survey survey = new Survey();
           model.addAttribute("survey",survey);
       }
       catch (Exception e){
           e.printStackTrace();
       }
        return "survey/createSurvey";
    }
    @GetMapping("/addBranch")
    public String addBranch(Model model){
        try{
            Branch branch = new Branch();
            model.addAttribute("branch",branch);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "survey/addBranch";
    }
    @PostMapping("/saveBranch")
    public String saveBranch(@RequestParam("branch") String branch,Model model){
        String[] branch_data = branch.split(",");
        for(String branchValue : branch_data) {
            String trim = branchValue.trim();
            Branch branch1 = (Branch) branchesService.findOrCreateBranch(trim);
        }
        model.addAttribute("success","Successfully added Branch "+branch);
        return "redirect:/survey/branches";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute("survey") Survey survey, @RequestParam(name="branch") String branch){
        try{
            Timestamp currentTimestamp = new Timestamp(new Date().getTime());
            String[] branch_data = branch.split(",");
            List<Branch> branches = new ArrayList<>();

            for(String branchValue : branch_data){
                String trim = branchValue.trim();
                Branch branch1 =(Branch) branchesService.findOrCreateBranch(trim);
                branches.add(branch1);
            }

            survey.setSubmissionTime(currentTimestamp);
            survey.setBranches(branches);
            surveyService.save(survey);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:/survey/surveys";
    }
    @GetMapping("/adminSurvey")
    public String adminSurvey(Model model){
        List<Survey> surveys = surveyService.findAll();
        model.addAttribute("surveys",surveys);
        return "survey/adminSurvey";
    }

    @GetMapping("/goToQuestionCreatingPage")
    public String goToQuestionCreatingPage(@RequestParam("surveyId") int surveyId,Model model){
        Survey survey = surveyService.findById(surveyId);
        model.addAttribute("survey",survey);
        return "survey/goToQuestionCreatingPage";
    }
    @GetMapping("/addQuestion")
    public String addQuestion(@RequestParam("surveyId") int surveyId,Model model){
        Survey survey = surveyService.findById(surveyId);
        Question question = new Question();
        model.addAttribute("Question",question);
        model.addAttribute("survey",survey);
        return "survey/questionform";
    }
    @PostMapping("/createQuestion")
    public String createQuestion(@RequestParam("surveyId") int surveyId,
                                 @ModelAttribute("Question") Question questionObject,
                                 @RequestParam("question") String question,
                                 @RequestParam("option") String option,Model model) {
        try{

            Survey survey = surveyService.findById(surveyId);
            Question question1 = questionService.findByQuestion(questionObject.getQuestion());
            if(question1!=null){
               model.addAttribute("success",question1.getQuestion()+" is already exist");
                model.addAttribute("survey",survey);
                return "redirect:/survey/questionpage?surveyId="+surveyId;
            }

            String[] options = option.split(",");
            List<Options> options1 = new ArrayList<>();
            for (String option_data : options) {
                String trim = option_data.trim();
                Options options2 = (Options) optionService.findOrCreateOption(trim);
                options1.add(options2);
            }
            questionObject.setOptions(options1);
            questionObject.setSurvey(survey);

            if (survey.getQuestions().isEmpty()) {
                List<Question> questions = new ArrayList<>();
                questions.add(questionObject);
                survey.setQuestions(questions);
            } else {
                survey.getQuestions().add(questionObject);
            }
            questionObject.setQuestion(question);
            questionService.save(questionObject);
            surveyService.save(survey);
            model.addAttribute("survey",survey);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/survey/questionpage?surveyId="+surveyId;
    }

    @GetMapping("/questionPage")
    public String questionPage(@RequestParam("surveyId") int surveyId,Model model){
       try{
           Survey survey = surveyService.findById(surveyId);
           Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
           User user =null;
           if (principal instanceof UserDetails) {
               UserDetails userDetails = (UserDetails) principal;

               user = userService.findByMail(userDetails.getUsername());
           }
           for(User user1: survey.getUsers()){
               if( user1.getEmail()!=null && user1.getEmail().equals(user.getEmail())){
                   return "survey/thankyou";
               }
           }
           model.addAttribute("survey",survey);
       }
       catch (Exception e){
           e.printStackTrace();
       }
        return "survey/surveyFormWithQuestion";
    }
    @PostMapping("/submitSurvey")
    public String submitSurvey(@RequestParam("surveyId") int surveyId,
                               @RequestParam Map<String, String> allParams) {
        try {
            Survey survey = surveyService.findById(surveyId);
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = null;

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                user = userService.findByMail(userDetails.getUsername());
            }
            for (Map.Entry<String, String> entry : allParams.entrySet()) {
                String paramName = entry.getKey();
                String paramValue = entry.getValue();


                if (paramName.startsWith("selectedOption_")) {
                    int questionId = Integer.parseInt(paramName.substring("selectedOption_".length()));
                    int optionId = Integer.parseInt(paramValue);
                    Question originalQuestion = questionService.findById(questionId);
                    Options selectedOption = optionService.findById(optionId);
                    if (originalQuestion != null && selectedOption != null) {
                        SelectQuestion duplicateQuestion = new SelectQuestion();

                        duplicateQuestion.setQuestionName(originalQuestion.getQuestion());

                        duplicateQuestion.setSelectedOptionWithDto(selectedOption);
                        duplicateQuestion.setSurveyDto(survey);
                        selectQuestionService.save(duplicateQuestion);

                        user.getQuestions().add(originalQuestion);
                        user.getOptions().add(selectedOption);
                        userService.save(user);

                        survey.getQuestionsDto().add(duplicateQuestion);
                        System.out.println(survey.getQuestionsDto()+"hello");
                        surveyService.save(survey);

                    }
                }
            }
            survey.getUsers().add(user);
            surveyService.save(survey);
            System.out.println(survey.getQuestionsDto()+"hi");
        } catch (Exception e) {
            e.printStackTrace();
    }

    return "survey/thankyou";
}

    @PostMapping("/publishSurvey")
    public String publishSurvey(@RequestParam("surveyId") int surveyId){
        Survey survey = surveyService.findById(surveyId);
        survey.setSurveyEnable(true);
        surveyService.save(survey);
        return "redirect:/survey/questionpage?surveyId="+surveyId;
    }
    @PostMapping("/unpublishSurvey")
    public String unpublishSurvey(@RequestParam("surveyId") int surveyId){
        Survey survey = surveyService.findById(surveyId);
        survey.setSurveyEnable(false);
        surveyService.save(survey);
        return "redirect:/survey/questionpage?surveyId="+surveyId;
    }
    @PostMapping("/report")
    public String report(@RequestParam("surveyId") int surveyId, Model model) {
        try{
            Map<String, Map<String, Integer>> report = new HashMap<>();
            Survey survey = surveyService.findById(surveyId);
            for(SelectQuestion question:survey.getQuestionsDto()){
                System.out.println(question.getQuestionName()+" "+question.getSelectedOptionWithDto().getOption());
                Map<String, Integer> optionScore = report.getOrDefault(question.getQuestionName(), new HashMap<>());
                String selectedOption = question.getSelectedOptionWithDto().getOption();
                System.out.println(selectedOption);
                optionScore.put(selectedOption, optionScore.getOrDefault(selectedOption, 0) + 1);
                report.put(question.getQuestionName(), optionScore);
            }
            System.out.println(report);
            model.addAttribute("report",report);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "survey/reportPage";
    }
    @GetMapping("/userdetails")
    public String userDetails(Model model){
        List<User> users = userService.findAll();
        Collections.sort(users, Comparator.comparingInt(User::getId));
        model.addAttribute("users",users);
        return "survey/userdetails";
    }
    @GetMapping("/surveys")
    public String surveys(Model model){
        try{
            List<Survey> surveys = surveyService.findAll();
            int flag=0;
            for(Survey survey:surveys){
                flag=0;
                StringBuilder branches = new StringBuilder("");
                for(Branch branch:survey.getBranches()){
                    if(flag==0){
                        flag=1;
                        branches.append(branch.getBranch());
                    }
                    else {
                        branches.append(","+branch.getBranch());
                    }
                }
                survey.setBranch(branches.toString());
            }
            Collections.sort(surveys,Comparator.comparingInt(Survey::getId));

            model.addAttribute("surveys",surveys);
            model.addAttribute("survey",new Survey());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "survey/surveys";
    }
    @GetMapping("/questionpage")
    public String questionpage(@RequestParam("surveyId") int surveyId,Model model){
        Survey survey = surveyService.findById(surveyId);
        model.addAttribute("survey",survey);
        model.addAttribute("Question",new Question());
        return "survey/questionpage";
    }
    @GetMapping("/branches")
    public String branches(Model model){
        List<Branch> branches = branchesService.findAll();

        Collections.sort(branches,Comparator.comparingInt(Branch::getId));
        model.addAttribute("branch",new Branch());
        model.addAttribute("branches",branches);
        return "survey/branches";
    }
    @GetMapping("/deleteSurvey")
    public String deleteSurvey(@RequestParam("surveyId") int surveyId){
        Survey survey = surveyService.findById(surveyId);
        if(survey!=null){
            for (Question question : survey.getQuestions()) {
                question.setSurvey(null);
                questionService.save(question);
                questionService.deleteById(question.getId());
            }
            surveyService.deleteById(surveyId);
        }
        else{
            throw new RuntimeException("survey not found with - "+surveyId);
        }
        return "redirect:/survey/surveys";
    }
    @GetMapping("/deleteQuestion")
    public String deleteQuestion(@RequestParam("surveyId") int surevyId,@RequestParam("questionId") int questionId,Model model){
        Question question = questionService.findById(questionId);
        Survey survey = surveyService.findById(surevyId);
        if(survey!=null){
            question.setSurvey(null);
            question.setUsers(null);
            questionService.save(question);
            if(survey.getQuestions().contains(question)){

                survey.getQuestions().remove(survey.getQuestions().indexOf(question));
                surveyService.save(survey);
            }
            questionService.deleteById(questionId);
        }

        return "redirect:/survey/questionpage?surveyId="+surevyId;
    }
    @PostMapping("/deleteBranch")
    public String deleteBranch(@RequestParam("branch") String branchName,Model model){
        Branch branch = branchesService.findByBranch(branchName);
        if(branch!=null){
            branchesService.deleteById(branch.getId());
            List<Branch> branches = branchesService.findAll();

            Collections.sort(branches,Comparator.comparingInt(Branch::getId));
            model.addAttribute("branch",new Branch());
            model.addAttribute("success","Successfully Deleted Branch "+branchName);
            model.addAttribute("branches",branches);
        }
        else {
            throw new RuntimeException("branch is not present");
        }
        return "survey/branches";
    }
}




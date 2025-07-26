package com.example.quiz.platform.controller;
import com.example.quiz.platform.model.Attempt;
import com.example.quiz.platform.model.Quiz;
import com.example.quiz.platform.repository.AttemptRepository;
import com.example.quiz.platform.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin(origins = "*")
public class QuizController {

    @Autowired
    private QuizRepository quizRepo;

    @Autowired
    private AttemptRepository attemptRepo;

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizRepo.findAll();
    }

    @GetMapping("/{id}")
    public Quiz getQuizById(@PathVariable String id) {
        return quizRepo.findById(id).orElse(null);
    }


    @PostMapping("/one-by-one")
    public Quiz createQuizOneByOne(@RequestBody Quiz quiz) {
        quiz.setPublished(true);
        return quizRepo.save(quiz);
    }


    @DeleteMapping("/{id}")
    public void deleteQuiz(@PathVariable String id) {
        quizRepo.deleteById(id);
    }

    @PostMapping("/submit")
    public Attempt submitQuiz(@RequestBody Attempt attempt) {
        return attemptRepo.save(attempt);
    }

    @GetMapping("/selected")
    public Quiz getSelectedQuiz() {
        LocalDateTime now = LocalDateTime.now();
        List<Quiz> all = quizRepo.findAll();
        return all.stream()
                .filter(quiz -> quiz.isPublished() && quiz.getExpiryDateTime() != null && quiz.getExpiryDateTime().isAfter(now))
                .findFirst()
                .orElse(null);
    }

}

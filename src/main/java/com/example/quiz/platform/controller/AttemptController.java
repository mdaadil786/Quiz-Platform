package com.example.quiz.platform.controller;
import com.example.quiz.platform.model.Attempt;
import com.example.quiz.platform.model.Question;
import com.example.quiz.platform.model.Quiz;
import com.example.quiz.platform.repository.AttemptRepository;
import com.example.quiz.platform.repository.QuizRepository;
import com.example.quiz.platform.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attempts")
@CrossOrigin(origins = "*")
public class AttemptController {

    @Autowired private AttemptRepository attemptRepo;
    @Autowired private QuizRepository quizRepo;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/submit/{quizId}")
    public String submitQuiz(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String quizId,
            @RequestBody List<Integer> userAnswers) {

        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.getEmailFromToken(token);

        if (!attemptRepo.findByUserEmailAndQuizId(email, quizId).isEmpty()) {
            return "You have already attempted this quiz!";
        }

        Quiz quiz = quizRepo.findById(quizId).orElse(null);
        if (quiz == null) return "Quiz not found!";

        int score = 0;
        List<Question> questions = quiz.getQuestions();
        for (int i = 0; i < questions.size(); i++) {
            if (i < userAnswers.size() && questions.get(i).getCorrectAnswer() == userAnswers.get(i)) {
                score++;
            }
        }

        Attempt attempt = new Attempt();
        attempt.setQuizId(quizId);
        attempt.setUserEmail(email);
        attempt.setAnswers(userAnswers);
        attempt.setScore(score);
        attemptRepo.save(attempt);

        return "Score: " + score + "/" + questions.size();
    }
}

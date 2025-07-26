package com.example.quiz.platform.repository;

import com.example.quiz.platform.model.Attempt;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AttemptRepository extends MongoRepository<Attempt, String> {
    List<Attempt> findByUserEmailAndQuizId(String email, String quizId);
}

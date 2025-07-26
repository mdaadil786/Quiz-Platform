package com.example.quiz.platform.repository;

import com.example.quiz.platform.model.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuizRepository extends MongoRepository<Quiz, String> {
}

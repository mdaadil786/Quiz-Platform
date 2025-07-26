package com.example.quiz.platform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "attempts")
public class Attempt {
    @Id
    private String id;
    private String userEmail;
    private String quizId;
    private int score;
    private List<Integer> answers;
    private long timeTaken;
    private List<Integer> correctIndex;
}

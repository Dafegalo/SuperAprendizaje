package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionBank {
    private final List<Question> questions = new ArrayList<>();
    private final Random random = new Random();

    public void addQuestion(Question question) {
        if (question != null) questions.add(question);
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
    }

    public List<Question> getAllQuestions() {
        return new ArrayList<>(questions);
    }

    // Devuelve y REMUEVE una pregunta aleatoria para evitar repeticiones
    public Question getRandomQuestion() {
        if (questions.isEmpty()) return null;
        int idx = random.nextInt(questions.size());
        return questions.remove(idx);
    }

    public boolean isEmpty() {
        return questions.isEmpty();
    }

    public int size() {
        return questions.size();
    }

    public void clear() {
        questions.clear();
    }
}
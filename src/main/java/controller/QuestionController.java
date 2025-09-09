package controller;
import java.util.List;
import java.util.Random;
import model.Question;
import model.QuestionBank;

public class QuestionController {
    private QuestionBank questionBank;
    private Random random;

    public QuestionController(QuestionBank questionBank) {
        this.questionBank = questionBank;
        this.random = new Random();
    }

    public void loadQuestions(List<Question> questions) {
        for (Question question : questions) {
            questionBank.addQuestion(question);
        }
    }

    public Question getRandomQuestion() {
        List<Question> questions = questionBank.getAllQuestions();
        if (questions.isEmpty()) {
            return null;
        }
        int index = random.nextInt(questions.size());
        return questions.get(index);
    }

    public boolean validateAnswer(Question question, String answer) {
        return question.getCorrectAnswer().equalsIgnoreCase(answer);
    }
}
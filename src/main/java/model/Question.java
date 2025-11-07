package model;

public class Question {
    private String questionText;
    private String[] options;
    private String correctAnswer;
    private Category category;

    public Question(String questionText, String[] options, String correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public Question(String questionText, String[] options, String correctAnswer, Category category) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.category = category;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public Category getCategory() {
        return category;
    }

    public boolean checkAnswer(String answer) {
        return correctAnswer.equals(answer);
    }
}
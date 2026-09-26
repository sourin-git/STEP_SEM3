import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnlineExaminationSystem {
    interface Question {
        String getPrompt();
        boolean isCorrect(String answer);
    }

    static class MultipleChoiceQuestion implements Question {
        private final String prompt;
        private final String correctAnswer;

        MultipleChoiceQuestion(String prompt, String correctAnswer) {
            this.prompt = prompt;
            this.correctAnswer = correctAnswer;
        }

        public String getPrompt() { return prompt; }
        public boolean isCorrect(String answer) { return correctAnswer.equalsIgnoreCase(answer); }
    }

    static class TrueFalseQuestion implements Question {
        private final String prompt;
        private final boolean correctAnswer;

        TrueFalseQuestion(String prompt, boolean correctAnswer) {
            this.prompt = prompt;
            this.correctAnswer = correctAnswer;
        }

        public String getPrompt() { return prompt; }
        public boolean isCorrect(String answer) {
            return Boolean.toString(correctAnswer).equalsIgnoreCase(answer);
        }
    }

    static class Student {
        private final String name;

        Student(String name) { this.name = name; }

        Attempt start(Examination examination) {
            return examination.startAttempt(this);
        }
    }

    static class Examination {
        private final String title;
        private final List<Question> questions;
        private final Map<Student, Attempt> attempts = new HashMap<>();

        Examination(String title, List<Question> questions) {
            this.title = title;
            this.questions = new ArrayList<>(questions);
        }

        Attempt startAttempt(Student student) {
            Attempt existing = attempts.get(student);
            if (existing != null) {
                throw new IllegalStateException("Student already has an attempt for this examination");
            }
            Attempt attempt = new Attempt(student, this);
            attempts.put(student, attempt);
            System.out.println("Examination '" + title + "' started by " + student.name + ".");
            return attempt;
        }
    }

    static class Attempt {
        private final Student student;
        private final Examination examination;
        private final Map<Integer, String> answers = new HashMap<>();
        private boolean submitted;

        Attempt(Student student, Examination examination) {
            this.student = student;
            this.examination = examination;
        }

        void answer(int questionNumber, String answer) {
            if (submitted) {
                throw new IllegalStateException("Submitted answers cannot be changed");
            }
            if (questionNumber < 1 || questionNumber > examination.questions.size()) {
                throw new IllegalArgumentException("Question number is out of range");
            }
            answers.put(questionNumber, answer);
            System.out.println("Question " + questionNumber + " answered with '" + answer + "'.");
        }

        void submit() {
            if (submitted) {
                throw new IllegalStateException("Attempt has already been submitted");
            }
            submitted = true;
            System.out.println("Examination '" + examination.title + "' submitted successfully.");
            int correct = 0;
            for (int index = 0; index < examination.questions.size(); index++) {
                String answer = answers.get(index + 1);
                if (answer != null && examination.questions.get(index).isCorrect(answer)) {
                    correct++;
                }
            }
            System.out.println("Result for '" + examination.title + "' attempt: " + correct + "/"
                    + examination.questions.size() + " correct.");
        }
    }

    public static void main(String[] args) {
        List<Question> questions = List.of(
                new MultipleChoiceQuestion("2 + 2 = ?", "A"),
                new MultipleChoiceQuestion("Capital of France?", "B"));
        Examination examination = new Examination("Math Quiz", questions);
        Student student = new Student("Student");
        Attempt attempt = student.start(examination);
        attempt.answer(1, "A");
        attempt.answer(2, "C");
        attempt.submit();
    }
}
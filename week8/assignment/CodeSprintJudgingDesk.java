import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeSprintJudgingDesk {
    enum HackathonState {
        OPEN, JUDGING, PUBLISHED
    }

    interface ScoringRule {
        double calculate(double idea, double execution, double presentation);
    }

    static class InnovationScoringRule implements ScoringRule {
        @Override
        public double calculate(double idea, double execution, double presentation) {
            return (idea * 0.50) + (execution * 0.30) + (presentation * 0.20);
        }
    }

    static class OpenScoringRule implements ScoringRule {
        @Override
        public double calculate(double idea, double execution, double presentation) {
            return (idea + execution + presentation) / 3.0;
        }
    }

    static class Student {
        private final String name;

        Student(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    static class Score {
        private double idea;
        private double execution;
        private double presentation;
        private double finalScore;

        void setValues(double idea, double execution, double presentation, double finalScore) {
            this.idea = idea;
            this.execution = execution;
            this.presentation = presentation;
            this.finalScore = finalScore;
        }

        public double getFinalScore() {
            return finalScore;
        }
    }

    static class Project {
        private final String name;
        private final Team team;
        private Score score;

        Project(String name, Team team) {
            this.name = name;
            this.team = team;
        }

        public String getName() {
            return name;
        }

        public Team getTeam() {
            return team;
        }

        public Score getScore() {
            return score;
        }

        public void setScore(Score score) {
            this.score = score;
        }
    }

    static class Team {
        private final String name;
        private final List<Student> members;
        private final ScoringRule scoringRule;
        private final Hackathon hackathon;
        private Project project;

        Team(String name, List<Student> members, ScoringRule scoringRule, Hackathon hackathon) {
            this.name = name;
            this.members = new ArrayList<>(members);
            this.scoringRule = scoringRule;
            this.hackathon = hackathon;
        }

        public String getName() {
            return name;
        }

        public List<Student> getMembers() {
            return new ArrayList<>(members);
        }

        public ScoringRule getScoringRule() {
            return scoringRule;
        }

        public boolean register() {
            if (members.size() < 2 || members.size() > 4) {
                return false;
            }
            return hackathon.registerTeam(this);
        }

        public Project submitProject(String projectName) {
            if (project != null) {
                return project;
            }
            project = new Project(projectName, this);
            hackathon.addProject(project);
            return project;
        }

        public boolean canScoreProject() {
            return hackathon.getState() != HackathonState.PUBLISHED;
        }
    }

    static class Judge {
        private final String name;

        Judge(String name) {
            this.name = name;
        }

        public void scoreProject(Project project, double idea, double execution, double presentation) {
            if (project.getTeam().canScoreProject()) {
                double finalScore = project.getTeam().getScoringRule().calculate(idea, execution, presentation);
                Score score = new Score();
                score.setValues(idea, execution, presentation, finalScore);
                project.setScore(score);
                System.out.println("Score recorded for '" + project.getName() + "'.");
            } else {
                System.out.println("Rescore rejected: Results have already been published.");
            }
        }
    }

    static class Hackathon {
        private final String name;
        private final Map<Student, Team> studentToTeam = new HashMap<>();
        private final List<Team> teams = new ArrayList<>();
        private final List<Project> projects = new ArrayList<>();
        private HackathonState state = HackathonState.OPEN;

        Hackathon(String name) {
            this.name = name;
        }

        public HackathonState getState() {
            return state;
        }

        public boolean registerTeam(Team team) {
            if (state != HackathonState.OPEN) {
                return false;
            }
            if (team.getMembers().size() < 2 || team.getMembers().size() > 4) {
                return false;
            }
            for (Student student : team.getMembers()) {
                if (studentToTeam.containsKey(student)) {
                    return false;
                }
            }
            for (Student student : team.getMembers()) {
                studentToTeam.put(student, team);
            }
            teams.add(team);
            return true;
        }

        public void addProject(Project project) {
            projects.add(project);
        }

        public void publishResults() {
            state = HackathonState.PUBLISHED;
            System.out.println("Results published.");
        }

        public void printTeamSummary(Team team) {
            System.out.println("Team " + team.getName() + " registered (" + team.getMembers().size()
                    + " members, " + (team.getScoringRule() instanceof InnovationScoringRule ? "Innovation" : "Open") + " track).");
        }
    }

    public static void main(String[] args) {
        Hackathon hackathon = new Hackathon("Code Sprint");

        Student a = new Student("Asha");
        Student r = new Student("Ravi");
        Student n = new Student("Neha");
        Student k = new Student("Kiran");

        Team byteBusters = new Team("ByteBusters", Arrays.asList(a, r, n), new InnovationScoringRule(), hackathon);
        Team soloCoder = new Team("SoloCoder", Arrays.asList(k), new OpenScoringRule(), hackathon);

        if (byteBusters.register()) {
            hackathon.printTeamSummary(byteBusters);
        }

        if (soloCoder.register()) {
            hackathon.printTeamSummary(soloCoder);
        } else {
            System.out.println("Registration failed: A team must have 2 to 4 members.");
        }

        Project project = byteBusters.submitProject("SmartAttend");
        System.out.println("Project '" + project.getName() + "' submitted by " + byteBusters.getName() + ".");

        Judge judge = new Judge("Judge 1");
        judge.scoreProject(project, 8, 7, 9);

        if (project.getScore() != null) {
            System.out.printf("Final score: %.2f.%n", project.getScore().getFinalScore());
        }

        hackathon.publishResults();

        judge.scoreProject(project, 10, 7, 9);
    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RockPaperScissorsGame {
    private static final String[] MOVES = {"Rock", "Paper", "Scissors"};

    public static String playRound(String playerMove, String computerMove) {
        if (playerMove.equalsIgnoreCase(computerMove)) {
            return "Draw";
        }

        boolean playerWins = playerMove.equalsIgnoreCase("Rock") && computerMove.equalsIgnoreCase("Scissors")
                || playerMove.equalsIgnoreCase("Paper") && computerMove.equalsIgnoreCase("Rock")
                || playerMove.equalsIgnoreCase("Scissors") && computerMove.equalsIgnoreCase("Paper");

        return playerWins ? "Player Wins" : "Computer Wins";
    }

    private static String normalizeMove(String move) {
        for (String validMove : MOVES) {
            if (validMove.equalsIgnoreCase(move)) {
                return validMove;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        List<String[]> rounds = new ArrayList<>();
        int wins = 0;
        int losses = 0;
        int draws = 0;

        System.out.print("Enter number of rounds: ");
        int numberOfRounds = scanner.nextInt();
        scanner.nextLine();

        for (int round = 1; round <= numberOfRounds; round++) {
            String playerMove;
            do {
                System.out.print("Round " + round + " - Enter Rock, Paper, or Scissors: ");
                playerMove = normalizeMove(scanner.nextLine().trim());
                if (playerMove == null) {
                    System.out.println("Invalid move. Please try again.");
                }
            } while (playerMove == null);

            String computerMove = MOVES[random.nextInt(MOVES.length)];
            String result = playRound(playerMove, computerMove);
            rounds.add(new String[]{String.valueOf(round), playerMove, computerMove, result});

            if (result.equals("Player Wins")) {
                wins++;
            } else if (result.equals("Computer Wins")) {
                losses++;
            } else {
                draws++;
            }

            System.out.println("Result: " + result);
        }

        System.out.println("\nFinal Summary");
        System.out.printf("%-8s %-15s %-17s %-15s%n", "Round", "Player Move", "Computer Move", "Result");
        for (String[] round : rounds) {
            System.out.printf("%-8s %-15s %-17s %-15s%n", round[0], round[1], round[2], round[3]);
        }

        double winPercentage = numberOfRounds == 0 ? 0 : wins * 100.0 / numberOfRounds;
        System.out.printf("%nWins: %d | Losses: %d | Draws: %d | Win Percentage: %.1f%%%n",
                wins, losses, draws, winPercentage);
        scanner.close();
    }
}
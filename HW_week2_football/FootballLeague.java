package HW_week2_football;

import java.util.ArrayList;
import java.util.Scanner;

public class FootballLeague {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Game> games = new ArrayList<>();

        int choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Add Game");
            System.out.println("2. Team Performance");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter home team: ");
                    String home = scanner.nextLine();
                    System.out.print("Enter away team: ");
                    String away = scanner.nextLine();
                    System.out.print("Enter home team goals: ");
                    int homeGoals = scanner.nextInt();
                    System.out.print("Enter away team goals: ");
                    int awayGoals = scanner.nextInt();
                    games.add(new Game(home, away, homeGoals, awayGoals));
                    System.out.println("Game added.");
                    break;

                case 2:
                    System.out.print("Enter team name: ");
                    String team = scanner.nextLine();
                    int wins = 0, losses = 0, draws = 0;

                    for (Game g : games) {
                        if (g.getHomeTeam().equalsIgnoreCase(team)) {
                            if (g.getHomeGoals() > g.getAwayGoals()) wins++;
                            else if (g.getHomeGoals() < g.getAwayGoals()) losses++;
                            else draws++;
                        } else if (g.getAwayTeam().equalsIgnoreCase(team)) {
                            if (g.getAwayGoals() > g.getHomeGoals()) wins++;
                            else if (g.getAwayGoals() < g.getHomeGoals()) losses++;
                            else draws++;
                        }
                    }

                    System.out.println("Team: " + team);
                    System.out.println("Wins: " + wins);
                    System.out.println("Losses: " + losses);
                    System.out.println("Draws: " + draws);
                    break;

                case 0:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }
}

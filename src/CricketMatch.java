import java.util.*;

class Player {
    String name;
    int runs;
    int balls;
    int wickets;
    int ballsBowled;

    public Player(String name) {
        this.name = name;
        this.runs = 0;
        this.balls = 0;
        this.wickets = 0;
        this.ballsBowled = 0;
    }
}

class CricketScoreboard {
    private final int overs;
    private final int playersPerTeam;
    private final List<Player> teamA;
    private final List<Player> teamB;
    private int score;
    private int wickets;
    private int currentOver;
    private int currentBall;
    private int strikerIndex;
    private int nonStrikerIndex;
    private int bowlerIndex;
    private final Scanner scanner;

    public CricketScoreboard(int overs, int playersPerTeam, List<Player> teamA, List<Player> teamB) {
        this.overs = overs;
        this.playersPerTeam = playersPerTeam;
        this.teamA = teamA;
        this.teamB = teamB;
        this.score = 0;
        this.wickets = 0;
        this.currentOver = 0;
        this.currentBall = 0;
        this.strikerIndex = 0;
        this.nonStrikerIndex = 1;
        this.bowlerIndex = playersPerTeam - 1; // Last player of Team B as bowler
        this.scanner = new Scanner(System.in);
    }

    public void startMatch() {
        System.out.println("Match starts now!");
        while (currentOver < overs && wickets < playersPerTeam - 1) {
            System.out.println("Over " + (currentOver + 1) + ":");
            for (currentBall = 0; currentBall < 6; currentBall++) {
                if (wickets >= playersPerTeam - 1) break;
                System.out.print("Enter ball type (ball, no ball, wide, by, wicket): ");
                String ballType = scanner.next();

                handleBall(ballType);
                displayScore();
            }
            currentOver++;
            rotateStrike();
        }
        System.out.println("Innings over. Final Score: " + score + "/" + wickets);
        displayPlayerStats();
    }

    private void handleBall(String ballType) {
        Player striker = teamA.get(strikerIndex);
        Player bowler = teamB.get(bowlerIndex);

        switch (ballType) {
            case "ball":
                System.out.print("Runs scored on ball: ");
                int runs = scanner.nextInt();
                score += runs;
                striker.runs += runs;
                striker.balls++;
                bowler.ballsBowled++;
                if (runs % 2 != 0) rotateStrike();
                break;
            case "no ball":
                score += 1;
                System.out.println("Free hit next ball.");
                break;
            case "wide":
                score += 1;
                break;
            case "by":
                System.out.print("Runs scored as byes: ");
                int byRuns = scanner.nextInt();
                score += byRuns;
                bowler.ballsBowled++;
                break;
            case "wicket":
                wickets++;
                bowler.wickets++;
                bowler.ballsBowled++;
                System.out.println(striker.name + " is out!");
                if (wickets < playersPerTeam - 1) strikerIndex = wickets + 1;
                break;
            default:
                System.out.println("Invalid ball type!");
        }
    }

    private void rotateStrike() {
        int temp = strikerIndex;
        strikerIndex = nonStrikerIndex;
        nonStrikerIndex = temp;
    }

    private void displayScore() {
        System.out.println("Score: " + score + "/" + wickets + " in " + currentOver + "." + currentBall + " overs");
        System.out.println("Striker: " + teamA.get(strikerIndex).name + " - " + teamA.get(strikerIndex).runs + "(" + teamA.get(strikerIndex).balls + ")");
        System.out.println("Non-Striker: " + teamA.get(nonStrikerIndex).name + " - " + teamA.get(nonStrikerIndex).runs + "(" + teamA.get(nonStrikerIndex).balls + ")");
        System.out.println("Bowler: " + teamB.get(bowlerIndex).name + " - Wickets: " + teamB.get(bowlerIndex).wickets + ", Balls: " + teamB.get(bowlerIndex).ballsBowled);
    }

    private void displayPlayerStats() {
        System.out.println("Player Statistics:");
        System.out.println("Batting Stats:");
        for (Player player : teamA) {
            System.out.println(player.name + " - Runs: " + player.runs + ", Balls: " + player.balls);
        }
        System.out.println("Bowling Stats:");
        for (Player player : teamB) {
            System.out.println(player.name + " - Wickets: " + player.wickets + ",Balls :"+ player.ballsBowled);
        }
    }


}


public class CricketMatch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of overs: ");
        int overs = scanner.nextInt();

        System.out.print("Enter number of players per team: ");
        int playersPerTeam = scanner.nextInt();

        List<Player> teamA = new ArrayList<>();
        List<Player> teamB = new ArrayList<>();

        System.out.println("Enter names of players for Team A:");
        for (int i = 0; i < playersPerTeam; i++) {
            System.out.print("Player " + (i + 1) + ": ");
            teamA.add(new Player(scanner.next()));
        }

        System.out.println("Enter names of players for Team B:");
        for (int i = 0; i < playersPerTeam; i++) {
            System.out.print("Player " + (i + 1) + ": ");
            teamB.add(new Player(scanner.next()));
        }
        CricketScoreboard match = new CricketScoreboard(overs, playersPerTeam, teamA, teamB);
        match.startMatch();
    }
}


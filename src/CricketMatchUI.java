import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class CricketwithUI extends JFrame {
   String name;
   int runs;
   int balls;
   int wickets;
   int ballsBowled;

   public CricketwithUI(String name) {
       this.name = name;
       this.runs = 0;
       this.balls = 0;
       this.wickets = 0;
       this.ballsBowled = 0;
   }
}

class CricketScoreboardUI {
   private final int overs;
   private final int playersPerTeam;
   private final java.util.List<CricketwithUI> teamA;
   private final java.util.List<CricketwithUI> teamB;
   private int score;
   private int wickets;
   private int currentOver;
   private int currentBall;
   private int strikerIndex;
   private int nonStrikerIndex;
   private int bowlerIndex;
   private final JTextArea scoreDisplay;
   private final JFrame frame;

   public CricketScoreboardUI(int overs, int playersPerTeam, java.util.List<CricketwithUI> teamA, java.util.List<CricketwithUI> teamB) {
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
       this.bowlerIndex = playersPerTeam - 1;

       frame = new JFrame("Cricket Scoreboard");
       frame.setSize(600, 400);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       // Score display area
       scoreDisplay = new JTextArea();
       scoreDisplay.setEditable(false);
       scoreDisplay.setFont(new Font("Monospaced", Font.PLAIN, 14));
       scoreDisplay.setText("Match starts now!\n");

       JScrollPane scrollPane = new JScrollPane(scoreDisplay);

       // Buttons for ball actions
       JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 5, 5));
       addButton(buttonPanel, "Ball", e -> handleBall("ball"));
       addButton(buttonPanel, "No Ball", e -> handleBall("no ball"));
       addButton(buttonPanel, "Wide", e -> handleBall("wide"));
       addButton(buttonPanel, "By", e -> handleBall("by"));
       addButton(buttonPanel, "Wicket", e -> handleBall("wicket"));

       frame.setLayout(new BorderLayout());
       frame.add(scrollPane, BorderLayout.CENTER);
       frame.add(buttonPanel, BorderLayout.SOUTH);

       displayScore();
       frame.setVisible(true);
   }

   private void addButton(JPanel panel, String label, ActionListener actionListener) {
       JButton button = new JButton(label);
       button.addActionListener(actionListener);
       panel.add(button);
   }

   private void handleBall(String ballType) {
       if (currentOver >= overs || wickets >= playersPerTeam - 1) {
           scoreDisplay.append("Innings over. Final Score: " + score + "/" + wickets + "\n");
           displayCricketwithUIStats();
           return;
       }

       CricketwithUI striker = teamA.get(strikerIndex);
       CricketwithUI bowler = teamB.get(bowlerIndex);

       String input; // Declare input here for use in all cases

       switch (ballType) {
           case "ball":
               input = JOptionPane.showInputDialog(frame, "Runs scored on ball:");
               if (input == null || input.isEmpty()) return;
               int runs = Integer.parseInt(input);
               score += runs;
               striker.runs += runs;
               striker.balls++;
               bowler.ballsBowled++;
               if (runs % 2 != 0) rotateStrike();
               break;
           case "no ball":
               score += 1;
               scoreDisplay.append("No ball! Free hit on next delivery.\n");
               break;
           case "wide":
               score += 1;
               scoreDisplay.append("Wide ball!\n");
               break;
           case "by":
               input = JOptionPane.showInputDialog(frame, "Runs scored as byes:");
               if (input == null || input.isEmpty()) return;
               int byRuns = Integer.parseInt(input);
               score += byRuns;
               bowler.ballsBowled++;
               break;
           case "wicket":
               wickets++;
               bowler.wickets++;
               bowler.ballsBowled++;
               scoreDisplay.append(striker.name + " is out!\n");
               if (wickets < playersPerTeam - 1) {
                   strikerIndex = wickets + 1;
                   // If the new strikerIndex equals nonStrikerIndex, increment nonStrikerIndex
                   if (strikerIndex == nonStrikerIndex && nonStrikerIndex < playersPerTeam - 1) {
                       nonStrikerIndex++;
                   }
               }
               break;
       }

       currentBall++;
       if (currentBall == 6) {
           currentOver++;
           currentBall = 0;
           rotateStrike();
       }

       displayScore();
   }

   private void rotateStrike() {
       int temp = strikerIndex;
       strikerIndex = nonStrikerIndex;
       nonStrikerIndex = temp;
   }

   private void displayScore() {
       scoreDisplay.append("Score: " + score + "/" + wickets + " in " + currentOver + "." + currentBall + " overs\n");
       scoreDisplay.append("Striker: " + teamA.get(strikerIndex).name + " - " + teamA.get(strikerIndex).runs + "(" + teamA.get(strikerIndex).balls + ")\n");
       scoreDisplay.append("Non-Striker: " + teamA.get(nonStrikerIndex).name + " - " + teamA.get(nonStrikerIndex).runs + "(" + teamA.get(nonStrikerIndex).balls + ")\n");
       scoreDisplay.append("Bowler: " + teamB.get(bowlerIndex).name + " - Wickets: " + teamB.get(bowlerIndex).wickets + ", Balls: " + teamB.get(bowlerIndex).ballsBowled + "\n");
   }

   private void displayCricketwithUIStats() {
       scoreDisplay.append("CricketwithUI Statistics:\n");
       scoreDisplay.append("Batting Stats:\n");
       for (CricketwithUI player : teamA) {
           scoreDisplay.append(player.name + " - Runs: " + player.runs + ", Balls: " + player.balls + "\n");
       }
       scoreDisplay.append("Bowling Stats:\n");
       for (CricketwithUI player : teamB) {
           scoreDisplay.append(player.name + " - Wickets: " + player.wickets + ", Balls: " + player.ballsBowled + "\n");
       }
   }
}

public class CricketMatchUI {
   public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);

       System.out.print("Enter number of overs: ");
       int overs = scanner.nextInt();

       System.out.print("Enter number of players per team: ");
       int playersPerTeam = scanner.nextInt();

       java.util.List<CricketwithUI> teamA = new java.util.ArrayList<>();
       java.util.List<CricketwithUI> teamB = new java.util.ArrayList<>();

       System.out.println("Enter names of players for Team A:");
       for (int i = 0; i < playersPerTeam; i++) {
           System.out.print("CricketwithUI " + (i + 1) + ": ");
           teamA.add(new CricketwithUI(scanner.next()));
       }

       System.out.println("Enter names of players for Team B:");
       for (int i = 0; i < playersPerTeam; i++) {
           System.out.print("CricketwithUI " + (i + 1) + ": ");
           teamB.add(new CricketwithUI(scanner.next()));
       }

       new CricketScoreboardUI(overs, playersPerTeam, teamA, teamB);
   }
}

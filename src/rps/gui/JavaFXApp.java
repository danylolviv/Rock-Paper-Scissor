package rps.gui;

// Java imports
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import rps.bll.game.*;
import rps.bll.player.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Scanner;

/**
 * JavaFX implementation of the RPS game
 *
 * @author smsj
 */


public class JavaFXApp extends Application {



    @FXML
    private ImageView userScissor;

    @FXML
    private ImageView userPaper;

    @FXML
    private ImageView userRock;

    @FXML
    private Text aiPicked;

    @FXML
    private Pane userPane;

    @FXML
    private Pane aiPane;

    @FXML
    private Pane vsPane;


    public static void launch() {
        Application.launch();

        IPlayer human = new Player("User", PlayerType.Human);
        IPlayer bot = new Player("AI", PlayerType.AI);

        GameManager ge = new GameManager(human, bot);

        while (true) {
            String playerMove = getPlayerMove();

            if (playerMove.equalsIgnoreCase("exit"))
                break;

            ge.playRound(Move.valueOf(playerMove));

            ge.getGameState().getHistoricResults().forEach((result) -> {
                System.out.println(getResultAsString(result));
            });
        }
    }


    public static String getPlayerMove() {
        Scanner keyboard = new Scanner(System.in);
        String input;
        boolean inputOK;

        do {
            inputOK = false;
            System.out.println();
            System.out.print("Choose Your Weapon (Rock/R, Paper/P or Scissor/S) or Exit/E to quit the game: ");
            input = keyboard.next();

            if (input.equalsIgnoreCase("rock") || input.equalsIgnoreCase("r") ||
                    input.equalsIgnoreCase("paper") || input.equalsIgnoreCase("p") ||
                    input.equalsIgnoreCase("scissor") || input.equalsIgnoreCase("s") ||
                    input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("e")) {
                inputOK = true;

                if (input.equalsIgnoreCase("r")) { input = "Rock"; }
                else if (input.equalsIgnoreCase("p")) { input = "Paper"; }
                else if (input.equalsIgnoreCase("s")) { input = "Scissor"; }
                else if (input.equalsIgnoreCase("e")) { input = "Exit"; }
            }
            else { System.out.println("Invalid input. Try again :)"); }
        }
        while (!inputOK);

        return input;
    }

    public static String getResultAsString(Result result) {
        String statusText = result.getType() == ResultType.Win ? "wins over " : "ties ";

        return "Round #" + result.getRoundNumber() + ":" +
                result.getWinnerPlayer().getPlayerName() +
                " (" + result.getWinnerMove() + ") " +
                statusText + result.getLoserPlayer().getPlayerName() +
                " (" + result.getLoserMove() + ")!";
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/rps/gui/view/GameView.fxml"));
        stage.setTitle("Rock Paper Scissors AI Ultimate");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
    public void rockHandle(javafx.event.ActionEvent actionEvent) {
        userPane.setVisible(true);
        userRock.setVisible(true);
        userPaper.setVisible(false);
        userScissor.setVisible(false);
        vsPane.setVisible(true);
    }

    public void paperHandle(javafx.event.ActionEvent actionEvent) {
        userPane.setVisible(true);
        userRock.setVisible(false);
        userPaper.setVisible(true);
        userScissor.setVisible(false);
        vsPane.setVisible(true);
    }

    public void scissorsHandle(javafx.event.ActionEvent actionEvent) {
        userPane.setVisible(true);
        userRock.setVisible(false);
        userPaper.setVisible(false);
        userScissor.setVisible(true);
        vsPane.setVisible(true);
    }
}

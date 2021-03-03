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

    private static GameManager ge;


    public static void launch() {
        IPlayer human = new Player("User", PlayerType.Human);
        IPlayer bot = new Player("AI", PlayerType.AI);

        ge = new GameManager(human, bot);

        Application.launch();
    }

    public static void playerMove(Move move){
        ge.playRound(move);

        //TODO
        //put this shit into a list as history
        ge.getGameState().getHistoricResults().forEach((result) -> {
            System.out.println(getResultAsString(result));
        });
        //TODO
        //use this info to add a bot icon
        if(ge.getLastResult().getWinnerPlayer().getPlayerType() == PlayerType.AI) {
            ge.getLastResult().getWinnerMove(); // AI's choice
        }
        else ge.getLastResult().getLoserMove(); // AI's choice
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
    public void rockHandle() {
        userPane.setVisible(true);
        userRock.setVisible(true);
        userPaper.setVisible(false);
        userScissor.setVisible(false);
        vsPane.setVisible(true);
        playerMove(Move.Rock);
    }

    public void paperHandle() {
        userPane.setVisible(true);
        userRock.setVisible(false);
        userPaper.setVisible(true);
        userScissor.setVisible(false);
        vsPane.setVisible(true);
        playerMove(Move.Paper);
    }

    public void scissorsHandle() {
        userPane.setVisible(true);
        userRock.setVisible(false);
        userPaper.setVisible(false);
        userScissor.setVisible(true);
        vsPane.setVisible(true);
        playerMove(Move.Scissor);
    }


}

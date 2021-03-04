package rps.gui;

// Java imports
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import rps.bll.game.*;
import rps.bll.player.*;
import rps.bll.game.GameManager;

import javax.swing.text.html.CSS;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;
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
    private ImageView aiPick;

    @FXML
    private ImageView yourPick;

    @FXML
    private Pane userPane;

    @FXML
    private Pane aiPane;

    @FXML
    private Pane vsPane;

    @FXML
    private ImageView aiPaper;

    @FXML
    private ImageView aiRock;

    @FXML
    private ImageView aiScissor;

    @FXML
    private Pane aiWon;

    @FXML
    private Pane userWon;

    @FXML
    private Pane tied;

    @FXML
    private Pane welcome;

    @FXML
    private Pane rps;

    @FXML
    private ScrollPane scrollPane;

    private static GameManager ge;

    private final TilePane tilePaneClass = new TilePane();


    public static void launch() {
        IPlayer human = new Player("User", PlayerType.Human);
        IPlayer bot = new Player("AI", PlayerType.AI);

        ge = new GameManager(human, bot);

        Application.launch();
    }

    public void playerMove(Move move){
        ge.playRound(move);
        ge.getLastResult();
        javafx.scene.control.Label lblContent = new javafx.scene.control.Label(getResultAsString(ge.getLastResult()));
        tilePaneClass.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #f5dfbb;");
        scrollPane.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #f5dfbb;");
        VBox vbox = new VBox();
        lblContent.setStyle("-fx-text-fill: #FFFFFF");
        vbox.getChildren().add(lblContent);
        tilePaneClass.getChildren().add(vbox);
        scrollPane.setContent(tilePaneClass);

        Move aiMove;
        if(ge.getLastResult().getWinnerPlayer().getPlayerType() == PlayerType.AI) {
            aiMove = ge.getLastResult().getWinnerMove();
        }
        else aiMove = ge.getLastResult().getLoserMove();
        displayIconAI(aiMove);
    }


    public static String getResultAsString(Result result) {
        String statusText = result.getType() == ResultType.Win ? "wins over " : "ties ";

        return "Round #" + result.getRoundNumber() + ":" + result.getWinnerPlayer().getPlayerName() + " (" + result.getWinnerMove() + ") " + statusText + result.getLoserPlayer().getPlayerName() + " (" + result.getLoserMove() + ")!";

    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/rps/gui/view/GameView.fxml"));
        stage.setTitle("Rock Paper Scissors AI Ultimate");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    public void winnerAnnouncement(){
        if(ge.getLastResult().getWinnerPlayer().getPlayerType() == PlayerType.AI){
            aiWon.setVisible(true);
            userWon.setVisible(false); }

        if(ge.getLastResult().getWinnerPlayer().getPlayerType() == PlayerType.Human){
            aiWon.setVisible(false);
            userWon.setVisible(true); }

        if (ge.getLastResult().getWinnerMove()==ge.getLastResult().getLoserMove()){
            tied.setVisible(true);
            userWon.setVisible(false);
        } else {
            tied.setVisible(false);
        }

    }
    int aiHighscore = 0;
    int userHighscore = 0;
    public void highscore(){

        if (ge.getLastResult().getWinnerPlayer().getPlayerType() == PlayerType.AI) {
            aiHighscore = aiHighscore+1;
        } else if(ge.getLastResult().getWinnerPlayer().getPlayerType() == PlayerType.Human){
            userHighscore = userHighscore+1;
        } else if (ge.getLastResult().getWinnerMove()==ge.getLastResult().getLoserMove()){
            aiHighscore = aiHighscore+0;
            userHighscore = userHighscore+0;
        }

        System.out.println(aiHighscore);
        System.out.println(userHighscore);
    }

    public void rockHandle() {
        userPane.setVisible(true);
        userRock.setVisible(true);
        userPaper.setVisible(false);
        userScissor.setVisible(false);
        vsPane.setVisible(true);
        yourPick.setVisible(true);
        rps.setVisible(false);
        welcome.setVisible(false);
        playerMove(Move.Rock);

    }

    public void paperHandle() {
        userPane.setVisible(true);
        userRock.setVisible(false);
        userPaper.setVisible(true);
        userScissor.setVisible(false);
        vsPane.setVisible(true);
        yourPick.setVisible(true);
        rps.setVisible(false);
        welcome.setVisible(false);
        playerMove(Move.Paper);

    }

    public void scissorsHandle() {
        userPane.setVisible(true);
        userRock.setVisible(false);
        userPaper.setVisible(false);
        userScissor.setVisible(true);
        vsPane.setVisible(true);
        yourPick.setVisible(true);
        rps.setVisible(false);
        welcome.setVisible(false);
        playerMove(Move.Scissor);

    }
    public void aiRockHandle() {
        aiPane.setVisible(true);
        aiRock.setVisible(true);
        aiPaper.setVisible(false);
        aiPick.setVisible(true);
        aiScissor.setVisible(false);
        winnerAnnouncement();
        highscore();

    }

    public void aiPaperHandle() {
        aiPane.setVisible(true);
        aiRock.setVisible(false);
        aiPaper.setVisible(true);
        aiPick.setVisible(true);
        aiScissor.setVisible(false);
        winnerAnnouncement();
        highscore();

    }

    public void aiScissorsHandle() {
        aiPane.setVisible(true);
        aiRock.setVisible(false);
        aiPaper.setVisible(false);
        aiPick.setVisible(true);
        aiScissor.setVisible(true);
        winnerAnnouncement();
        highscore();

    }

    public void displayIconAI(Move move){
        switch (move) {
            case Rock -> aiRockHandle();
            case Paper -> aiPaperHandle();
            case Scissor -> aiScissorsHandle();
        }
    }
}

package rps.bll.player;

//Project imports

import rps.bll.game.IGameState;
import rps.bll.game.Move;
import rps.bll.game.Result;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Example implementation of a player.
 *
 * @author smsj
 */
public class Player implements IPlayer {

    private String name;
    private PlayerType type;

    /**
     * @param name
     */
    public Player(String name, PlayerType type) {
        this.name = name;
        this.type = type;
    }


    @Override
    public String getPlayerName() {
        return name;
    }


    @Override
    public PlayerType getPlayerType() {
        return type;
    }


    /**
     * Decides the next move for the bot...
     * @param state Contains the current game state including historic moves/results
     * @return Next move
     */
    @Override
    public Move doMove(IGameState state) {
        //Historic data to analyze and decide next move...
        ArrayList<Result> results = (ArrayList<Result>) state.getHistoricResults();

        if(results.isEmpty() || results.get(results.size()-1).getLoserMove() == results.get(results.size()-1).getWinnerMove()) return danyloAI();
        else return basicAI(results);
    }

    private Move basicAI(ArrayList<Result> results){
        Result lastResult;
        lastResult = results.get(results.size()-1);

        if(lastResult.getWinnerPlayer().getPlayerType() == PlayerType.AI) {
            return lastResult.getLoserMove();
        }
        else {
            for (Move move:Move.values()) {
                if(move != lastResult.getLoserMove() && move != lastResult.getWinnerMove()) {
                    return move;
                }
            }
        }
        return Move.Rock;
    }

    private Move danyloAI(){
        int randomNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);
        if (randomNum == 1){
            return Move.Rock;
        }else if(randomNum == 2){
            return Move.Paper;
        }else if(randomNum == 3){
            return Move.Scissor;
        }else {
            return Move.Rock;
        }
    }
}

package rps.bll.player;

//Project imports

import rps.bll.MoveLogic;
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
        /**
         * Create instance of the net move logic and pass historic to it
         * Call method on this class which returns the next move for the Ai
         * @return Next move
         */

        MoveLogic moveLogic = new MoveLogic(results);
        return moveLogic.getNextMove();
        //return moveLogic.basicAI(results);
        //return moveLogic.randomAI();
    }

}

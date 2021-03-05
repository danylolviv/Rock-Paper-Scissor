package rps.bll;

import rps.bll.game.Move;
import rps.bll.game.Result;
import rps.bll.player.PlayerType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MoveLogic {

    private ArrayList<Result> recentResult;
    Move nextMove;
    int humanRockMove;
    int humanPaperMove;
    int humanScissorMove;


    public MoveLogic(ArrayList<Result> results) {
        this.recentResult = results;
    }


    /**
     * AI plays random Move sice there is no historic data.
     */
    private void firstMove(){
            int randomNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);

            switch (randomNum){
                case 1: nextMove = Move.Rock;
                    break;
                case 2: nextMove = Move.Paper;
                    break;
                case 3: nextMove = Move.Scissor;
                    break;
                default: nextMove = Move.Rock;
            }
    }

    /**
     * AI moves according to Numberphile theory.
     */

    private void ifAIWins(Result res){
        nextMove =  res.getLoserMove();
    }

    /**
     * AI moves according to Numberphile theory.
     */
    private void ifAILoses(Result res){
        Move loserMove = res.getLoserMove();

        switch (loserMove){
            case Rock -> nextMove = Move.Scissor;
            case Paper -> nextMove = Move.Rock;
            case Scissor -> nextMove = Move.Paper;
        }
    }

    /**
     * AI plays random Move.
     */
    private void tieCase(Result res){
        int randomNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);
        if (randomNum == 1){ nextMove = Move.Rock; }
        else if(randomNum == 2){ nextMove = Move.Paper; }
        else if(randomNum == 3){ nextMove = Move.Scissor; }
        else { nextMove = Move.Rock; }
    }

    /**
     * AI based on human
     * behaviour and typical
     * reaction to winning or loosing.
     */
    private void humanOrientedAI(ArrayList<Result> gameHistory){
        if (!gameHistory.isEmpty()){
            Result recentRes = gameHistory.get(gameHistory.size()-1);

            if(recentRes.getRoundNumber() >= 10 ){
                analyticalAI();
            }

            PlayerType winner = recentRes.getWinnerPlayer().getPlayerType();
            Move winnerMove = recentRes.getWinnerMove();
            Move loserMove = recentRes.getLoserMove();

            if(winnerMove == loserMove){
                tieCase(recentRes);
            } else if(winner == PlayerType.AI){
                ifAIWins(recentRes);
            }else {
                ifAILoses(recentRes);
            }
        }else{
            firstMove();
        }
    }

    public Move getNextMove() {
        humanOrientedAI(recentResult);
        return nextMove;
    }

    public void analyticalAI(){
        for (Result r: recentResult) {
            Move thisMove;
            if(r.getLoserPlayer().getPlayerType() == PlayerType.Human){
                thisMove = r.getLoserMove();
                switch (thisMove){
                    case Scissor -> humanScissorMove++;
                    case Paper -> humanPaperMove++;
                    case Rock -> humanRockMove++;
                }
            }else {
                thisMove = r.getWinnerMove();
                switch (thisMove){
                    case Rock -> humanRockMove++;
                    case Paper -> humanPaperMove++;
                    case Scissor -> humanScissorMove++;
                }
            }

        }

        List<Integer> abundance = new ArrayList<>();
        abundance.add(humanPaperMove);
        abundance.add(humanRockMove);
        abundance.add(humanScissorMove);
        Collections.sort(abundance);
        int highestAbundanceMove = abundance.get(2);

        if (highestAbundanceMove == humanPaperMove){
            nextMove = Move.Scissor;
        }else if (highestAbundanceMove == humanRockMove){
            nextMove = Move.Paper;
        }else {
            nextMove = Move.Rock;
        }

        System.out.println();

        System.out.println("Paper Moves made: " + humanPaperMove);
        System.out.println("Rock Moves made: " + humanRockMove);
        System.out.println("Scissor Moves made: " + humanScissorMove);
    }


    /**
     * Generates random value for the next move.
     */
    public Move randomAI(){
        int randomNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);

        switch (randomNum){
            case 1: return Move.Rock;
            case 2: return Move.Paper;
            case 3: return Move.Scissor;
            default: return Move.Rock;
        }
    }


    /**
     * Tomas wrote this.
     */
    public Move basicAI(ArrayList<Result> results){

        if(results.isEmpty()){
            int randomNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);

            switch (randomNum){
                case 1: return  Move.Rock;
                case 2: return Move.Paper;
                case 3: return Move.Scissor;
                default: return Move.Rock;
            }
        }

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

}

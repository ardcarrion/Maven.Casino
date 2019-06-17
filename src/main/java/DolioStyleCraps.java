
import java.util.ArrayList;
import java.util.Scanner;


public class DolioStyleCraps extends DiceGame {
    public DolioStyleCraps(Player players, Integer numberOfDice) {
        super(players, numberOfDice);
    }




    ArrayList<Integer> pointList=new ArrayList<>();
    public DolioStyleCraps(DStyleCrapsPlayer players) {
        super(players, 2);
   }



    public void playGame() {
       super.getDice().toss();
        System.out.println("Player rolled "+dice.tossAndSum());
    }


    public Boolean didWin(DStyleCrapsPlayer player) {
       if(dice.sum()==7||dice.sum()==11||pointList.contains(dice.sum())){
          // House house=new House(2);
         //  player.earnedChips(house.payout(player.betchips));
           return true;
       }
       else if(dice.sum()==2||dice.sum()==12||dice.sum()==3)
           return false;
       else
           playGame();
        return false;
       }


    public void endOfGame() {

        if(didWin((DStyleCrapsPlayer) players[0]))
            System.out.println("Player "+players[0]+" won the game");
        else if(!didWin((DStyleCrapsPlayer) players[0]))
            System.out.println("Player "+players[0]+" lost the game");
        else
            playGame();


        boolean keepRolling=true;
        while(keepRolling) {
            if(dice.sum()==7||dice.sum()==11){
                System.out.println("Player won the game! Congratulations...");
                keepRolling=false;}
            else if(dice.sum()==2||dice.sum()==12||dice.sum()==3){
                System.out.println("Player lost the game,GoodLuck next time...");
                keepRolling=false;}
            else if(pointList.contains(dice.sum())){
                System.out.println("Player won the game rolled "+dice.sum());
                keepRolling=false;
            }
            else{
                pointList.add(dice.sum());
                System.out.println("Keep Playing");
                System.out.println("Press [Enter] to roll the dice");
           //    in.nextLine();
                playGame();}
        }

    }
    }


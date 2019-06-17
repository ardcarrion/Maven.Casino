import java.util.ArrayList;
import java.util.List;

public class Poker extends CardGame {

    List<Card> communityCard;
    Integer pool = 0;
    Integer lastBet = 0;
    Integer sameBetCount = 0;
    List<PokerPlayer> pokerPlayerList;  // to store all player
    Console console;
    House house;

    public Poker(PokerPlayer player, Console console) {
        super(player, 1);
        this.console = console;
        house = new House(5,decks);
        pokerPlayerList = new ArrayList<>();
        pokerPlayerList.add(player);
        for (Integer index = 1; index < 4; index++)
            pokerPlayerList.add(new PokerNPC(new Person(300.0, "Computer", 21)));

        //----- for debugging
        for (PokerPlayer p: pokerPlayerList)
            p.addChips(500);
    }

    public void playGame() {
        house.shuffle();
        while (!onePlayerStanding() && !showDownTime()) {
            initializeBet();
            determineTurnToPlay();
        }
        PokerPlayer winner = showDown();
        awardPool(winner);
        endOfGame();
    }

    private void determineTurnToPlay() {
        if (communityCard.size() == 0)
            preFlop();
        else
            postFlop();
    }

    private void preFlop() {
        update(pokerPlayerList.get(0).smallBlind());
        update(pokerPlayerList.get(1).bigBlind());
        dealCardToAllPlayer();
        startBetting(2);
        flop();
    }

    private void flop() {
        for (int i = 0; i < 3; i++)
            communityCard.add(house.dealCard());
    }

    private void dealCardToAllPlayer() {
        for (PokerPlayer player : pokerPlayerList){
            ArrayList<Card> twoCard = new ArrayList<>();
            twoCard.add(house.dealCard());
            twoCard.add(house.dealCard());
            player.setHand(twoCard);
        }
    }


    private void postFlop() {
        console.println(communityCard.toString());
        startBetting(0);
        communityCard.add(house.dealCard());
    }

    private PokerPlayer showDown() {
        // make an array of everyone's point
        // for each player
        // determine the point they got by add communityCard + player hand
        // send it to evaluate
        // return winner


        return pokerPlayerList.get(0);
    }

    private Boolean showDownTime() {
        return communityCard.size() >= 5;
    }

    public void startBetting(Integer startingPlayerIndex) {
        Integer currentPlayerIndex = startingPlayerIndex;

        // keep betting when everyone hasn't bet same
        // and when there is still 2 or more unfolded player
        while(!onePlayerStanding() && !everyoneBetSame())
        {
            if (!pokerPlayerList.get(currentPlayerIndex).isFolded())
                update(pokerPlayerList.get(currentPlayerIndex).getBetFromAction(console, lastBet));

            currentPlayerIndex++;
            if (currentPlayerIndex >= pokerPlayerList.size())
                currentPlayerIndex = 0;
        }
    }

    private void initializeBet() {
        lastBet = 0;
        sameBetCount = 0;
    }

    private void update(Integer betFromAction) {
        if (betFromAction != null) {
            updatePool(betFromAction);
            updateSameBetCount(betFromAction);
            updateLastBet(betFromAction);
        }
    }

    private void updatePool(Integer betFromAction) {
        pool += betFromAction;
    }


    private void updateSameBetCount(Integer betFromAction) {
        if (betFromAction.equals(lastBet))
            sameBetCount++;
        else
            sameBetCount = 0;
    }

    private void updateLastBet(Integer betFromAction) {
        lastBet = betFromAction;
    }

    private Boolean isNPC(PokerPlayer player) {
        return player instanceof PokerNPC;
    }

    private Integer getNumOfFoldedPlayer()
    {
        Integer numOfFoldedPlayer = 0;
        for (PokerPlayer pokerPlayer : pokerPlayerList)
            if (pokerPlayer.isFolded())
                numOfFoldedPlayer++;

        return numOfFoldedPlayer;
    }

    private Boolean onePlayerStanding() {


        return getNumOfFoldedPlayer() == pokerPlayerList.size()-1;
    }

    private Boolean everyoneBetSame() {
        return sameBetCount == pokerPlayerList.size() - getNumOfFoldedPlayer();
    }

    private void awardPool(PokerPlayer winner) {
        winner.addChips(pool);
        pool = 0;
    }


    public void endOfGame() {
        // ask if keep playing
        // if keep playing
        // reset bet
        // change seat
        // call playGame

        String input = console.getStringInput("Keep Playing? Y/N");
        if (input.equalsIgnoreCase("y"))
            playGame();
        else
            console.println("you have "+ pokerPlayerList.get(0).getChip() + " chips in total.");
    }
}

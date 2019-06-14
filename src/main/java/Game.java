public abstract class Game {
    protected Player[] players;
    protected Turn turn;
    public Game(Player[] players) {
        this.players = players;
    }

    abstract Boolean didWin(Player player);
    abstract void playGame();
    abstract Boolean endOfGame();


}

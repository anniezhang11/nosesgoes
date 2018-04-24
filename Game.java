import java.util.*;

public class Game {
    private Deck deck;
    private Discard discard; 
    private static final int HAND_SIZE = 10;

    private Player human;
    private Player bot;

    public Game(){
        //TODO
        this.deck = new Deck();
        deck.shuffleDeck();
        this.discard = new Discard();
        //Make the game 
        System.out.println(deck.getDeck().size());

        this.human = new Player(deck);
        System.out.println(deck.getDeck().size());
    }
}


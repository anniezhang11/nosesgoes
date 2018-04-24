import java.util.*;

public class Player{
    private ArrayList<Card> hand; 
    private ArrayList<ArrayList<Card>> meld;
    private int deadwoodScore; 
    private ArrayList<Card> deadwood;

    public Player(Deck deck){
        //TODO
        ArrayList<Card> oldDeck = deck.getDeck();
        this.hand = new ArrayList<Card>(oldDeck.subList(0,10));
        oldDeck.removeAll(this.hand);
        deck.setDeck(oldDeck);
    }

    public void knock(){
        //todo
    }

    public void callGin(){
    
    }

    private int calculateDeadwood(ArrayList<Card> deadwood){
        //calculate deadoowd score

        return -1;
    }

    
}
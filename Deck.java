import java.util.*;
import java.util.stream.Collectors;
public class Deck {
    
    private ArrayList<Card> cards;

    public Deck() {
        this.cards = new ArrayList<Card>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public String toString() {
        return cards.stream().map(Object::toString).collect(Collectors.joining(", "));
    }
    
    public Card getTop(){
        return cards.get(0);
    }
    
    public void setDeck(ArrayList<Card> cards){
        this.cards = cards;
    }

    public ArrayList<Card> getDeck(){
        return cards;
    }

    public Card drawTop(){
        //returns top card
        return cards.remove(0);
    }

    public void shuffleDeck(){
        //shuffle deck
        Collections.shuffle(cards);
    }
}
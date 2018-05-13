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
    
    // bubu's code
    // public Card getTop(){
    //     return cards.get(0);
    // }
    
    // public void setDeck(ArrayList<Card> cards){
    //     this.cards = cards;
    // }

    // public ArrayList<Card> getDeck(){
    //     return cards;
    // }

    public Card draw(){
        //returns top card
        return cards.remove(0);
    }

    public void shuffle(){
        //shuffle deck
        Collections.shuffle(cards);
    }

    public int size() {
        return cards.size();
    }
}
import java.util.ArrayList;

public class State {
    private Hand hand;
    private int deckSize;
    private ArrayList<Card> discard;

    public State(Hand hand, int deckSize, ArrayList<Card> discard) {
        this.hand = hand;
        this.deckSize = deckSize;
        this.discard = discard;
    }

    public Hand getHand() {
        return hand;
    }

    public int getDeckSize() {
        return deckSize;
    }

    public ArrayList<Card> getDiscard() {
        return discard;
    }
}
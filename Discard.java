import java.util.ArrayList;

import javax.smartcardio.Card;

public class Discard{
    private ArrayList<Card> pile;

    public Discard(){
        //TODO
        pile = new ArrayList<Card>();
    }

    public Card getTop(){
        //get top card
        return pile.get(0);
    }

    public void setTop(Card card){
        //add to discard
        pile.add(0,card);
        return;
    }

    public Card takeTop(){
        //take from discard
        return pile.remove(0);
    }
}
import java.io.Serializable;
import java.util.*;

import javax.smartcardio.Card;

/**
 * Models a hand of 10 cards. The hand is not sorted. Not threadsafe.
 * The hand is a set: adding the same card twice will not add duplicates
 * of the card.
 * @inv size() > 0
 * @inv size() <= HAND_SIZE
 */
public class Hand implements Serializable
{
	public static final int HAND_SIZE = 10;
	private ArrayList<Card> aCards;
	private ArrayList<Card> aUnmatched;
	private ArrayList<ICardSet> aRuns;
	private ArrayList<ICardSet> aSets;
	
	/**
	 * Creates a new, empty hand.
	 */
	public Hand()
	{
		aCards = new ArrayList<Card>();
		aUnmatched = new ArrayList<Card>();
		aRuns = new ArrayList<ICardSet>();
		aSets = new ArrayList<ICardSet>();
	}
	
	/**
	 * Adds pCard to the list of unmatched cards.
	 * If the card is already in the hand, it is not added.
	 * @param pCard The card to add.
	 * @pre pCard != null
	 */
	public void add( Card pCard )
	{
		aCards.add(pCard);
        aUnmatched.add(pCard);
	}
	
	/**
	 * Remove pCard from the hand and break any matched set
	 * that the card is part of. Does nothing if
	 * pCard is not in the hand.
	 * @param pCard The card to remove.
	 * @pre pCard != null
	 */
	public void remove( Card pCard )
	{
		aCards.remove(pCard);
		aUnmatched.remove(pCard);
		
		// Remove any runs containing the card to remove.
		ArrayList<ICardSet> runsToRemove = new ArrayList<ICardSet>();
		for (ICardSet run : aRuns) 
		{
			if (run.contains(pCard)) 
			{
				runsToRemove.add(run);	
			}
		}
		for (ICardSet run : runsToRemove)
		{
			aRuns.remove(run);
		}
		
		// Remove any sets containing the card to remove.
		ArrayList<ICardSet> setsToRemove = new ArrayList<ICardSet>();
		for (ICardSet group : aSets) 
		{
			if (group.contains(pCard))
			{
				setsToRemove.add(group);
			}
		}
		for (ICardSet group : setsToRemove)
		{
			aSets.remove(group);
        }

	}
	
	/**
	 * @return True if the hand is complete.
	 */
	public boolean isComplete()
	{
		if (this.size() == HAND_SIZE) 
		{
			return true;
		}
		return false; 
	}
	
	/**
	 * Removes all the cards from the hand.
	 */
	public void clear()
	{
		aCards = new ArrayList<Card>();
		aUnmatched = new ArrayList<Card>();
		aRuns = new ArrayList<ICardSet>();
		aSets = new ArrayList<ICardSet>();
    }
    
    public ArrayList<ICardSet> getRuns() {
        return aRuns;
    }

    public ArrayList<ICardSet> getSets() {
        return aSets;
    }

    public ArrayList<Card> getUnmatched() {
    	Collections.sort(aUnmatched);
        return aUnmatched;
    }
	
	/**
	 * @return A copy of the set of matched sets
	 */
	public Set<ICardSet> getMatchedSets()
	{
		HashSet<ICardSet> matched = new HashSet<ICardSet>(aSets);
		matched.addAll(aRuns);
		return matched;
	}
	
	/**
	 * @return A copy of the set of unmatched cards.
	 */
	public Set<Card> getUnmatchedCards()
	{
		Collections.sort(aUnmatched);
		return new HashSet<Card>(aUnmatched);
	}
	
	/**
	 * @return A copy of the set of sets of matched cards.
	 */
	public ArrayList<Card> getMatchedCards() {
        ArrayList<Card> cards = new ArrayList<Card>();
        Set<ICardSet> allSets = getMatchedSets();
        for (ICardSet s : allSets) {
            Iterator<Card> iter = s.iterator();
            while (iter.hasNext()) {
                Card thisCard = iter.next();
                cards.add(thisCard);
            }
        }
        return cards;
    }
	
	/**
	 * @return The number of cards in the hand.
	 */
	public int size()
	{
		return aCards.size(); 
	}
	
	/**
	 * Determines if pCard is already in the hand, either as an
	 * unmatched card or as part of a set.
	 * @param pCard The card to check.
	 * @return true if the card is already in the hand.
	 * @pre pCard != null 
	 */
	public boolean contains( Card pCard )
	{
		return aCards.contains(pCard);
	}
	
	/**
	 * @return The total point value of the unmatched cards in this hand.
	 */
	public int score()
	{
		int score = 0;
		for (Card c : this.aUnmatched)
		{
			int cardPoints = c.getRank().ordinal() + 1;
			if (cardPoints > HAND_SIZE)
			{
				cardPoints = HAND_SIZE;
			}
			score += cardPoints;
		}
		return score; 
	}
	
	
	/**
	 * Calculates the matching of cards into sets and runs that
	 * results in the lowest amount of points for unmatched cards.
	 */
	public void autoMatch()
	{
		HashSet<CardSet> sets = new HashSet<CardSet>();
		
		// TODO: current implementation just makes sets
		
		// all cards are considered unmatched
		aRuns = new ArrayList<ICardSet>();
		aSets = new ArrayList<ICardSet>();
		
		// copy the cards to the unmatched card set
		aUnmatched = new ArrayList<Card>();
		for(Card c : aCards)
		{
			aUnmatched.add(c);
		}
		
        Collections.sort(aCards);
        System.out.println("aCards pre: " + aCards.toString());
        Collections.sort(aUnmatched);
        // System.out.println("aUnMatched pre: " + aUnmatched.toString());
		
		// Make groups by starting at a card
		for(int i = aCards.size() - 1; i >= 1; i--)
		{
			//TODO: if card already in a meld, it cannot be used in another meld
			Card groupCard = aCards.get(i);
//			boolean mybool = aUnmatched.contains(groupCard); //this doesnt work for some unknown reason
			
			HashSet<Card> possibleSet = new HashSet<Card>();
			HashSet<Card> possibleRun = new HashSet<Card>();
			possibleSet.add(groupCard);
			possibleRun.add(groupCard);
            Rank groupRank = groupCard.getRank();
            Suit groupSuit = groupCard.getSuit();
            int minRank = groupRank.toInt() - 1;

			for(int j = i - 1; j >= 0; j--)
			{
				Card currentCard = aCards.get(j);
				Rank currentRank = currentCard.getRank();
                Suit currentSuit = currentCard.getSuit();
                
				// if this rank is the same as groupRank, add it to possible Set
                if (currentRank == groupRank)
                {	
                	possibleSet.add(currentCard);
                    System.out.println("aUnmatched removed: " + aUnmatched.toString());
                }
                
				// if this suit is the same as groupSuit and is consecutive add it to possible Run
                if (currentSuit == groupSuit && currentRank.toInt() == minRank)
                {	
                	minRank--;
                	possibleRun.add(currentCard);
                    System.out.println("aUnmatched removed: " + aUnmatched.toString());
                }
                
			}
			System.out.println("possibleSet: " + possibleSet.toString());
			System.out.println("possibleRun: " + possibleRun.toString());
			
            CardSet possibleCardSet = new CardSet(possibleSet);
            CardSet possibleCardRun = new CardSet(possibleRun);
            
            boolean issetbool = possibleCardSet.isGroup();
            boolean isrunbool = possibleCardRun.isRun();
            
            System.out.println("is set: " + issetbool);
            System.out.println("is run: " + isrunbool);
            
            //TODO: the following should account for group:run 00, 01, 10, and 11
            
            //00 do nothing
            //01 add subset with the same suit
            //10 add subset with the same rank
            //11 check whether the run value or group value is higher
            
            if (!issetbool && !isrunbool) {


            } else if (issetbool && !isrunbool) {
				aSets.add(possibleCardSet);
				for (Card c : possibleCardSet) {
                    System.out.println("adding card to matched: " + c.toString());
                    aUnmatched.remove(c);
                }
            } else if (!issetbool && isrunbool) {
				aRuns.add(possibleCardRun);
				for (Card c : possibleCardRun) {
                    System.out.println("adding card to matched: " + c.toString());
                    aUnmatched.remove(c);
                }
            } else if (issetbool && isrunbool) {
            	aSets.add(possibleCardSet); 	
				for (Card c : possibleCardRun) {
                    System.out.println("adding card to matched: " + c.toString());
                    aUnmatched.remove(c);
                }
				for (Card c : possibleCardSet) {
                    System.out.println("adding card to matched: " + c.toString());
                    aUnmatched.remove(c);
                }
            }
          
            System.out.println("aUnmatched post: " + aUnmatched.toString());
            
        }
  
//		System.out.println("aUnmatched post: " + aUnmatched.toString());
		Collections.sort(aUnmatched);

	}
	
	/**
	 * Gets the cards in the hand.
	 * @return a copy of the cards in the hand
	 */
	public ArrayList<Card> getCards()
	{
		ArrayList<Card> ret = new ArrayList<Card>();
		Collections.sort(aCards);
		for(Card c : aCards)
		{
			ret.add(c);
		}
		return ret;
	}
}
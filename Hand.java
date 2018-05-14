import java.io.Serializable;
import java.util.*;

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
		return new HashSet<Card>(aUnmatched);
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
		for(int i = 0; i < aCards.size() - 1; i++)
		{
			HashSet<Card> possibleSet = new HashSet<Card>();
			HashSet<Card> possibleRun = new HashSet<Card>();
			possibleSet.add(aCards.get(i));
			possibleRun.add(aCards.get(i));
            Rank groupRank = aCards.get(i).getRank();
            Suit groupSuit = aCards.get(i).getSuit();
            int maxRank = groupRank.toInt() + 1;

			for(int j = i + 1; j < aCards.size(); j++)
			{
				Rank currentRank = aCards.get(j).getRank();
                Suit currentSuit = aCards.get(j).getSuit();
                
				// if this rank is the same as groupRank, add it to possible Set
                if (currentRank == groupRank)
                {	
                	possibleSet.add(aCards.get(j));
                    aUnmatched.remove(aCards.get(j));
                    // System.out.println("aUnmatched removed: " + aUnmatched.toString());
                }
                
				// if this suit is the same as groupSuit, add it to possible Run
                if (currentSuit == groupSuit && currentRank.toInt() == maxRank)
                {	
                	maxRank++;
                	possibleRun.add(aCards.get(j));
                    aUnmatched.remove(aCards.get(j));
                    // System.out.println("aUnmatched removed: " + aUnmatched.toString());
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
				//re-add cards from both failed meld attempts
				for (Card c : possibleCardRun) {
                    // System.out.println("adding card: " + c.toString());
                    aUnmatched.add(c);
                }
				for (Card c : possibleCardSet) {
                    // System.out.println("adding card: " + c.toString());
                    aUnmatched.add(c);
                }
            } else if (issetbool && !isrunbool) {
				aSets.add(possibleCardSet);
				//re-add cards from other failed meld attempt
				for (Card c : possibleCardRun) {
                    // System.out.println("adding card: " + c.toString());
                    aUnmatched.add(c);
                }
            } else if (!issetbool && isrunbool) {
				aRuns.add(possibleCardRun);
				//re-add cards from other failed meld attempt
				for (Card c : possibleCardSet) {
                    // System.out.println("adding card: " + c.toString());
                    aUnmatched.add(c);
                }
            } else if (issetbool && isrunbool) {
            	// TODO: check the values of the two and pick the higher one
            	aSets.add(possibleCardSet);
            	//re-add cards from other failed meld attempt
            	for (Card c : possibleCardRun) {
                    // System.out.println("adding card: " + c.toString());
                    aUnmatched.add(c);
                }
            }
            
        }
//         System.out.println("aUnmatched post: " + aUnmatched.toString());

	}
	
	/**
	 * Gets the cards in the hand.
	 * @return a copy of the cards in the hand
	 */
	public ArrayList<Card> getCards()
	{
		ArrayList<Card> ret = new ArrayList<Card>();
		for(Card c : aCards)
		{
			ret.add(c);
		}
		return ret;
	}
}
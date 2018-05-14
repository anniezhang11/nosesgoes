import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 */
public class CardSet implements ICardSet, Serializable
{
	private HashSet<Card> aCards;
	
	CardSet(Set<Card> pInput) 
	{
		aCards = new HashSet<Card>();
		for (Card c1 : pInput) 
		{
			Card c2 = new Card(c1.getRank(), c1.getSuit());
			aCards.add(c2);
		}
	}
	
	@Override
	public Iterator<Card> iterator()
	{
		return aCards.iterator();
	}

	@Override
	public boolean contains(Card pCard)
	{
		return aCards.contains(pCard);
	}

	@Override
	public int size()
	{
		return aCards.size();
	}

	@Override
	public boolean isGroup()
	{
        System.out.println("poss " + aCards.toString());
		if (aCards.size() < 3)
		{
			System.out.println("too short to be a set " + aCards.size());
			return false;
		} //Need to have three or four cards
		
		ArrayList<Card> aList = new ArrayList<Card>(aCards);
		
		Rank rank = null; //Loop through cards, check if they have the same rank
		for(Card c1 : aList) 
		{
            System.out.println("this rank " + c1.getRank().toInt());
			if(rank == null) 
			{ 
                rank = c1.getRank(); 
                System.out.println("og rank " + rank.toInt());
			} //set the first one, then check each after
			else if( rank.toInt() != (c1.getRank().toInt()) ) 
			{
                System.out.println("rank " + c1.getRank().toInt());
				return false;
			}
		}
		return true; //returns true if they all have the same rank
    }
    
    @Override
    public boolean remove(Card card) {
        return aCards.remove(card);
    }

	@Override
	public boolean isRun()
	{
		if (aCards.size() < 3) {
			System.out.println("too short to be a run " + aCards.size());
			return false;
		} //Need to have three or more cards
		
		ArrayList<Card> aList = new ArrayList<Card>(aCards);
		Collections.sort(aList, new Comparator<Card>() { //Sorts the cards, so we can check if they are sequential
			public int compare(Card pC1, Card pC2) 
			{
				if(pC1.getRank().ordinal() > pC2.getRank().ordinal()) 
				{
					return Integer.MAX_VALUE;
				}
				else if(pC1.getRank().ordinal() == pC2.getRank().ordinal()) 
				{ 
					return Integer.MIN_VALUE; 
				} 
				else 
				{
					return 0; 
				}
			};
        });
        System.out.println("sorted: " + aList.toString());
        
        Rank groupRank = aList.get(0).getRank();
        System.out.println("grouprank: " + groupRank);
        int maxRank = groupRank.toInt() + 1;
		
		for(int i = 1; i < aList.size() - 1; i++) {
			System.out.println("maxrank: " + maxRank);
			Rank currentRank = aList.get(i).getRank();
            System.out.println("thisrank: " + currentRank);
            
            if (currentRank.toInt() == maxRank) {	
            	maxRank++;
            } else {
            	return false;
            }
		}
		return true; //there are >=3 and in order they are consecutive
	}
}
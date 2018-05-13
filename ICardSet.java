import java.io.Serializable;
import java.util.*;

/**
 * Implementors of this class should be immutable.
 */
public interface ICardSet extends Iterable<Card>, Serializable
{
	/**
	 * @param pCard A card to check
	 * @return True if pCard is in this set
	 */
	boolean contains(Card pCard);

	/**
	 * @return The size of the group.
	 */
	int size();
	
	/**
	 * @return true if the object represents a group.
	 */
	boolean isGroup();
	
	/**
	 * @return true if the object represents a run.
	 */
    boolean isRun();
    
    boolean remove(Card card);
}
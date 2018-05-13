import java.util.*;


public class GinRummy {

    public static Action[] getAvailableActions(State state) {
        if (state.getHand().size() == 10) {
            return new Action[] {Action.DRAW_STOCK, Action.DRAW_DISCARD};
        } else if (isHandKnockable(state)) {
            return new Action[]{Action.KNOCK, Action.DISCARD};
        } else {
            return new Action[] {Action.DISCARD};
        }
    }

    public static boolean isHandKnockable(State state) {
        return calcDeadwood(state) <= 10;
    }


    private static HashMap<ArrayList<Card>, Integer> mem = new HashMap<>();

    public static int calcDeadwood(State state) {
        Hand hand = state.getHand();
        return hand.score();
    }
}
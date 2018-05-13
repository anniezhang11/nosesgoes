import java.util.Random;

public class BotPlayer implements Player {
    @Override
    public Action promptAction(Action[] actions, State state) {
        Random random = new Random();
        int choice = random.nextInt(actions.length);
        return actions[choice];
    }

    @Override
    public Card promptCard(State state) {
        Random random = new Random();
        int choice = random.nextInt(state.getHand().size());
        return state.getHand().get(choice);
    }
}
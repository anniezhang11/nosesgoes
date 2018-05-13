import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {
    private Deck deck;
    private ArrayList<Card> discard;
    private Hand[] hands;
    public static final int HAND_SIZE = 10;
    private boolean knocked;
    private Player[] players;
    private int turn;
    private static GinRummy ginRummy;

    public Game() {
        deck = new Deck();
        deck.shuffle();
        knocked = false;
        discard = new ArrayList<>();
        hands = new Hand[]{new Hand(), new Hand()};
        players = new Player[]{new HumanPlayer(), new BotPlayer()};
        turn = 0;

        IntStream.range(0, HAND_SIZE).forEach(
            i -> {
                for (Hand hand : hands) {
                    hand.add(deck.draw());
                }
            }
        );

        discard.add(0, deck.draw());
    }

    public void run() {
        while (!knocked && deck.size() > 2) {
            Player p = players[turn % players.length];
            Hand hand = hands[turn % players.length];

            // Draw
            State state = new State(hand, deck.size(), discard);
            Action action = p.promptAction(ginRummy.getAvailableActions(state), state);
            Card card;
            switch (action) {
                case DRAW_STOCK:
                    card = deck.draw();
                    hand.add(card);
                    hand.autoMatch();
                    break;
                case DRAW_DISCARD:
                    card = discard.remove(0);
                    hand.add(card);
                    hand.autoMatch();
                    break;
            }

            // Discard or Knock
            state = new State(hand, deck.size(), discard);
            action = p.promptAction(ginRummy.getAvailableActions(state), state);

            switch (action) {
                case DISCARD:
                    card = p.promptCard(state);
                    hand.remove(card);
                    hand.autoMatch();
                    discard.add(0, card);

                    break;
                case KNOCK:
                    knocked = true;
                    break;
            }

            turn++;
        }
    }

    public String toString() {
        String s = "";
        s += "Deck: " + deck + "\n";
        s += "Discard: " + discard.stream().map(Object::toString).collect(Collectors.joining(", ")) + "\n";
        for (Hand hand : hands) {
            s += "Hand: " + hand.getCards().stream().map(Object::toString).collect(Collectors.joining(" ")) + "\n";
        }
        return s;
    }
}


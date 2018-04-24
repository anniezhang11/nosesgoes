public enum Suit {
    CLUB ("clubs", 'C', "♣️"),
    DIAMOND ("diamonds", 'D', "♦️"),
    HEART ("hearts", 'H', "♥️"),
    SPADE ("spades", 'S', "♠️");

    private final String name;
    private final char letter;
    private final String symbol;

    Suit(String name, char letter, String symbol) {
        this.name = name;
        this.letter = letter;
        this.symbol = symbol;
    } 

    //TODO: methods
    public String toString() {
        return symbol;
    }

}
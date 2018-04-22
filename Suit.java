public enum Suit {
    CLUB ("clubs", 'C'),
    DIAMOND ("diamonds", 'D'),
    HEART ("hearts", 'H'),
    SPADE ("spades", 'S');

    private final String name;
    private final char letter;

    Suit(String name, char letter) {
    this.name = name;
    this.letter = letter;
    } 

    //TODO: methods
}
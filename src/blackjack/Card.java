package blackjack;

public class Card {
    private Suit suit;
    private Value value;
    
    public Card(Suit suit, Value value) //konstruktör för kortlek
    {
        this.value = value;
        this.suit = suit;
    }
    
    public String toString() //låter oss skriva ut i strängar
    {
        return this.suit.toString() + "-" + this.value.toString();
    }
    
    public Value getValue()
    {
        return this.value;
    }
}

package blackjack;
import java.util.*;

public class Deck {
    private ArrayList<Card> cards; //instansvariabler
    
    //konstruktör
    public Deck() 
    {
        this.cards = new ArrayList<Card>(); //skapa kortlek i arrayList
    }
    
    //generera kort i kortleken
    public void createFullDeck() 
    {
       for(Suit cardSuit : Suit.values()) //gå igenom alla sviter
       {
           for(Value cardValue : Value.values()) //gå igenom alla valörer
           {
               //lägg till kort i kortlek
               this.cards.add(new Card(cardSuit,cardValue));
           }
       }
    }
    
    //metod för att blanda kortleken
    public void shuffle()
    {
        //tillfällig kortlek för dragna kort
        ArrayList<Card> tempDeck = new ArrayList<Card>();
        //random funktion för att blanda kortlek
        Random random = new Random();
        int randomCardIndex = 0;
        int originalSize = this.cards.size();
        /*
        sparar lekens storlek. Eftersom vi ändrar storleken på leken under 
        spelets gång kommer this.cards.size() att förstöra for-loopen
        */
        for(int i = 0; i < originalSize; i++)
        {
            /*
            Generera random index följer syntax rand.nextInt((max - min) + 1) + min);
            */
            randomCardIndex = random.nextInt((this.cards.size()-1 - 0) + 1) + 0;
            tempDeck.add(this.cards.get(randomCardIndex));
            //lägg till det dragna kortet i den tillfälliga leken
            this.cards.remove(randomCardIndex);
            //ta bort draget kort från original lek
        }
        
        this.cards = tempDeck;
    }
    
    //metod för att skriva ut kort som strängar
    public String toString() 
    {
        String cardListOutput = "";
        int i = 0;
        for(Card aCard : this.cards)
        {
            cardListOutput += "\n" + i + "-" + aCard.toString();
            i++;
        }
        return cardListOutput;
    }
    
    //metod för att ta bort kort från kortlek
    public void removeCard(int i) 
    {
        this.cards.remove(i);
    }
    
    //metod för att hämta kort från kortlek
    public Card getCard(int i) 
    {
        return this.cards.get(i);
    }
    
    //metod för att lägga till kort i lek
    public void addCard(Card addCard) 
    {
        this.cards.add(addCard);
    }
    
    //metod som drar från kortleken, flyttar kort mellan kortlekarna
    public void draw(Deck comingFrom)
    {
        this.cards.add(comingFrom.getCard(0));
        comingFrom.removeCard(0);
    }
    
    //metod för att flytta tillbaka hel tempdeck till original kortlek
    public void moveAllToDeck(Deck moveTo)
    {
        int thisDeckSize = this.cards.size();
        
        //lägg kort i moveTo()
        for(int i=0; i < thisDeckSize; i++)
        {
            moveTo.addCard(this.getCard(i));
        }
        //töm kortlek
        for(int i=0; i < thisDeckSize; i++)
        {
            this.removeCard(0); //töm kort på index 0, fortsätt tills tom
        }
    }
    
    //metod för att returnera storleken på kortleken
    public int deckSize()
    {
        return this.cards.size();
    }
    
    //metod för att skriva ut värdet på handen
    public int cardsValue() 
    {
        int totalValue = 0; //börja med tom hand, inget värde
        int aces = 0;
        
        for(Card aCard : this.cards) //loop för att gå igenom alla kort
        {
            switch(aCard.getValue()) //kontrollera värde på kort
            {
                case TWO: totalValue += 2; break;
                case THREE: totalValue += 3; break;
                case FOUR: totalValue += 4; break;
                case FIVE: totalValue += 5; break;
                case SIX: totalValue += 6; break;
                case SEVEN: totalValue += 7; break;
                case EIGHT: totalValue += 8; break;
                case NINE: totalValue += 9; break;
                case TEN: totalValue += 10; break;
                case JACK: totalValue += 10; break;
                case QUEEN: totalValue += 10; break;
                case KING: totalValue += 10; break;
                case ACE: totalValue += 1; break;                
            }
        }
        for(int i = 0; i < aces; i++) //loop för att bestämma värde på ess (1 eller 11)
        {
            if(totalValue > 10) //om totalsumma är högre än 10, räkna ess som 1, annars 11
            {
                totalValue += 1;
            }
            else
            {
                totalValue += 11;
            }
        }
        
        return totalValue;
    }
}

package blackjack;
import java.util.*;

public class BlackJack {
    public static void main(String[] args) {
        //intro
        System.out.println("Welcome to Blackjack!");
        
        //skapa kortlek
        Deck playingDeck = new Deck(); //importera klassen Deck
        playingDeck.createFullDeck(); //skapa kortlek
        playingDeck.shuffle(); //blanda kortlek
        
        //ge spelaren en kortlek
        Deck playerDeck = new Deck(); // ge spelaren kort
        Deck dealerDeck = new Deck(); // ge dealer kort
        
        double playerMoney = 100.00; //ge spelaren startkapital för spelet
        
        Scanner input = new Scanner(System.in);
        int totalPlays = 0;
        int totalWins = 0;
        
        //while-loop för spelet
        while(playerMoney > 0) //fortsätt spela sålänge spelaren har pengar kvar
        {
            //Samla insats från spelare
            System.out.println("You have played "+totalPlays+" times, and won "+totalWins+" times");
            System.out.println("You have $"+playerMoney+", place bet plase.");
            double playerBet = input.nextDouble();
            if(playerBet > playerMoney)
            {
                System.out.println("You don't have enough money for that.");
            }
            
            boolean endRound = false; //boolean för att hålla koll på när rundor slutar
            
            //Starta spelet
            //Spelare börjar med 2 kort
            playerDeck.draw(playingDeck);
            playerDeck.draw(playingDeck);
            
            //Dealer börjar med 2 kort
            dealerDeck.draw(playingDeck);
            dealerDeck.draw(playingDeck);
            
            while(true) //loop för att dra fler kort i spelet
            {
                System.out.print("\nYour hand:");
                System.out.print(playerDeck.toString());
                System.out.println("\nYour hand value is currently: "+
                        playerDeck.cardsValue());
                
                //Visa dealerns hand, visa bara ett kort
                System.out.println("Dealer hand: "+dealerDeck.getCard(0).toString()+
                        "[Hidden]");
                
                //Fråga om spelaren vill dra kort eller stanna
                System.out.println("Do you want to (1)hit or (2)stand?");
                int response = input.nextInt();
                
                //De drar fler kort
                if(response == 1)
                {
                    playerDeck.draw(playingDeck);
                    System.out.println("You drew a: "+
                            playerDeck.getCard(playerDeck.deckSize()-1).toString());
                    
                    //om total är över 21, förlust
                    if(playerDeck.cardsValue() > 21)
                    {
                        System.out.println("Bust. Your current value is: "+
                                playerDeck.cardsValue());
                        playerMoney -= playerBet; //spelare förlorar sin insats
                        endRound = true;
                        break;
                    }
                }
                if(response == 2) //om spelare väljar att stanna, avbryt loop
                {
                    break;
                }
            }
            
            //visa dealerns kkort
            System.out.println("Dealers hand: "+dealerDeck.toString());
            //ta reda på om dealer har mer poäng än spelaren och om rundan är slut
            if((dealerDeck.cardsValue() > playerDeck.cardsValue()) && 
                    endRound == false)
            {
                System.out.println("Dealer wins!");
                playerMoney -= playerBet;
                endRound = true;
            }
            //simpel AI, dealer drar kort tills värdet är 16 eller högre, sen stannar han
            while((dealerDeck.cardsValue() < 17) && endRound == false)
            {
                dealerDeck.draw(playingDeck);
                System.out.println("Dealer drew: "+
                        dealerDeck.getCard(dealerDeck.deckSize()-1).toString());
            }
            
            //visa värde på dealerns hand
            System.out.println("Dealer's hands current value is: "+dealerDeck.cardsValue());
            //om dealerns total är över 21, vinst
            if((dealerDeck.cardsValue() > 21) && endRound == false)
            {
                System.out.println("Dealer is bust, you win!");
                playerMoney += playerBet;
                endRound = true;
                totalWins++;
            }
            // se om det är oavgjort
            if((playerDeck.cardsValue() == dealerDeck.cardsValue()) && endRound == false)
            {
                System.out.println("It's a draw!");
                endRound = true;
                totalPlays++;
            }
            //kontroll om spelare vunnit
            if((playerDeck.cardsValue() > dealerDeck.cardsValue()) && endRound == false)
            {
                System.out.println("You win!");
                playerMoney += playerBet;
                endRound = true;
                totalWins++;
            }
            else if(endRound == false)
            {
                System.out.println("You lose the round.");
                playerMoney -= playerBet;
                endRound = true;
            }
            //lägg tillbaka korten i kortleken
            playerDeck.moveAllToDeck(playingDeck);
            dealerDeck.moveAllToDeck(playingDeck);
            System.out.println("End of round.");
            totalPlays++;
        }
        
        System.out.println("You're out of money, too bad!");

    }
    
}

package blackjack;
import java.util.*;

public class BlackJack {
    public static void main(String[] args) {
        //intro
        System.out.println("Welcome to Blackjack!");
        
        //create new deck
        Deck playingDeck = new Deck();
        playingDeck.createFullDeck();
        playingDeck.shuffle();
        
        //give the player and dealer a deck
        Deck playerDeck = new Deck();
        Deck dealerDeck = new Deck();
        
        double playerMoney = 100.00; //Give player a bank
        
        Scanner input = new Scanner(System.in);
        int totalPlays = 0;
        int totalWins = 0;
        
        //while-loop for playing the game
        while(playerMoney > 0) //keep playing as long as the player has money left
        {
            //Ask player to place bet
            System.out.println("You have played "+totalPlays+" times, and won "+totalWins+" times");
            System.out.println("You have $"+playerMoney+", place bet plase.");
            double playerBet = input.nextDouble();
            if(playerBet > playerMoney)
            {
                System.out.println("You don't have enough money for that.");
            }
            
            boolean endRound = false; //variable to keep track of wether current round is going
            
            //Start the game, give the player 2 cards
            playerDeck.draw(playingDeck);
            playerDeck.draw(playingDeck);
            
            //Give the dealer 2 cards
            dealerDeck.draw(playingDeck);
            dealerDeck.draw(playingDeck);
            
            while(true) //while-loop to keep drawing cards
            {
                System.out.print("\nYour hand:");
                System.out.print(playerDeck.toString());
                System.out.println("\nYour hand value is currently: "+
                        playerDeck.cardsValue());
                
                //shows the dealers hand, show the first card and hides the others
                System.out.println("Dealer hand: "+dealerDeck.getCard(0).toString()+
                        "[Hidden]");
                
                //Ask if player wants to hit or stand
                System.out.println("Do you want to (1)hit or (2)stand?");
                int response = input.nextInt();
                
                //If player hits, draw more cards
                if(response == 1)
                {
                    playerDeck.draw(playingDeck);
                    System.out.println("You drew a: "+
                            playerDeck.getCard(playerDeck.deckSize()-1).toString());
                    
                    //If hand value is over 21, bust
                    if(playerDeck.cardsValue() > 21)
                    {
                        System.out.println("Bust. Your current value is: "+
                                playerDeck.cardsValue());
                        playerMoney -= playerBet; //Withdraw player bet from player bank
                        endRound = true;
                        break;
                    }
                }
                if(response == 2) //If player stands, end the loop
                {
                    break;
                }
            }
            
            //Show dealers full hand
            System.out.println("Dealers hand: "+dealerDeck.toString());
            //Check wether dealers value is higher than players and if game is still going
            if((dealerDeck.cardsValue() > playerDeck.cardsValue()) && 
                    endRound == false)
            {
                System.out.println("Dealer wins!");
                playerMoney -= playerBet;
                endRound = true;
            }
            //dealer-AI, if hand value is 16 or lower, hit.
            while((dealerDeck.cardsValue() < 17) && endRound == false)
            {
                dealerDeck.draw(playingDeck);
                System.out.println("Dealer drew: "+
                        dealerDeck.getCard(dealerDeck.deckSize()-1).toString());
            }
            
            //Show the value of dealers hand
            System.out.println("Dealer's hands current value is: "+dealerDeck.cardsValue());
            //If dealer is bust, you win
            if((dealerDeck.cardsValue() > 21) && endRound == false)
            {
                System.out.println("Dealer is bust, you win!");
                playerMoney += playerBet;
                endRound = true;
                totalWins++;
            }
            //check if it's a draw
            if((playerDeck.cardsValue() == dealerDeck.cardsValue()) && endRound == false)
            {
                System.out.println("It's a draw!");
                endRound = true;
                totalPlays++;
            }
            //Check if player won
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
            //put back cards into the deck
            playerDeck.moveAllToDeck(playingDeck);
            dealerDeck.moveAllToDeck(playingDeck);
            System.out.println("End of round.");
            totalPlays++;
        }
        
        System.out.println("You're out of money, too bad!");

    }
    
}

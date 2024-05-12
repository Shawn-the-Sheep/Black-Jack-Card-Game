import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class BlackJackUI
{
    public static void main(String[] args)
    {
        final int COST_PER_PERK = 20;
        Deck deck = new Deck();
        String[] ranks = {"Ace", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "Jack", "Queen", "King"};
        String[] suits = {"clubs", "diamonds", "hearts", "spades"};
        Map<String, Integer> ranks_to_values = new HashMap<>();

        for (int i = 1; i < ranks.length; i++)
        {
            if (i > 9)
            {
                ranks_to_values.put(ranks[i], 10);
            }
            else
            {
                ranks_to_values.put(ranks[i], i + 1);
            }
        }

        for (String rank : ranks)
        {
            for (String suit : suits)
            {
                if (rank.equals("Ace"))
                {
                    deck.addCard(new Card(rank, suit, -1));
                }
                else
                {
                    deck.addCard(new Card(rank, suit, ranks_to_values.get(rank)));
                }
            }
        }

        boolean bought = false;
        User user = new User("The user");
        Dealer dealer = new Dealer("The dealer");
        Scanner in = new Scanner(System.in);
        displayIntro();

        while (true)
        {
            System.out.print(">");
            String action = in.nextLine();

            if (action.equalsIgnoreCase("QUIT") || action.equalsIgnoreCase("Q"))
            {
                in.close();
                return;
            }
            else if (action.equalsIgnoreCase("TOKENS"))
            {
                System.out.println("You currently have " + user.getTokens() + " tokens\n");
            }
            else if (action.equalsIgnoreCase("BUY"))
            {
                bought = true;
                user.subtractTokens(COST_PER_PERK);
                System.out.println("For the next turn, you will be able to see the dealer's hidden card\n");
            }
            else if (action.equalsIgnoreCase("PLAY"))
            {
                user.setBet();
                deck.shuffle();

                Card userFirstCard = deck.drawCard();
                Card userSecondCard = deck.drawCard();

                Card dealerFirstCard = deck.drawCard();
                Card dealerSecondCard = deck.drawCard();

                user.handleFirstBatch(userFirstCard, userSecondCard);
                dealer.handleFirstBatch(dealerFirstCard, dealerSecondCard);

                System.out.println();

                if (bought)
                {
                    dealer.displayHand();
                    bought = false;
                }
                else
                {
                    System.out.println("DEALER'S HAND");
                    System.out.println(dealerFirstCard);
                    System.out.println("one card hidden");
                }

                System.out.println();
                user.displayHand();

                boolean not_busted = user.currentTurn(deck);

                if (!not_busted)
                {
                    displayFinalHand(user, dealer);
                    System.out.println(dealer + "\n");
                    dealer.resetSum();
                    dealer.emptyMap();
                }
                else if (user.getSum() == Player.LIMIT)
                {
                    displayFinalHand(user, dealer);
                    System.out.println(user + "\n");
                    user.win();
                    dealer.resetSum();
                    dealer.emptyMap();
                }
                else
                {
                    not_busted = dealer.currentTurn(user.getSum(), deck);

                    if (!not_busted)
                    {
                        displayFinalHand(user, dealer);
                        System.out.println(user + "\n");
                        user.win();
                    }
                    else
                    {
                        displayFinalHand(user, dealer);
                        System.out.println(dealer + "\n");
                    }
                }

                user.resetSum();
                deck.returnToDeck(user.getCards());
                deck.returnToDeck(dealer.getCards());
                
            }
        }
    }
    public static void displayIntro()
    {
        System.out.println("Welcome to the Blackjack card game\n");
        System.out.println("4 actions are supported\n");
        System.out.println("PLAY: play a round of black jack");
        System.out.println("TOKENS: displays your current number of tokens");
        System.out.println("BUY: pay 20 tokens to see dealer's hidden card next turn");
        System.out.println("QUIT or Q: quit the application\n");
    }
    public static void displayFinalHand(Player a, Player b)
    {
        a.displayHand();
        b.displayHand();
    }
}
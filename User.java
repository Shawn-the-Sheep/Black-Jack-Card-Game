import java.util.Scanner;

public class User extends Player
{
    private int tokens, bet;
    private static Scanner in = new Scanner(System.in);

    public User(String name)
    {
        super(name);
        tokens = 100;
        bet = 0;
    }

    private static String HitorStay()
    {
        boolean valid_answer = false;
        String answer = null;

        while (!valid_answer)
        {
            System.out.print("Hit or Stay: ");
            answer = in.nextLine();
            System.out.println();

            if (answer.equalsIgnoreCase("H") || answer.equalsIgnoreCase("S"))
            {
                valid_answer = true;
            }
        }
        return answer;
    }

    public int getTokens()
    {
        return tokens;
    }

    public void subtractTokens(int amount)
    {
        tokens -= amount;
    }

    private static boolean onlyDigits(String str)
    {
        for (int i = 0; i < str.length(); i++)
        {
            if (!Character.isDigit(str.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }

    public void setBet()
    {
        int bet = 0;
        boolean valid_answer = false;
        System.out.println("You currently have " + tokens + " tokens");
        while (!valid_answer)
        {
            System.out.print("Enter a bet: ");
            String answer = in.nextLine();

            if (onlyDigits(answer))
            {
                bet = Integer.parseInt(answer);
                if (bet <= tokens)
                {
                    valid_answer = true;
                }
                else
                {
                    System.out.println("you cannot bet more than you have");
                }
            }
            else
            {
                System.out.println("bet must be an integer");
            }
        }
        this.bet = bet;
        tokens -= bet;
    }

    public boolean currentTurn(Deck deck)
    {
        System.out.println("Remember to enter 'H' or 'h' for Hit and 'S' or 's' for Stay");

        while (getSum() < LIMIT)
        {
            String answer = HitorStay();

            if (answer.equalsIgnoreCase("S"))
            {
                break;
            }

            Card current_card = deck.drawCard();
            System.out.println("The user has drawn a " + current_card + "\n");
            addCard(current_card);

            if (current_card.getRank().equals("Ace"))
            {
                if (getSum() + SECOND_POSSIBLE_VALUE_FOR_ACE > LIMIT)
                {
                    addAce(current_card, FIRST_POSSIBLE_VALUE_FOR_ACE);
                    addSum(FIRST_POSSIBLE_VALUE_FOR_ACE);
                }
                else
                {
                    addAce(current_card, SECOND_POSSIBLE_VALUE_FOR_ACE);
                    addSum(SECOND_POSSIBLE_VALUE_FOR_ACE);
                }
            }
            else
            {
                addSum(current_card.getValue());
            }

            if (getSum() > LIMIT)
            {
                underLimit();
            }
        }
        emptyMap();
        
        if (getSum() > LIMIT)
        {
            return false;
        }
        
        return true;
    }

    public void win()
    {
        tokens += 2 * bet;
    }

    public void displayHand()
    {
        System.out.println("USER'S HAND");
        super.displayHand();
    }
}
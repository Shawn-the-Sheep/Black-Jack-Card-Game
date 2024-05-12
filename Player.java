import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;

public class Player
{
    private ArrayList<Card> cards;
    private Map<Card, Integer> ace_values;
    private int sum;
    private String name;
    public static final int LIMIT = 21;
    public static final int FIRST_POSSIBLE_VALUE_FOR_ACE = 1;
    public static final int SECOND_POSSIBLE_VALUE_FOR_ACE = 11;

    public Player(String name)
    {
        this.name = name;
        cards = new ArrayList<>();
        sum = 0;
        ace_values = new TreeMap<>();
    }

    public void addCard(Card card)
    {
        cards.add(card);
    }

    public ArrayList<Card> getCards()
    {
        return cards;
    }

    public int getSum()
    {
        return sum;
    }

    public String toString()
    {
        return name + " has won";
    }

    public void emptyMap()
    {
        ace_values = new TreeMap<>();
    }

    public void addAce(Card card, int value)
    {
        ace_values.put(card, value);
    }

    public void addSum(int amount)
    {
        sum += amount;
    }

    public void resetSum()
    {
        sum = 0;
    }

    public void displayHand()
    {
        for (Card card : cards)
        {
            System.out.println(card);
        }
        System.out.println();
    }

    public void underLimit()
    {
        Iterator<Card> itr = ace_values.keySet().iterator();

        while (itr.hasNext())
        {
            Card current_card = itr.next();
            if (ace_values.get(current_card) == SECOND_POSSIBLE_VALUE_FOR_ACE)
            {
                sum -= SECOND_POSSIBLE_VALUE_FOR_ACE - FIRST_POSSIBLE_VALUE_FOR_ACE;
                ace_values.put(current_card, FIRST_POSSIBLE_VALUE_FOR_ACE);
                break;
            }
        }
    }

    public void handleFirstBatch(Card firstCard, Card secondCard)
    {
        addCard(firstCard);
        addCard(secondCard);

        if (firstCard.getRank().equals("Ace"))
        {
            addSum(Player.SECOND_POSSIBLE_VALUE_FOR_ACE);
            addAce(firstCard, Player.SECOND_POSSIBLE_VALUE_FOR_ACE);

            if (secondCard.getRank().equals("Ace"))
            {
                addSum(Player.FIRST_POSSIBLE_VALUE_FOR_ACE);
                addAce(secondCard, Player.FIRST_POSSIBLE_VALUE_FOR_ACE);
            }
            else
            {
                addSum(secondCard.getValue());
            }
        }
        else
        {
            addSum(firstCard.getValue());

            if (secondCard.getRank().equals("Ace"))
            {
                addSum(Player.SECOND_POSSIBLE_VALUE_FOR_ACE);
                addAce(secondCard, Player.SECOND_POSSIBLE_VALUE_FOR_ACE);
            }
            else
            {
                addSum(secondCard.getValue());
            }
        }
    }
}
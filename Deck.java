import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Collections;

public class Deck
{
    private ArrayList<Card> cards;

    public Deck()
    {
        cards = new ArrayList<>();
    }

    public Card drawCard()
    {
        return cards.remove(cards.size() - 1);
    }

    public void addCard(Card card)
    {
        cards.add(card);
    }

    public void shuffle()
    {
        Collections.shuffle(cards);
    }

    public void returnToDeck(ArrayList<Card> otherCards)
    {
        ListIterator<Card> itr = otherCards.listIterator();

        while (itr.hasNext())
        {
            cards.add(itr.next());
            itr.remove();
        }
    }
}
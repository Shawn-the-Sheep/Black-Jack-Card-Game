public class Dealer extends Player
{
    public Dealer(String name)
    {
        super(name);
    }

    public boolean currentTurn(int sumToBeat, Deck deck)
    {
        boolean not_busted = true;
        
        while (getSum() <= sumToBeat)
        {
            Card current_card = deck.drawCard();
            System.out.println("The dealer has drawn a " + current_card + "\n");
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
            not_busted = false;
        }

        resetSum();
        return not_busted;
    }

    public void displayHand()
    {
        System.out.println("DEALER'S HAND");
        super.displayHand();
    }
}
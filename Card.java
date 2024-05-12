import java.lang.Comparable;

public class Card implements Comparable<Card>
{
    private String rank, suit;
    private int value;

    public Card(String rank, String suit, int value)
    {
        this.rank = rank;
        this.suit = suit;
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    public String toString()
    {
        return rank + " of " + suit;
    }
    
    public int compareTo(Card other)
    {
        return value - other.value;
    }

    public String getRank()
    {
        return rank;
    }
}
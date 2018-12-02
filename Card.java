/**
 *  ***Texas Hold'em - test project***
 *
 *  Card
 *      This class makes a card object to emulate
 *      a regular playingcard. Gets suit from Suit.java
 *
 * @version 1.0
 * @since 2018-11-30
 * @author Axzter
 *
 */

public class Card implements Comparable<Card> {

    private Suit suit;
    private int rank;

    public Card(Suit suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    public int getSuitByNumber() {
        if (suit == Suit.h) {
            return 0;
        } else if (suit == Suit.s) {
            return 1;
        } else if (suit == Suit.c) {
            return 2;
        } else return 3;

    }


    public int compareTo(Card o) {
        if (rank < o.rank) {
            return -1;
        }
        if (rank > o.rank) {
            return 1;
        }
        return 0;
    }

    public String toString() {
        String printRank = "";

        if (rank >= 2 && rank <= 10) {
            printRank = String.valueOf(rank);
        } else if (rank == 11) {
            printRank = "J";
        } else if (rank == 12) {
            printRank = "Q";
        } else if (rank == 13) {
            printRank = "K";
        } else if (rank == 14) {
            printRank = "A";
        }

        return printRank + suit;
    }
}

/**
 *  ***Texas Hold'em - test project***
 *
 *  Deck
 *      This class makes a deck of 52 cards
 *      using the card object from Card.java
 *
 * @version 1.0
 * @since 2018-11-30
 * @author Axzter
 *
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {

    private final static Random RND = new Random();
    private ArrayList<Card> cards = new ArrayList<>();

    public Deck() {
        for (Suit suit : Suit.values()) {
            for (int rank = 2; rank <= 14; rank++) {
                Card c = new Card(suit, rank);
                cards.add(c);
            }
        }
    }

    public void shuffle() {
        for (int i = 0; i < 3000; i++) {
            cards.add(cards.remove(RND.nextInt(cards.size())));
        }

        Collections.shuffle(cards);

    }

    public void cheatDeck() {
        Card c1 = new Card(Suit.c, 4);
        Card c2 = new Card(Suit.s, 12);
        Card c3 = new Card(Suit.s, 5);
        Card c4 = new Card(Suit.d, 14);
        Card c5 = new Card(Suit.h, 12);
        Card c6 = new Card(Suit.c, 12);
        Card c7 = new Card(Suit.s, 14);

        cards.add(0, c1);
        cards.add(0, c2);
        cards.add(0, c3);
        cards.add(0, c4);
        cards.add(0, c5);
        cards.add(0, c6);
        cards.add(0, c7);


    }

    public void printDeck() {
        for (Card card : cards) {
            System.out.println(card);
        }
    }

    public Card drawCard() {
        return cards.remove(0);
    }
}

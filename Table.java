/**
 *  ***Texas Hold'em - test project***
 *
 *  Table
 *      This class makes a Table for the games common
 *      cards to be shown. Uses a deck from Deck.java
 *
 * @version 1.0
 * @since 2018-11-30
 * @author Axzter
 *
 */

import java.util.ArrayList;

public class Table {

    ArrayList<Card> thisTable = new ArrayList<>();

    public Table(Deck deck) {

    }

    public void flop(Deck deck) {
        for (int i = 0; i <= 2; i++) {
            Card c = deck.drawCard();
            thisTable.add(c);
        }
    }

    public void turn(Deck deck) {
        thisTable.add(deck.drawCard());

    }

    public void river(Deck deck) {
        thisTable.add(deck.drawCard());

    }

    public void clearTable() {
        this.thisTable.clear();
    }

    public void printTable() {
        for (Card card : thisTable) {
            System.out.print(card + " ");
        }
    }

}

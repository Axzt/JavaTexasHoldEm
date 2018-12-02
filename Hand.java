/**
 *  ***Texas Hold'em - test project***
 *
 *  Hand
 *      This class makes a Hand object that holds
 *      the players cards, and a copy of the tables cards.
 *      Using; Deck.java and Card.java
 *
 * @version 1.0
 * @since 2018-11-30
 * @author Axzter
 *
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Hand {

    Card[] myHand = new Card[2];
    ArrayList<Card> currentHand = new ArrayList<>();

    private String name = "";


    public Hand(String name, Deck deck) {

        this.name = name;

        myHand[0] = deck.drawCard();
        myHand[1] = deck.drawCard();
        sort();
    }

    public String getName() {
        return name;
    }

    public void handFlop(Table table) {
        currentHand.clear();
        for (Card card : myHand) {
            currentHand.add(card);
        }
        for (Card card : table.thisTable) {
            currentHand.add(card);
        }
        sort();
    }

    public void handTurn(Table table) {
        currentHand.clear();
        for (Card card : myHand) {
            currentHand.add(card);
        }
        for (Card card : table.thisTable) {
            currentHand.add(card);
        }
        sort();
    }

    public void handRiver(Table table) {
        currentHand.clear();
        for (Card card : myHand) {
            currentHand.add(card);
        }
        for (Card card : table.thisTable) {
            currentHand.add(card);
        }
        sort();
    }

    public void printHand() {

        if (myHand.length > currentHand.size()) {
            System.out.print(Arrays.toString(myHand));
        } else {

            for (Card card : currentHand) {
                System.out.print(card + " ");
            }
        }
        System.out.println();

    }

    private void sort() {
        Arrays.sort(myHand);
        Collections.sort(currentHand);
    }

    public String toString() {
        String result = "";

        if (myHand.length > currentHand.size()) {
            for (Card card : myHand) {
                result += (card + " ");
            }
        } else {
            for (Card card : currentHand) {
                result += (card + " ");
            }
        }


        return result;
    }
}

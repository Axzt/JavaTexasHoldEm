/**
 *  ***Texas Hold'em - test project***
 *
 *  Hand Evaluator
 *      This class evaluates the strength of a poker hand
 *      using the Hand class from Hand.java
 *
 * @version 1.0
 * @since 2018-11-30
 * @author Axzter
 *
 */

import java.util.ArrayList;
import java.util.Collections;

public class HandEvaluator {

    //for index 0-6 aka 7 cards
    private int numberOfCards = 6;

    private ArrayList<Card> cards = new ArrayList<>();

    public int kicker = -1;

    public int[] cardsInEachRank = new int[15];

    public int[] cardsInEachSuit = new int[4];

    public int highCard = -1;

    public int pairHigh = -1;

    public int pairLow = -1;

    public int noOfPairs = 0;

    public int noOfTrips = 0;

    public int[] trips = new int[2];

    public int maxPairsToCount = 2;

    public int[] pairs = new int[maxPairsToCount];

    public int tripsRank = -1;

    public int quadsRank = -1;

    public int flushSuit = -1;

    public int flushRank = -1;

    public int straightRank = -1;

    public boolean aceCanCountAsOne = false;
    public String handResult = "";

    ArrayList<Card> straightArray = new ArrayList<>();

    ArrayList<Card> flushArray = new ArrayList<>();

    ArrayList<Card> straightFlushArray = new ArrayList<>();

    public ArrayList<Integer> kickers = new ArrayList<>();

    public int result = -1;

    public int highOne = -1;

    public int highTwo = -1;

    public HandEvaluator(Hand hand) {

        cards = hand.currentHand;


        sortByNumOfSuitAndRank();
        findDuplicates();
        findFlush();
        findStraight();


        if (royalFlush()) {
            result = 10;
            handResult = ("Royal Flush in " + getSuitByInt(flushSuit) + "!!!");
        } else if (straightFlush()) {
            result = 9;
            highOne = straightRank;
            handResult = ("Straight Flush " + cardConverter(straightRank) + " high, in " + getSuitByInt(flushSuit) + "!");
        } else if (quads()) {
            result = 8;
            highOne = quadsRank;
            calculateKicker();
            handResult = ("Four of a kind with " + cardConverter(quadsRank) + " - Kicker: " + cardConverter(kicker));
        } else if (house()) {
            highOne = tripsRank;
            highTwo = pairs[0];
            handResult = ("Full House with " + cardConverter(tripsRank) + " and " + cardConverter(pairs[0]) + ".");
            result = 7;
        } else if (flush()) {
            highOne = flushRank;
            handResult = ("Flush in " + getSuitByInt(flushSuit) + " with " + cardConverter(flushRank) + " high.");
            result = 6;
        } else if (straight()) {
            highOne = straightRank;
            handResult = ("Straight with " + cardConverter(straightRank) + " high.");
            result = 5;
        } else if (trips()) {
            calculateKicker();
            highOne = tripsRank;
            handResult = ("Three of a kind with " + cardConverter(tripsRank) + ". - Kicker: " + cardConverter(kicker));
            result = 4;
        } else if (twoPair()) {
            calculateKicker();
            highOne = pairs[0];
            highTwo = pairs[1];
            handResult = ("Two pairs, " + cardConverter(pairs[0]) + " and " + cardConverter(pairs[1]) + ". - Kicker: " + cardConverter(kicker));
            result = 3;
        } else if (pair()) {
            calculateKicker();
            highOne = pairHigh;
            handResult = ("One pair in " + cardConverter(pairHigh) + ". - Kicker: " + cardConverter(kicker));
            result = 2;
        } else if (highCard()) {
            calculateKicker();
            highOne = highCard;
            handResult = ("High card with " + cardConverter(highCard) + ". - Kicker: " + cardConverter(kicker));
            result = 1;
        }


    }

    private void sortByNumOfSuitAndRank() {
        for (Card card : cards) {
            cardsInEachRank[card.getRank()]++;
            cardsInEachSuit[card.getSuitByNumber()]++;
        }
    }

    private void findDuplicates() {
        for (int i = 14; i >= 2; i--) {
            if (cardsInEachRank[i] == 4) {
                quadsRank = i;
            } else if (cardsInEachRank[i] == 3) {
                trips[noOfTrips++] = i;
                tripsRank = trips[0];
            } else if (cardsInEachRank[i] == 2) {
                if (noOfPairs < maxPairsToCount) {
                    pairs[noOfPairs++] = i;
                }
            }
        }
    }

    private void findFlush() {
        boolean isFlush = false;
        for (int i = 0; i < 4; i++) {
            if (cardsInEachSuit[i] >= 5) {
                flushSuit = i;
                isFlush = true;
                for (Card card : cards) {
                    if (card.getSuitByNumber() == flushSuit) {
                        if (card.getRank() > flushRank) {
                            flushRank = card.getRank();
                        }
                    }
                }
            }
        }
        if (isFlush = true) {
            for (Card card : cards) {
                if (card.getSuitByNumber() == flushSuit) {
                    flushArray.add(card);
                }
            }
        }
    }

    private void findStraight() {
        boolean inStraight = false;
        int rank = -1;
        int count = 0;
        for (int i = 14; i >= 2; i--) {
            if (cardsInEachRank[i] == 0) {
                inStraight = false;
                count = 0;
            } else {
                if (!inStraight) {
                    inStraight = true;
                    rank = i;
                }
                count++;
                if (count >= 5) {
                    straightRank = rank;
                    break;
                }
            }
        }
        if ((count == 4) && (rank == 5) && (cardsInEachRank[14] > 0)) {
            aceCanCountAsOne = true;
            straightRank = rank;
        }


        if (inStraight == true) {
            int highestRank = straightRank;
            int nextrank = straightRank;

            Collections.reverse(cards);
            for (Card card : cards) {

                if (nextrank == 1) {
                    nextrank = 14;
                }
                if (card.getRank() == nextrank) {
                    straightArray.add(card);
                    nextrank--;
                }
            }
            Collections.sort(cards);
            Collections.sort(straightArray);
        }
    }

    private boolean findStraightSecond() {
        boolean inStraight = false;
        int rank = -1;
        int count = 0;

        int[] cardsInRank = new int[15];

        for (Card card : straightFlushArray) {
            cardsInRank[card.getRank()]++;
        }

        for (int i = 14; i >= 2; i--) {
            if (cardsInRank[i] == 0) {
                inStraight = false;
                count = 0;
            } else {
                if (!inStraight) {
                    inStraight = true;
                    rank = i;
                }
                count++;
                if (count >= 5) {
                    straightRank = rank;
                    break;
                }
            }
        }
        if ((count == 4) && (rank == 5) && (cardsInEachRank[14] > 0)) {
            aceCanCountAsOne = true;
        }
        if (inStraight) {
            return true;
        } else {
            return false;
        }
    }

    private boolean royalFlush() {
        if (straightFlush() && straightRank == 14 && flushRank == 14) {
            return true;
        } else {
            return false;
        }
    }

    public boolean straightFlush() {

        int highest = -1;

        if (straightRank != -1 && flushRank != -1) {

            for (Card card : straightArray) {
                if (flushArray.contains(card)) {
                    straightFlushArray.add(card);
                }
            }

        }

        if (straightFlushArray.size() >= 5 && findStraightSecond()) {
            for (Card card : straightFlushArray) {
                if (card.getRank() > highest) {
                    highest = card.getRank();
                }
            }

            straightRank = highest;
            flushRank = highest;
            return true;
        } else {
            return false;
        }

    }

    private boolean quads() {
        if (quadsRank != -1) {
            for (Card card : cards) {
                if (card.getRank() != quadsRank) {
                    kickers.add(card.getRank());
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean house() {
        if ((tripsRank != -1) && ((pairs[0] > 0) || trips[1] != 0)) {
            tripsRank = trips[0];
            if (trips[1] > pairs[0]) {
                pairs[0] = trips[1];
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean flush() {
        if (flushRank != -1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean straight() {
        if (straightRank != -1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean trips() {
        if (tripsRank != -1) {
            for (Card card : cards) {
                int rank = card.getRank();
                if (rank != tripsRank) {
                    kickers.add(rank);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean twoPair() {
        if (noOfPairs == 2) {
            pairHigh = pairs[0];
            pairLow = pairs[1];

            for (Card card : cards) {
                int rank = card.getRank();
                if ((rank != pairHigh) && (rank != pairLow)) {
                    kickers.add(rank);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean pair() {
        if (noOfPairs == 1) {
            pairHigh = pairs[0];
            for (Card card : cards) {
                int rank = card.getRank();
                if (rank != pairHigh) {
                    kickers.add(rank);
                }
            }
            return true;
        } else {
            return false;
        }

    }

    private boolean highCard() {
        for (Card card : cards) {
            if (card.getRank() > highCard) {
                highCard = card.getRank();
            }
        }
        for (Card card : cards) {
            if (card.getRank() != highCard) {
                kickers.add(card.getRank());
            }
        }
        return true;
    }

    private void calculateKicker() {
        for (int i : kickers) {
            if (i > kicker) {
                kicker = i;
            }
        }
    }

    public String cardConverter(int i) {
        String result = "";
        if (i == 14) {
            result = "Aces";
        } else if (i == 13) {
            result = "Kings";
        } else if (i == 12) {
            result = "Queens";
        } else if (i == 11) {
            result = "Jacks";
        } else {
            result = String.valueOf(i + "'s");
        }
        return result;
    }

    public String getSuitByInt(int i) {
        if (i == 0) {
            return "hearts";
        } else if (i == 1) {
            return "spades";
        } else if (i == 2) {
            return "clubs";
        } else return "diamonds";
    }

}
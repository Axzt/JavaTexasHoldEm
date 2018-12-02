/**
 *  ***Texas Hold'em - test project***
 *
 *  TexasHold'Em
 *      This is the main class of the program.
 *      Controlls the stages of the game, and checks for winners.
 *      Using; Table.java, Hand.java, HandEvaluator.java, Card.java, Deck.java
 *
 * @version 1.0
 * @since 2018-11-30
 * @author Axzter
 *
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TexasHoldEm {

    Scanner kb = new Scanner(System.in);

    ArrayList<Hand> allPlayers = new ArrayList<>();

    public void flop(Table table, Deck deck) {
        table.flop(deck);
        for (Hand hand : allPlayers) {
            hand.handFlop(table);
        }
    }

    public void turn(Table table, Deck deck) {
        table.turn(deck);
        for (Hand hand : allPlayers) {
            hand.handTurn(table);
        }
    }

    public void river(Table table, Deck deck) {
        table.river(deck);
        for (Hand hand : allPlayers) {
            hand.handRiver(table);
        }
    }

    public void setNumberOfPlayers(int players, Deck deck) {

        while (players > 23 || players < 1) {
            if (players > 23) {
                System.out.println("Too many players! More than 52 cards needed");
                System.out.println("Max players is 23. try again.");
                players = kb.nextInt();
                kb.nextLine();

            } else if (players < 1) {
                System.out.println("Too few players, try again.");
                players = kb.nextInt();
                kb.nextLine();
            }
        }
        String name = "Player ";

        for (int i = 1; i <= players; i++) {
            Hand hand = new Hand((name + String.valueOf(i)), deck);
            allPlayers.add(hand);
        }
    }

    public Deck setUpDeck() {

        Deck deck = new Deck();
        deck.shuffle();
        return deck;
    }

    public Table setUpTable(Deck deck) {
        Table table = new Table(deck);
        return table;
    }

    public void newGame() {

        Deck deck = setUpDeck();
        Table table = setUpTable(deck);

        System.out.print("How many players will be playing? :");
        int players = kb.nextInt();
        kb.nextLine();
        System.out.println();

        setNumberOfPlayers(players, deck);


        for (Hand hand : allPlayers) {
            System.out.print(hand.getName() + ": ");
            System.out.println(Arrays.toString(hand.myHand));
        }

        flop(table, deck);


        System.out.println();
        System.out.println("Table after flop: ");
        table.printTable();
        System.out.println();
        System.out.println();
        temporaryWinners(allPlayers);


        turn(table, deck);


        System.out.println();
        System.out.println("Table after turn: ");
        table.printTable();
        System.out.println();
        System.out.println();
        temporaryWinners(allPlayers);

        river(table, deck);
        System.out.println();
        System.out.println("Table after River: ");
        table.printTable();
        System.out.println();



        determineWinners(allPlayers);

        allPlayers.clear();

    }

    public void temporaryWinners(ArrayList arr) {
        ArrayList<Hand> winners = playersWithBestKickers(playersWithBestHand(playersWithBestResult(arr)));


        if(winners.size() == 1){
            System.out.print("Strongest hand: "+ winners.get(0).getName());
            System.out.println();
        }else{
            System.out.print("Strongest hands: ");
            for (Hand hand : winners) {
                System.out.print(hand.getName() + ", ");
            }
            System.out.println();
        }


    }

    public ArrayList playersWithBestResult(ArrayList arr) {
        ArrayList<Hand> players = arr;
        ArrayList<Hand> winners = new ArrayList<>();

        int highestResult = -1;


        for (Hand hand : players) {
            HandEvaluator eval = new HandEvaluator(hand);
            if (eval.result > highestResult) {
                highestResult = eval.result;
            }
        }

        for (Hand hand : players) {
            HandEvaluator eval = new HandEvaluator(hand);
            if (eval.result == highestResult) {
                winners.add(hand);
            }
        }
        return winners;
    }

    public ArrayList playersWithBestHand(ArrayList arr) {
        ArrayList<Hand> players = arr;
        ArrayList<Hand> sameValueHigh = new ArrayList<>();
        ArrayList<Hand> winners = new ArrayList<>();

        int highValue = -1;
        int highValueTwo = -1;

        if (players.size() == 1) {
            winners.add(players.get(0));
        } else {
            for (Hand hand : players) {
                HandEvaluator eval = new HandEvaluator(hand);
                if (eval.highOne > highValue) {
                    highValue = eval.highOne;
                }
            }
            for(Hand hand: players){
                HandEvaluator eval = new HandEvaluator(hand);
                if (eval.highOne == highValue) {
                    sameValueHigh.add(hand);
                }
            }

            for(Hand hand: sameValueHigh){
                HandEvaluator eval = new HandEvaluator(hand);
                if(eval.highTwo > highValueTwo){
                    highValueTwo = eval.highTwo;
                }
            }
            for(Hand hand: sameValueHigh){
                HandEvaluator eval = new HandEvaluator(hand);
                if(eval.highTwo == highValueTwo){
                    winners.add(hand);
                }
            }
        }
        return winners;
    }

    public ArrayList playersWithBestKickers(ArrayList arr) {
        ArrayList<Hand> players = arr;
        ArrayList<Hand> winners = new ArrayList<>();

        int kickerResult = -1;

        if (players.size() == 1) {
            winners.add(players.get(0));
        } else {
            for (Hand hand : players) {
                HandEvaluator eval = new HandEvaluator(hand);
                if (eval.kicker > kickerResult) {
                    kickerResult = eval.kicker;
                }
            }
            for (Hand hand : players) {
                HandEvaluator eval = new HandEvaluator(hand);
                if (eval.kicker == kickerResult) {
                    winners.add(hand);
                }
            }
        }
        return winners;
    }

    public void determineWinners(ArrayList arr) {

        ArrayList<Hand> winners = playersWithBestKickers(playersWithBestHand(playersWithBestResult(arr)));

        if (winners.size() == 1) {
            HandEvaluator eval = new HandEvaluator(winners.get(0));
            System.out.println();
            System.out.println("___________________________________");
            System.out.println("We have one winner! - " + winners.get(0).getName());
            System.out.println("With the winning hand: ");
            System.out.println(eval.handResult);
        }
        if (winners.size() > 1) {
            System.out.println();
            System.out.println("___________________________________");
            System.out.println("We have a draw between: ");
            int winner = 1;

            for (Hand hand : winners) {
                HandEvaluator eval = new HandEvaluator(hand);
                System.out.println();
                System.out.println("Winner " + winner + " : " + hand.getName());
                System.out.println("With the winning hand: ");
                System.out.println(eval.handResult);
                winner++;
            }
        }
    }

    public void playAgain() {
        boolean cont = true;
        while (cont) {
            System.out.println("___________________________________");
            System.out.println("Type \"q\" to exit");
            System.out.println("ENTER to continue");
            String answer = kb.nextLine();

            if (answer.equalsIgnoreCase("q")) {
                cont = false;
            } else {
                newGame();
            }
        }
    }

    public void welcomeMessage() {

        System.out.println("___________________________________");
        System.out.println("______Texas Hold'em Simulator______");
        System.out.println("__________Made by Axzter___________");
        System.out.println("_____________Nov-2018______________");
        System.out.println("___________________________________");
        System.out.println();
    }

    public void run() {
        welcomeMessage();
        newGame();
        playAgain();
    }

    public static void main(String[] args) {
        new TexasHoldEm().run();
    }

}

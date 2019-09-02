package com.diwayou.acm.team;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * https://leetcode-cn.com/problems/reveal-cards-in-increasing-order/
 */
public class RevealCardsInIncreasingOrder {
    public static void main(String[] args) {
        int[] deck = new int[] {1,2,3,4,5,6,7,8};

        System.out.println(Arrays.toString(new RevealCardsInIncreasingOrder().deckRevealedIncreasing(deck)));
    }

    public int[] deckRevealedIncreasing(int[] deck) {
        Arrays.sort(deck);
        if (deck.length <= 2) {
            return deck;
        }

        Deque<Integer> queue = new ArrayDeque<>(deck.length);
        queue.addLast(deck[deck.length - 1]);
        queue.addFirst(deck[deck.length - 2]);
        for (int i = deck.length - 3; i >= 0; i--) {
            queue.addFirst(queue.removeLast());
            queue.addFirst(deck[i]);
        }

        int i = 0;
        for (Integer ii : queue) {
            deck[i++] = ii;
        }

        return deck;
    }
}

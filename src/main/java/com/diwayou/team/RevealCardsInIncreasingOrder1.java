package com.diwayou.team;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/reveal-cards-in-increasing-order/
 */
public class RevealCardsInIncreasingOrder1 {
    public static void main(String[] args) {
        int[] deck = new int[]{1, 2, 3};

        System.out.println(Arrays.toString(new RevealCardsInIncreasingOrder1().deckRevealedIncreasing(deck)));
    }

    public int[] deckRevealedIncreasing(int[] deck) {
        Arrays.sort(deck);
        if (deck.length <= 2) {
            return deck;
        }

        int[] queue = new int[deck.length];
        int end = deck.length - 1;
        int head = end - 1;
        int tail = end;

        queue[tail] = deck[end];
        queue[head] = deck[end - 1];

        for (int i = deck.length - 3; i >= 0; --i) {
            head = head == 0 ? end : --head;
            queue[head] = queue[tail];
            head = head == 0 ? end : --head;
            queue[head] = deck[i];

            tail = tail == 0 ? end : --tail;
        }

        System.arraycopy(queue, head, deck, 0, deck.length - head);
        System.arraycopy(queue, 0, deck, deck.length - head, tail + 1);

        return deck;
    }
}

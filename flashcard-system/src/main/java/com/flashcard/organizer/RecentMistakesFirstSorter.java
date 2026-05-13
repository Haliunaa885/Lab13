// RecentMistakesFirstSorter.java
package com.flashcard.organizer;

import com.flashcard.model.Card;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RecentMistakesFirstSorter implements CardOrganizer {

    @Override
    public List<Card> organize(List<Card> cards) {

        List<Card> sorted = new ArrayList<>(cards);

        // хамгийн сүүлд алдсан card хамгийн эхэнд
        sorted.sort(
                Comparator.comparingLong(Card::getLastWrongOrder)
                        .reversed()
        );

        return sorted;
    }
}
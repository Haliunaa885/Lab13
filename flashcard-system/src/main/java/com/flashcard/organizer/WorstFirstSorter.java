package com.flashcard.organizer;

import com.flashcard.model.Card;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Хамгийн буруу хариулсан картуудыг эхэнд гаргадаг эрэмбэлэгч.
 * Зөв хариулсан хувь хамгийн бага картуудыг эхэнд байрлуулна.
 */
public class WorstFirstSorter implements CardOrganizer {

    /**
     * Картуудыг зөв хариулсан хувиар эрэмбэлнэ (багаас ихрүү).
     *
     * @param cards эрэмбэлэх картуудын жагсаалт
     * @return эрэмбэлэгдсэн жагсаалт
     */
    @Override
    public List<Card> organize(final List<Card> cards) {
        List<Card> sorted = new ArrayList<>(cards);
        sorted.sort(Comparator.comparingDouble(this::successRate));
        return sorted;
    }

    /**
     * Картын зөв хариулсан хувийг тооцоолно.
     *
     * @param card карт
     * @return зөв хариулсан хувь (0.0 ~ 1.0)
     */
    private double successRate(final Card card) {
        if (card.getWrongCount() == 0) {
            return 0.0;
        }
        return (double) card.getCorrectCount() / card.getWrongCount();
    }
}
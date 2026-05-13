package com.flashcard.achievement;

import com.flashcard.model.Card;
import java.util.List;

public class AchievementTracker {
    public static void checkCorrectAll(List<Card> cards, int round) {
        boolean allCorrect = cards.stream().allMatch(c -> !c.isLastWasWrong());
        if (allCorrect) {
            System.out.println("ACHIEVEMENT UNLOCKED: CORRECT - Buh cardad zuv hariulsan!");
        }
    }

    public static void checkRepeat(Card card, int totalAttemptsOnCard) {
        if (totalAttemptsOnCard > 5) {
            System.out.println("ACHIEVEMENT UNLOCKED: REPEAT - Neg cardad 5-aas olon udaa hariulsan!");
        }
    }

    public static void checkConfident(Card card) {
        if (card.getCorrectCount() >= 3) {
            System.out.println("ACHIEVEMENT UNLOCKED: CONFIDENT - Neg cardad gurvan udaa zuv hariulsan!");
        }
    }
}
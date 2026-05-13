package com.flashcard.organizer;

import com.flashcard.model.Card;
import java.util.List;

public interface CardOrganizer {
    List<Card> organize(List<Card> cards);
}
 
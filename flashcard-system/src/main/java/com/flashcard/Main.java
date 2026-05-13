package com.flashcard;

import com.flashcard.cli.CommandLineParser;
import com.flashcard.model.Card;
import com.flashcard.organizer.*;
import com.flashcard.achievement.AchievementTracker;

import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) throws Exception {

        CommandLineParser cli = CommandLineParser.parse(args);

        List<Card> cards = new ArrayList<>();

        BufferedReader fileReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(cli.getCardsFile()),
                        StandardCharsets.UTF_8
                )
        );

        String line;

        while ((line = fileReader.readLine()) != null) {

            String[] parts = line.split(";");

            if (parts.length == 2) {

                cards.add(
                        new Card(
                                parts[0].trim(),
                                parts[1].trim()
                        )
                );
            }
        }

        fileReader.close();

        Scanner scanner =
                new Scanner(System.in, StandardCharsets.UTF_8);

        int repetitions = cli.getRepetitions();

        // card бүрийн зөв хариулсан count
        Map<Card, Integer> correctCount =
                new LinkedHashMap<>();

        // нийт хэд оролдсон
        Map<Card, Integer> totalAttempts =
                new HashMap<>();

        for (Card c : cards) {

            correctCount.put(c, 0);

            totalAttempts.put(c, 0);
        }

        // алдааны дараалал
        long wrongSequence = 0;

        System.out.println(
                "Zorilgo: Buh cardiig "
                        + repetitions
                        + " udaa zuv hariulah\n"
        );

        boolean allDone = false;

        while (!allDone) {

            CardOrganizer organizer;

            switch (cli.getOrder()) {

                case "recent-mistakes-first":
                    organizer = new RecentMistakesFirstSorter();
                    break;

                case "worst-first":
                    organizer = new WorstFirstSorter();
                    break;

                default:
                    organizer = new RandomSorter();
                    break;
            }

            // дуусаагүй card-ууд
            List<Card> remaining = new ArrayList<>();

            for (Card c : cards) {

                if (correctCount.get(c) < repetitions) {
                    remaining.add(c);
                }
            }

            // эрэмбэлэх
            List<Card> sortedCards =
                    organizer.organize(remaining);

            for (Card c : sortedCards) {

                String question =
                        cli.isInvertCards()
                                ? c.getAnswer()
                                : c.getQuestion();

                String answer =
                        cli.isInvertCards()
                                ? c.getQuestion()
                                : c.getAnswer();

                int done = correctCount.get(c);

                System.out.println(
                        "\nAsuult: "
                                + question
                                + " ("
                                + done
                                + "/"
                                + repetitions
                                + ")"
                );

                System.out.print("Tanii hariult: ");

                String userAnswer =
                        scanner.nextLine().trim();

                // нийт attempt
                totalAttempts.put(
                        c,
                        totalAttempts.get(c) + 1
                );

                if (userAnswer.equalsIgnoreCase(answer)) {

                    int newCount =
                            correctCount.get(c) + 1;

                    correctCount.put(c, newCount);

                    c.setLastWasWrong(false);

                    c.incrementCorrect();

                    AchievementTracker.checkConfident(c);

                    if (newCount >= repetitions) {

                       
                      

                    } else {

                        System.out.println(
                                "Zuv! ("
                                        + newCount
                                        + "/"
                                        + repetitions
                                        + ")"
                        );
                    }

                } else {

                    System.out.println(
                            "Buruu! Zuv hariult: "
                                    + answer
                    );

                    // reset
                    correctCount.put(c, 0);

                    wrongSequence++;

                    c.setLastWasWrong(true);

                    c.setLastWrongOrder(wrongSequence);

                    c.incrementWrong();

                    AchievementTracker.checkRepeat(
                            c,
                            totalAttempts.get(c)
                    );

                    System.out.println(
                            "Tooluur reset! (0/"
                                    + repetitions
                                    + ")"
                    );
                }
            }

            // achievement шалгах
            AchievementTracker.checkCorrectAll(cards, 0);

            // бүгд дууссан эсэх
            allDone = true;

            for (Card c : cards) {

                if (correctCount.get(c) < repetitions) {

                    allDone = false;
                    break;
                }
            }

            if (!allDone) {

                long doneCount =
                        correctCount.values()
                                .stream()
                                .filter(v -> v >= repetitions)
                                .count();

                System.out.println(
                        "\n--- Yavts: "
                                + doneCount
                                + "/"
                                + cards.size()
                                + " card duussan ---"
                );
            }
        }


        scanner.close();
    }
}
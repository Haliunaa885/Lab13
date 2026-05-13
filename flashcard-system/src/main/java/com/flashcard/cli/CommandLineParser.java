package com.flashcard.cli;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "flashcard", description = "Flashcard learning system")
public class CommandLineParser implements Callable<Integer> {
    @Parameters(index = "0", description = "Cards file path")
    private String cardsFile;

    @Option(names = "--help", usageHelp = true, description = "Show help message")
    private boolean help;

    @Option(names = "--order", defaultValue = "random", description = "Order: random, worst-first, recent-mistakes-first")
    private String order;

    @Option(names = "--repetitions", defaultValue = "1", description = "How many correct answers required per card")
    private int repetitions;

    @Option(names = "--invertCards", defaultValue = "false", description = "Swap question and answer")
    private boolean invertCards;

    @Override
    public Integer call() throws Exception {
        if (help) {
            CommandLine.usage(this, System.out);
            return 0;
        }
        System.out.println("Cards file: " + cardsFile);
        System.out.println("Order: " + order);
        System.out.println("Repetitions: " + repetitions);
        System.out.println("Invert cards: " + invertCards);
        // Энд цааш үндсэн логик дуудагдана
        return 0;
    }

    public static CommandLineParser parse(String[] args) {
        CommandLineParser parser = new CommandLineParser();
        CommandLine cmd = new CommandLine(parser);
        try {
            cmd.parseArgs(args);
        } catch (Exception e) {
            cmd.usage(System.out);
            System.exit(1);
        }
        return parser;
    }

    public String getCardsFile() { return cardsFile; }
    public String getOrder() { return order; }
    public int getRepetitions() { return repetitions; }
    public boolean isInvertCards() { return invertCards; }
}
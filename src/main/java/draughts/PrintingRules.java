package main.java.draughts;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PrintingRules {

    public static void showRulesForPlayers() {
        String filePath = "/resources/game_rules.txt";
        BufferedReader fileReader = null;
        try {
            List<String> file = Files.readAllLines(Paths.get("resources\\game_rules.txt"));
            file.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

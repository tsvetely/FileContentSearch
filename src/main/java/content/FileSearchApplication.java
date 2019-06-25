package content;

import java.io.File;
import java.util.Scanner;

public class FileSearchApplication {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        StringBuilder directory = new StringBuilder();
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ' && input.charAt(i + 1) == '"') {
                for (int j = i+1; j < input.length(); j++) {
                    text.append(input.charAt(j));
                    i = j;
                }
            } else {
                directory.append(input.charAt(i));
            }
        }

        SearchFile search = new SearchFile();
        search.findFile(text.toString(), new File(directory.toString()));
        search.printResultsFromSearch();
        scanner.close();
    }
}

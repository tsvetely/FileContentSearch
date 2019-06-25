package content;

import java.io.File;

public interface Search {
    void findFile(String text, File file);
    boolean isZipFile(String fileName);
    void readZipFiles(String fileName, String searchText);
    void printResultsFromSearch();
}

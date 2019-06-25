package content;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class SearchFile implements Search {

    private static ArrayList<File> filesToPrint = new ArrayList<File>();

    @Override
    public void findFile(String s, File file) {
        File[] list = file.listFiles();// make array of all files in directory
        if (list != null) {
            for (File fileNew : list) {// for each files in list
                if (fileNew.isDirectory()) {// check if file is directory go into directory
                    findFile(s, fileNew);
                } else {
                    if (isZipFile(fileNew.getAbsolutePath())) {// check if file is zip
                        readZipFiles(fileNew.getAbsolutePath(), s);// method for read zip files
                    } else {
                        Scanner scanner = null;
                        File fileForRead;
                        try {
                            fileForRead = new File(fileNew.getAbsolutePath());
                            scanner = new Scanner(fileForRead);
                            while (scanner.hasNext()) {// read file with scanner
                                String line = scanner.nextLine();
                                if (line.contains(s)) {
                                    filesToPrint.add(fileForRead);
                                    break;
                                }
                            }
                        } catch (FileNotFoundException e) {
                            System.out.println(e.getMessage());
                        } finally {
                            if (scanner != null) {
                                scanner.close();
                            }


                        }
                    }
                }
            }
        }
    }


    @Override
    public void printResultsFromSearch() {
        // print result from search
        System.out.println("Results: ");
        // print message for empty results
        if (filesToPrint.size() == 0) {
            System.out.println("No such results!");
            return;
        }
        // print results
        for (File file : filesToPrint) {
            System.out.println(file);
        }
    }

    @Override
    public boolean isZipFile(String fileName) {
        return fileName.endsWith(".zip");
    }

    @Override
    public void readZipFiles(String fileName, String searchText) {
        ZipFile zipFile = null;
        Scanner scanner = null;
        try {
            zipFile = new ZipFile(fileName);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                File file = new File(entry.getName());
                InputStream stream = zipFile.getInputStream(entry);
                scanner = new Scanner(stream);
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (line.contains(searchText)) {
                        filesToPrint.add(file);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (scanner != null) {
                scanner.close();
            }
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

}

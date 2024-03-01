package pageprocessor;

import utiltypes.Page;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class fetch the page document data from a mentioned file name
 */
public class PageDataLoader {
    /**
     * This method list out all the pages with their contents and data from the mentioned file name
     * @param filename This is the file name from which page data will be fetched
     * @return It returns a list of all pages present in the file name
     */
    public static List<Page> fetchPages(String filename){
        List<Page> allPages = new ArrayList<>();
        try {
//            var filename = Files.readString(Paths.get("config.txt")).strip();
            List<String> lines = Files.readAllLines(Paths.get(filename));
            var lastIndex = lines.size();
            for (var i = lines.size() - 1; i >= 0; --i) {
                if (lines.get(i).startsWith("*PAGE")) {
                    Page newPage = new Page();
                    List<String> contents = lines.subList(i, lastIndex);
                    // It is checking the validity of the page to be enlisted
                    if(contents.size() > 2){
                        String[] output = contents.get(0).split("PAGE:");
                        newPage.setUrl(contents.get(0).split("PAGE:")[1]);
                        newPage.setTitle(contents.get(1));
                        newPage.setContents(contents.subList(2,contents.size()));
                        allPages.add(newPage);
                    }
                    lastIndex = i;
                }
            }
            Collections.reverse(allPages);
            return allPages;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Page fetching done");
        return null;
    }

}

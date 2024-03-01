package pageprocessor;

import utiltypes.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class facilitate converting all the pages' content words to inverse word to page list
 */
public class PageInvertIdxConverter {
    /**
     * This method convert all the pages' content of word to an inverse word to page list
     * @param allPageData It takes a list of all the pages in a document with their data and content of words
     * @return It returns a map containing word to page list where that particular word can be found
     */
    public static Map<String, List<Page>> invertPageIdx(List<Page> allPageData){
        Map<String, List<Page>> resultIdx = new HashMap<>();
        for(var page: allPageData){
            for(var word: page.getContents()){
                if(!resultIdx.containsKey(word)){
                    resultIdx.put(word, new ArrayList<>());
                }
                resultIdx.get(word).add(page);
            }
        }

        return resultIdx;
    }
}

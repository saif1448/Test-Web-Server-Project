package utils;

import pageprocessor.PageRanker;
import searchengine.WebServer;
import utiltypes.Page;

import java.util.*;

public class QuerySearch {

    /**
     * This class handles the finding matching pages based on the search query
     */
    private final PageRanker ranker;

    /**
     * It is the constructor to initiate search query class
     * @param ranker This takes a PageRanker object to rank the matched pages
     */
    public QuerySearch(PageRanker ranker){
        this.ranker = ranker;
    }

    /**
     * This method performs basic linear search to find matched pages based on the string query.
     * @param searchTerm It is the string query based on the matched page finding is done
     * @return It returns a list of pages based on the basic search
     */
    public  List<Page> basicSearch(String searchTerm){
        var result = new ArrayList<Page>();
        for (var page : WebServer.allPageData) {
            if (page.getContents().contains(searchTerm)) {
                result.add(page);
            }
        }
        return result;
    }

    /**
     * This method uses inverse index of the words to pages to search a page. It searches based on a single word
     * @param searchTerm It is a string query composed of only a single word based on which pages are matched
     * @param allWordIdx It is inverse index of the word to pages
     * @return It returns pages based on the search query word
     */
    public  List<Page> searchWithSingleWord(String searchTerm, Map<String, List<Page>> allWordIdx){
        List<Page> matchedPages = new ArrayList<>();
        matchedPages = allWordIdx.get(searchTerm);
        return matchedPages;
    }

    /**
     * It fetches matched pages based on single query composed of multiple words
     * @param searchTerms It takes a string query composed of multiple words
     * @param allWordIdx It is inverse index of the word to pages
     * @return It returns pages based on the search query word
     */

    public  List<Page> searchWithSingleQuery(String searchTerms, Map<String, List<Page>> allWordIdx){

        String[] allSearchWords = searchTerms.split(" ");
        List<Page> resultPages;
        List<Page> fetchedPages = searchWithSingleWord(allSearchWords[0], allWordIdx);
        if(fetchedPages!= null){
            Set<Page> commonPages = new HashSet<>(searchWithSingleWord(allSearchWords[0], allWordIdx));
            for(int i=1; i < allSearchWords.length; i++){
                commonPages.retainAll(searchWithSingleWord(allSearchWords[i], allWordIdx));
            }
            resultPages = new ArrayList<>(commonPages);
        }
        else{
            resultPages = new ArrayList<>();
        }

        return resultPages;
    }


    /**
     * It performs search based on a full query where multiple queries can be seperated with 'OR'
     * @param searchTerms It is string query which can be a single word or composed of multiple words or composed multiple queries separted by 'OR'
     * @param allWordIdx It is inverse index of the word to pages
     * @return It returns pages based on the search query word
     */
    public  List<Page> searchWithFullQueryWithOrLogic(String searchTerms, Map<String, List<Page>> allWordIdx){

        String[] allQueries = searchTerms.split("OR");
        for(int i=0; i<allQueries.length; i++){
            allQueries[i] = allQueries[i].trim().toLowerCase();
        }

        List<Page> resultPages = new ArrayList<>();
        for(var query: allQueries){
//            query = query.trim().toLowerCase();
            resultPages.addAll(searchWithSingleQuery(query, allWordIdx));
        }

        Set<Page> uniqueMatchedPages = new HashSet<>(resultPages);
        resultPages = new ArrayList<>(uniqueMatchedPages);

//        Here we are ranking the pages
        for (Page page : resultPages) {
            double rank = ranker.getPageRank(allQueries, page);
            page.setRank(rank);
        }

        //Sorting the pages based on their ranks
        resultPages.sort((p1, p2) -> Double.compare(p2.getRank(), p1.getRank()));


        for(Page page : resultPages){
            System.out.println(page.getTitle() + " " + page.getRank());
        }
        return resultPages;
    }
}

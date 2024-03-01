package pageprocessor;

import searchengine.WebServer;
import utiltypes.Page;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is a Page Ranker class which assigns rank to a particular page based on TF-IDF value
 */
public class TfIdfPageRanker extends PageRanker{

    Map<String, Double> wordIdfValue;

    /**
     * The constructor initially calculate all the IDF value of the word in all the documents
     */
    public TfIdfPageRanker(List<Page> allPageData, Map<String, List<Page>> allWordIdx){
        calculateAndSetIdfValue(allPageData, allWordIdx);
    }

    /**
     * This method calculates the IDF value and save it
     */
    private void calculateAndSetIdfValue(List<Page> allPageData, Map<String, List<Page>> allWordIdx){
//        int numberOfDocument = WebServer.allPageData.size();
//        Map<String, List<Page>> allWordIdx = WebServer.allWordIdx;

        int numberOfDocument = allPageData.size();

         wordIdfValue = new HashMap<>();

        for(var word : allWordIdx.keySet()){
            int size = allWordIdx.get(word).size();
            //Checking if a word is present in every document
            //if present the log value would be negative infinity, so IDF should be equal to 0
            //otherwise assigning calculated IDF value
            double idfVal = Math.log(numberOfDocument/size)!=Double.NEGATIVE_INFINITY?Math.log(numberOfDocument/size):0;

            wordIdfValue.put(word, idfVal);
        }
//        wordIdfValue =  wordIdfValue;
//        System.out.println(wordIdfValue);
    }

    /**
     * This method calculate the TF value for a single word for a particular page document
     * @param targetWord This is the mentioned word for which TF is calculated
     * @param singleMatchedPageDocument This is the Page document for which TF is calculated for a given word
     * @return It returns the TF value for the word for the particular page
     */
    private double calculateTfForSingleWord(String targetWord, Page singleMatchedPageDocument){
        int frequency = Collections.frequency(singleMatchedPageDocument.getContents(), targetWord);
        int totalWords = singleMatchedPageDocument.getContents().size();
        double res;
        res = (double) frequency/totalWords;
        return res;
    }

    /**
     * It calculates the TF-IDF value for all the queries for a particular Page document
     * @param allQueries It is an array of all the queries
     * @param singleMatchedPageDocument It is the page for which TF-IDF value is calculated
     * @return It returns the TF-IDF value for that particular page
     */
    private double calculateTfIdfRank(String[] allQueries, Page singleMatchedPageDocument){
        double maxRank = 0.0;
        for(String query: allQueries){
            query = query.trim();
            double tfIdfValue = 0.0;
            String[] queryWords = query.split(" ");

            for(String word : queryWords){
                double tfValue = calculateTfForSingleWord(word, singleMatchedPageDocument);
                double idfValue = wordIdfValue.getOrDefault(word, 0.0);
                tfIdfValue += tfValue*idfValue;
            }

            if(tfIdfValue > maxRank){
                maxRank = tfIdfValue;
            }
        }
//        System.out.println(maxRank);
        return maxRank;
    }

    /**
     * It calculates the TF-IDF value for all the queries for a particular Page document
     * @param allQueries It is an array of all the queries
     * @param singleMatchedPageDocument It is the page for which TF-IDF value is calculated
     * @return It returns the TF-IDF value for that particular page
     */
    public double getPageRank(String[] allQueries, Page singleMatchedPageDocument){
        return  calculateTfIdfRank(allQueries,singleMatchedPageDocument);
    }
}

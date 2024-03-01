package pageprocessor;

import utiltypes.Page;

/**
 * It is an abstract class to create a Page Ranker object
 */
public abstract class PageRanker {
    /**
     * This method returns the rank of the page
     * @param allQueries
     * @param singleMatchedPageDocument
     * @return
     */
    public abstract double getPageRank(String[] allQueries, Page singleMatchedPageDocument);

}

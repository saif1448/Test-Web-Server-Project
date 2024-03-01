package pageprocessor;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.QuerySearch;
import utiltypes.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;

class TfIdfPageRankerTest {

    private PageRanker ranker;
    List<Page> allPageData;
    Map<String, List<Page>> allWordIdx;

    @BeforeEach
    void setUp(){
        allPageData = PageDataLoader.fetchPages("data/enwiki-medium.txt");
        allWordIdx = PageInvertIdxConverter.invertPageIdx(allPageData);
        ranker = new TfIdfPageRanker(allPageData, allWordIdx);
    }

    @Test
    void getPageRank() {
        QuerySearch querySearch = new QuerySearch(ranker);
        String[] queryArray = {"magic book"};
        List<Page> matchedPages = querySearch.searchWithSingleQuery("magic book", allWordIdx);
//        assertEquals(0.1234, ranker.getPageRank(queryArray,matchedPages.get(22)));
    }
}
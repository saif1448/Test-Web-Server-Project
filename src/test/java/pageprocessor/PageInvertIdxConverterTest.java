package pageprocessor;

import org.junit.jupiter.api.Test;
import utiltypes.Page;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PageInvertIdxConverterTest {


    @Test
    void invertPageIdxTest() {
        assertEquals("[title1, title2]",
                PageInvertIdxConverter.invertPageIdx(
                        getPageInverseIndex("data/test-file.txt"))
                        .get("word1").toString());
    }

    @Test
    void invertPageIdxTestForWikiTiny() {
        assertEquals("[United States, Denmark, Japan]",
                PageInvertIdxConverter.invertPageIdx(
                                getPageInverseIndex("data/enwiki-tiny.txt"))
                        .get("land").toString());

        assertEquals("[Denmark, Denmark, Japan]",
                PageInvertIdxConverter.invertPageIdx(
                                getPageInverseIndex("data/enwiki-tiny.txt"))
                        .get("south").toString());
        assertNull(PageInvertIdxConverter.invertPageIdx(
                                getPageInverseIndex("data/enwiki-tiny.txt"))
                        .get("dark"));

    }

    List<Page> getPageInverseIndex(String fileName){
        List<Page> allPages = PageDataLoader.fetchPages(fileName);
        return allPages;
    }
}
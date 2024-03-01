package pageprocessor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PageDataLoaderTest {

    @Test
    void fetchPagesForTestFile() {
        assertEquals(2,PageDataLoader.fetchPages("data/test-file.txt").size());
    }

    @Test
    void fetchPagesForWikiTiny() {
        assertEquals(6,PageDataLoader.fetchPages("data/enwiki-tiny.txt").size());
    }
    @Test
    void fetchPagesForWikiSmall() {
        assertEquals(1033,PageDataLoader.fetchPages("data/enwiki-small.txt").size());
    }
}
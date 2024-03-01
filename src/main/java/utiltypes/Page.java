package utiltypes;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent a web document page
 */
public class Page {
    private String url;
    private String title;
    private List<String> contents;
    private double rank;

    public Page() {
        url= "";
        title = "";
        contents = new ArrayList<>();
        rank = 0.0;
    }

    /**
     *
     * @return It returns the URL of the Page
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url It sets the URL of the Page
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return It returns the title of the Page
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title It sets the title of the Page
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return it returns all the word contents in the Page
     */
    public List<String> getContents() {
        return contents;
    }

    /**
     *
     * @param contents It sets all the word contents withing the Page
     */
    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    /**
     *
     * @return It returns the rank of the Page
     */
    public double getRank() {
        return rank;
    }

    /**
     *
     * @param rank It sets the rank of the Page
     */
    public void setRank(double rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return title;
    }
}

package searchengine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import pageprocessor.PageDataLoader;
import pageprocessor.PageInvertIdxConverter;
import pageprocessor.PageRanker;
import pageprocessor.TfIdfPageRanker;
import utils.QuerySearch;
import utiltypes.Page;

public class WebServer {
  private static final int PORT = 8080;
  private static final int BACKLOG = 0;
  private static final Charset CHARSET = StandardCharsets.UTF_8;

  public static List<Page> allPageData = new ArrayList<>();
  public static Map<String, List<Page>> allWordIdx = new HashMap<>();


  HttpServer server;

  public WebServer(int port, String filename) throws IOException {
    //Loading all page data at the beginning
    allPageData = PageDataLoader.fetchPages(filename);
    allWordIdx = PageInvertIdxConverter.invertPageIdx(allPageData);
//    wordIdfValues = PageRanker.calculateIdfValue(allPageData.size(), allWordIdx);
    
    server = HttpServer.create(new InetSocketAddress(port), BACKLOG);
    server.createContext("/", io ->   respond(io, 200, "text/html", getFile("web/index.html")));
    server.createContext("/search", io -> search(io));
    server.createContext(
        "/favicon.ico", io -> respond(io, 200, "image/x-icon", getFile("web/favicon.ico")));
    server.createContext(
        "/code.js", io -> respond(io, 200, "application/javascript", getFile("web/code.js")));
    server.createContext(
        "/style.css", io -> respond(io, 200, "text/css", getFile("web/style.css")));
    server.start();
    String msg = " WebServer running on http://localhost:" + port + " ";
    System.out.println("╭"+"─".repeat(msg.length())+"╮");
    System.out.println("│"+msg+"│");
    System.out.println("╰"+"─".repeat(msg.length())+"╯");
  }
  
  private void search(HttpExchange io) {
    var urlQuery = io.getRequestURI().getRawQuery().split("=")[1];
    var searchTerm = URLDecoder.decode(urlQuery);
    var response = new ArrayList<String>();
    PageRanker ranker = new TfIdfPageRanker(WebServer.allPageData, WebServer.allWordIdx);
    QuerySearch querySearch = new QuerySearch(ranker);
    List<Page> matchedPages = querySearch.searchWithFullQueryWithOrLogic(searchTerm, allWordIdx);
    System.out.println(matchedPages.size());
    for (var page : matchedPages) {
      response.add(String.format("{\"url\": \"%s\", \"title\": \"%s\"}",
              page.getUrl(), page.getTitle()));
    }

    var bytes = response.toString().getBytes(CHARSET);
    respond(io, 200, "application/json", bytes);
  }

  private byte[] getFile(String filename) {
    try {
      return Files.readAllBytes(Paths.get(filename));
    } catch (IOException e) {
      e.printStackTrace();
      return new byte[0];
    }
  }

  private void respond(HttpExchange io, int code, String mime, byte[] response) {
    try {
      io.getResponseHeaders()
          .set("Content-Type", String.format("%s; charset=%s", mime, CHARSET.name()));
      io.sendResponseHeaders(200, response.length);
      io.getResponseBody().write(response);
    } catch (Exception e) {
    } finally {
      io.close();
    }
  }

  public static void main(final String... args) throws IOException {
    var filename = Files.readString(Paths.get("config.txt")).strip();
    new WebServer(PORT, filename);
  }
}
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class MyWebCrawler extends WebCrawler {

    private static int pageNumberCounter = 0;
    private static int visitedNumberCounter = 0;
    protected static final List<String> supportedContentTypes = new ArrayList<>(Arrays.asList("text/html", "application/msword", "text/pdf", "image/"));;

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
//        System.out.print("# Crawled Pages: " + ++pageNumberCounter);

        String loweredUrl = url.getURL().toLowerCase();
        boolean isUnderSameDomain =  loweredUrl.startsWith("http://www.nbcnews.com/") ||
                loweredUrl.startsWith("https://www.nbcnews.com/") ||
                loweredUrl.startsWith("http://nbcnews.com/") ||
                loweredUrl.startsWith("https://nbcnews.com/");
        if (!isUnderSameDomain) return false;

        for (String contentType : supportedContentTypes) {
            if (referringPage.getContentType() == null) break;
            if (referringPage.getContentType().contains(contentType)) return true;
        }

        return false;
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {
        printPageInfo(page);
        System.out.println("# Visited: " + ++visitedNumberCounter);
    }

    private void printPageInfo(Page page) {
//        String url = page.getWebURL().getURL();
//        System.out.println("URL: " + url);
//
//        if (page.getParseData() instanceof HtmlParseData) {
//            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
//            String text = htmlParseData.getText();
//            String html = htmlParseData.getHtml();
//            Set<WebURL> links = htmlParseData.getOutgoingUrls();
//
//            System.out.println("Text length: " + text.length());
//            System.out.println("Html length: " + html.length());
//            System.out.println("Number of outgoing links: " + links.size());
//
//        }
    }
}

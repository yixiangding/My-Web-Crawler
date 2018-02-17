import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

public class VisitCrawler extends MyWebCrawler {
    @Override
    public void visit(Page page) {
        CSVWriter write = CSVWriter.getInstance("visit_NBC_News.txt", true);
        String[] visitedPageInfo = new String[4];
        visitedPageInfo[0] = page.getWebURL().getURL();
        visitedPageInfo[1] = page.getStatusCode() + "";
        visitedPageInfo[2] = page.getParseData().getOutgoingUrls().size() + "";
        String contentType = page.getContentType();
        // Trim off useless part like "charset=utf-8"
        if (contentType.contains(";")) {
            contentType = contentType.substring(0, contentType.indexOf(";"));
        }
        visitedPageInfo[3] = contentType;
        write.writeLine(visitedPageInfo);
    }
}

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

public class VisitCrawler extends MyWebCrawler {
    @Override
    public void visit(Page page) {
        if (page.getContentType().contains("application/") ||
                page.getContentType().contains("css") ||
                page.getContentType().contains("javascript")) return;
        CSVWriter writer = CSVWriter.getInstance("visit_NBC_News.csv", true);
        String[] visitedPageInfo = populatePageInfo(page);
        writer.writeLine(visitedPageInfo);

        super.visit(page); // Print visit info
    }

    private String[] populatePageInfo(Page page) {
        String[] visitedPageInfo = new String[4];
        visitedPageInfo[0] = page.getWebURL().getURL();
        visitedPageInfo[1] = page.getContentData().length / 1024 + " kb";
        visitedPageInfo[2] = page.getParseData().getOutgoingUrls().size() + "";
        String contentType = page.getContentType();
        // Trim off useless part like "charset=utf-8"
        if (contentType.contains(";")) {
            contentType = contentType.substring(0, contentType.indexOf(";"));
        }
        visitedPageInfo[3] = contentType;

        return visitedPageInfo;
    }
}

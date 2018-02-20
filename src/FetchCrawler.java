import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.*;
import java.util.regex.Pattern;

public class FetchCrawler extends MyWebCrawler {

    @Override
    protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
        CSVWriter writer = CSVWriter.getInstance("fetch_NBC_News.csv", true);
        // Write required info into result file
        String[] urlAndCode = new String[2];
        urlAndCode[0] = webUrl.getURL();
        urlAndCode[1] = statusCode + "";
        writer.writeLine(urlAndCode);
    }

}

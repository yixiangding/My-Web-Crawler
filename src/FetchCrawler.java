import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.*;
import java.util.regex.Pattern;

public class FetchCrawler extends MyWebCrawler {

    @Override
    protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
        CSVWriter write = CSVWriter.getInstance("fetch_NBC_News.txt", true);
        String[] urlAndCode = new String[2];
        urlAndCode[0] = webUrl.getURL();
        urlAndCode[1] = statusCode + "";
        write.writeLine(urlAndCode);
    }

}

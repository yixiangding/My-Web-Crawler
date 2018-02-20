import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

public class UrlsCrawler extends MyWebCrawler {
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        CSVWriter writer = CSVWriter.getInstance("urls_NBC_News.csv", true);

        // Write required info into result file
        String[] urlAndResidency = new String[2];
        String loweredUrl = url.getURL().toLowerCase();
        urlAndResidency[0] = loweredUrl;
        boolean underSameDomain =  loweredUrl.startsWith("http://www.nbcnews.com/") || loweredUrl.startsWith("https://www.nbcnews.com/");
        urlAndResidency[1] = underSameDomain ? "OK" : "N_OK";
        writer.writeLine(urlAndResidency);

        return super.shouldVisit(referringPage, url);
    }
}

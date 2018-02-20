import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class UnitTester {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("results/fetch_NBC_News.csv"));
        int count = 0;
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(++count);
            System.out.println(line);
        }
    }
}

import java.io.*;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class CrawlReporter {
    /*
    Name: Tommy Trojan
    USC ID: 1234567890
    News site crawled: newsday.com
    Fetch Statistics ================
    # fetches attempted: # fetches succeeded: # fetches aborted: # fetches failed:
    Outgoing URLs:
    ==============
    Total URLs extracted:
    # unique URLs extracted:
    # unique URLs within News Site:
    # unique URLs outside News Site:
    Status Codes:
    =============
    200 OK:
    301 Moved Permanently:
    401 Unauthorized:
    403 Forbidden:
    404 Not Found:
    File Sizes:
    ===========
    < 1KB:
    1KB ~ <10KB:
    10KB ~ <100KB:
    100KB ~ <1MB:
    >= 1MB:
    Content Types:
    ==============
    text/html:
    image/gif:
    image/jpeg:
    image/png:
    application/pdf:
     */
    private static final String AUTHOR_NAME = "Yixiang Ding";
    private static final String USC_ID = "1068351563";
    private static final String WEBSITE_CRAWLER = "nbcnews.com";
    private static final String REPORT_PATH = "results/CrawlReport_NBC_News.txt";
    private static final String FETCH_RESULT_PATH = "results/fetch_NBC_News.csv";
    private static final String VISIT_RESULT_PATH = "results/visit_NBC_News.csv";
    private static final String URLS_RESULT_PATH = "results/urls_NBC_News.csv";

    private FileWriter reportWriter = null;

    private int[] fetchStats = new int[3]; // attempt, succeed, failed/aborted
    private int[] urlStats = new int[4]; // total, # unique, # within, # outside
    private HashMap<String, Integer> statusCodeStats = new HashMap<>(); // statusCode => #
    private int[] fileSizeStats = new int[5]; // < 1KB, 1~10KB, 10~100KB, 100KB~1MB, >= 1MB
    private HashMap<String, Integer> contentTypeStats = new HashMap<>(); // contentType => #
    private HashMap<String, String> statusCodeName = new HashMap<>(); // 200 => OK etc.

    public void writeReport() {
        reportWriter = generateReportWriter();
        initStatusCodeNameMap();
        writeReportHeader();
        calculateStats();
        writeContent();
        flushAndClose();
    }

    private FileWriter generateReportWriter() {
        FileWriter reportWriter = null;
        try {
            File file = new File(REPORT_PATH);
            if (!file.exists())
                file.createNewFile();
            reportWriter = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reportWriter;
    }

    private void initStatusCodeNameMap() {
        statusCodeName.put("200", "OK");
        statusCodeName.put("301", "Moved Permanently");
        statusCodeName.put("302", "Moved Temporarily");
        statusCodeName.put("401", "Unauthorized");
        statusCodeName.put("403", "Forbidden");
        statusCodeName.put("404", "Not Found");
        statusCodeName.put("410", "Gone");
        statusCodeName.put("500", "Internal Server Error");
        statusCodeName.put("503", "Service Unavailable");
        statusCodeName.put("504", "Gateway Timeout");
    }

    private void writeReportHeader() {
        writeLine("Name: " + AUTHOR_NAME);
        writeLine("USC ID: " + USC_ID);
        writeLine("News site crawled: " + WEBSITE_CRAWLER);
    }

    private void calculateStats() {
        try {
            calculateFetchStats();
            calculateVisitStats();
            calculateUrlsStats();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void calculateFetchStats() throws IOException {
        BufferedReader reader = getBufferedReader(FETCH_RESULT_PATH);
        String nextLine = reader.readLine();
        while (nextLine != null) {
            String[] lineParts = nextLine.split(",");
//            String statusCode = lineParts[1];
            String statusCode = lineParts[lineParts.length - 1];
            if (statusCode.length() == 3)
                addOneInMap(statusCodeStats, statusCode);

            fetchStats[0]++;
            if (statusCode.startsWith("2"))
                fetchStats[1]++;
            else
                fetchStats[2]++;

            nextLine = reader.readLine();
        }
        reader.close();
    }

    private void addOneInMap(HashMap<String, Integer> map, String key) {
        if (map.containsKey(key)) {
            map.put(key, map.get(key) + 1);
        } else {
            map.put(key, 1);
        }
    }

    private void calculateVisitStats() throws IOException {
        BufferedReader reader = getBufferedReader(VISIT_RESULT_PATH);
        String nextLine = reader.readLine();
        while (nextLine != null) {
            String[] lineParts = nextLine.split(",");

            String size = lineParts[1];
            int sizeValue = Integer.valueOf(size.substring(0, size.indexOf(" ")));
            calculateSizeStats(sizeValue);

            String contentType = lineParts[3];
            addOneInMap(contentTypeStats, contentType);

            nextLine = reader.readLine();
        }
        reader.close();
    }

    private void calculateSizeStats(int sizeValue) {
        if (sizeValue < 1) fileSizeStats[0]++;
        else if (sizeValue < 10) fileSizeStats[1]++;
        else if (sizeValue < 100) fileSizeStats[2]++;
        else if (sizeValue < 1024) fileSizeStats[3]++;
        else fileSizeStats[4]++;
    }

    private void calculateUrlsStats() throws IOException {
        BufferedReader reader = getBufferedReader(URLS_RESULT_PATH);
        HashSet<String> urlOccurred = new HashSet<>();
        String nextLine = reader.readLine();
        while (nextLine != null) {
            urlStats[0]++; // Total #

            String[] lineParts = nextLine.split(",");
            String url = lineParts[0];
            if (!urlOccurred.contains(url)) {
                urlStats[1]++; // # unique urls
                urlOccurred.add(url);
            }

            boolean isWithin = lineParts[1].equals("OK");
            if (isWithin)
                urlStats[2]++;
            else
                urlStats[3]++;

            nextLine = reader.readLine();
        }
        reader.close();
    }

    private BufferedReader getBufferedReader(String pathName) {
        BufferedReader br = null;
        try {
            FileReader fr = new FileReader(pathName);
            br = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return br;
    }

    private void writeContent() {
        writeSubtitle("Fetch Statistics:");
        writeLine("# fetches attempted: " + fetchStats[0]);
        writeLine("# fetches succeeded: " + fetchStats[1]);
        writeLine("# fetches aborted/failed: " + fetchStats[2]);

        writeSubtitle("Outgoing URLs:");
        writeLine("Total URLs extracted: " + urlStats[0]);
        writeLine("# unique URLs extracted: " + urlStats[1]);
        writeLine("# unique URLs within News Site: " + urlStats[2]);
        writeLine("# unique URLs outside News Site: " + urlStats[3]);

        writeSubtitle("Status Codes:");
        for (Map.Entry<String, Integer> statusCodeAndFreq : statusCodeStats.entrySet()) {
            String statusCode = statusCodeAndFreq.getKey();
            int frequency = statusCodeAndFreq.getValue();
            writeLine(statusCode + " " + statusCodeName.get(statusCode) + ": " + frequency);
        }

        writeSubtitle("File Sizes:");
        writeLine("< 1KB: " + fileSizeStats[0]);
        writeLine("1KB ~ <10KB: " + fileSizeStats[1]);
        writeLine("10KB ~ <100KB: " + fileSizeStats[2]);
        writeLine("100KB ~ <1MB: " + fileSizeStats[3]);
        writeLine(">= 1MB: " + fileSizeStats[4]);

        writeSubtitle("Content Types:");
        for (Map.Entry<String, Integer> contentTypeAndFreq : contentTypeStats.entrySet()) {
            writeLine(contentTypeAndFreq.getKey() + ": " + contentTypeAndFreq.getValue());
        }
    }

    private void writeSubtitle(String subtitle) {
        writeLine("");
        writeLine(subtitle);
        writeLine("================");
    }

    private void writeLine(String content) {
        try {
            reportWriter.write(content + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void flushAndClose() {
        try {
            reportWriter.flush();
            reportWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



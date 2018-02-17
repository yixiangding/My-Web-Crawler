import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter extends FileWriter{
    public static CSVWriter writer = null;

    private CSVWriter(String fileName, boolean append) throws IOException {
        super(fileName, append);
    }

    // Singleton pattern
    public static synchronized CSVWriter getInstance(String fileName, boolean append) {
        if (writer == null) {
            try {
                File file = new File("results/" + fileName);
                if (!file.exists()) {
                    file.createNewFile();
                }
                writer = new CSVWriter("results/" + fileName, append);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return writer;
    }

    public static synchronized CSVWriter getInstance() {
        if (writer == null) {
            try {
                writer = new CSVWriter("results/null.csv", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return writer;
    }

    public void writeLine(String[] values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            sb.append(values[i]);
            if (i != values.length - 1) sb.append(",");
        }
        sb.append("\n");
        try {
            write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

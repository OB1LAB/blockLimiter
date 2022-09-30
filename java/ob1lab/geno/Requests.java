package ob1lab.geno;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Requests {
    public static String get(String url) {
        try {
            URL address = new URL(url);
            URLConnection con = address.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("GET");
            http.setDoOutput(true);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();
            InputStream is = http.getInputStream();
            return new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
        } catch (IOException urlException) {
            urlException.printStackTrace();
            return "None";
        }
    }
    public static String post(String url, String data) {
        try {
            URL address = new URL(url);
            URLConnection con = address.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setDoInput(true);
            byte[] out = data.getBytes(StandardCharsets.UTF_8);
            http.setFixedLengthStreamingMode(out.length);
            http.setRequestProperty("user-agent", "Mozilla/5.0");
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
            }
            http.connect();
            InputStream is = http.getInputStream();
            return new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
        } catch (IOException urlException) {
            urlException.printStackTrace();
            return "None";
        }
    }
}

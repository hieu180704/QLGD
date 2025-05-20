package Api;

import java.io.*;
import java.net.*;
import java.util.logging.*;
import com.google.gson.Gson;

public class ApiClient {

    private static final Logger logger = Logger.getLogger(ApiClient.class.getName());
    private static final Gson gson = new Gson();

    // ==== CREATE (POST) ====
    public static String create(String apiUrl, String jsonBody) throws IOException {
        return sendRequest(apiUrl, "POST", jsonBody);
    }

    // ==== READ (GET) ====
    public static String read(String apiUrl) throws IOException {
        return sendRequest(apiUrl, "GET", null);
    }

    // ==== UPDATE (PUT) ====
    public static String update(String apiUrl, String jsonBody) throws IOException {
        return sendRequest(apiUrl, "PUT", jsonBody);
    }

    // ==== DELETE (DELETE) ====
    public static String delete(String apiUrl) throws IOException {
        return sendRequest(apiUrl, "DELETE", null);
    }

    // ==== COMMON METHOD: send HTTP request ====
    private static String sendRequest(String apiUrl, String method, String jsonBody) throws IOException {
        HttpURLConnection conn = null;
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(apiUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", "application/json");

            // Nếu là POST hoặc PUT, gửi dữ liệu JSON
            if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method)) {
                conn.setDoOutput(true);
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonBody.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
            }

            int code = conn.getResponseCode();

            InputStream is = (code >= 200 && code < 300) ? conn.getInputStream() : conn.getErrorStream();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "API request error: " + e.getMessage());
            throw e;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return response.toString();
    }

    // ==== JSON Parsing helper ====
    public static <T> T parseJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
}


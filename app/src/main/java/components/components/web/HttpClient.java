package components.web;

import android.util.Pair;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

class HttpClient extends HttpClientBase {

    private HttpURLConnection connection;

    public HttpClient(String url) throws MalformedURLException {
        super(url);
    }

    public String post(String body) throws IOException {
        //Create this.connection
        this.connection = (HttpURLConnection) this.url.openConnection();
        this.connection.setRequestMethod("POST");
        this.setHeaders();
        this.connection.setRequestProperty("Content-Length",
                Integer.toString(body.getBytes().length));

        this.connection.setUseCaches(false);
        this.connection.setDoOutput(true);

        //Send request
        DataOutputStream wr = new DataOutputStream (
                this.connection.getOutputStream());
        wr.writeBytes(body);
        wr.close();

        //Get Response
        InputStream is = this.connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        if (this.connection != null) {
            this.connection.disconnect();
        }
        return response.toString();
    }

    public String get(ArrayList<Pair<String, String>> queryParams) throws IOException {

        StringBuilder queryString = new StringBuilder("?");
        for (int i = 0; i < queryParams.size(); i++) {
            Pair<String, String> param = queryParams.get(i);
            queryString.append(param.first + "=" + param.second);
            if (i != queryParams.size() - 1) queryString.append("&");
        }
        this.url = new URL(this.url.toString() + queryString.toString());

        return this.get();
    }

    public String get() throws IOException {
        this.connection = (HttpURLConnection) this.url.openConnection();

        // optional default is GET
        this.connection.setRequestMethod("GET");

        //add request headers
        this.setHeaders();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(this.connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        return response.toString();
    }

    private void setHeaders() {
        Set<Map.Entry<String, String>> entries = this.headers.entrySet();
        for (Map.Entry<String, String> header : entries) {
            this.connection.setRequestProperty(header.getKey(), header.getValue());
        }
    }


}

package components.web;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public abstract class HttpClientBase implements IHttpClient {

    protected URL url;

    protected HashMap<String, String> headers;

    public HttpClientBase(String url) throws MalformedURLException {
        this.url = new URL(url);
        this.headers = new HashMap<>();
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

}

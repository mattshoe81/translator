package components.web;

import java.net.MalformedURLException;

public class WebFactory {
    public static IHttpClient newHttpClient(String url) throws MalformedURLException {
        return new HttpClient(url);
    }
}

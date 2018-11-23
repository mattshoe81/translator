package components.web;

public class WebFactory {
    public static IHttpClient newHttpClient(String url){
        return new HttpClient(url);
    }
}

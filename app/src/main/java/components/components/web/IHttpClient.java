package components.web;

import android.util.Pair;

import java.io.IOException;
import java.util.ArrayList;

public interface IHttpClient {
    String post(String body) throws IOException;

    String get(ArrayList<Pair<String, String>> queryString) throws IOException;

    String get() throws IOException;

    void addHeader(String key, String value);

}

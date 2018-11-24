package components.translation;

import android.util.Pair;

import com.google.api.client.json.JsonObjectParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import components.web.IHttpClient;
import components.web.WebFactory;

class GoogleTranslator implements ITranslator {

    public String translate(String text, Language from, Language to) {
        String response = "ERROR translating speech.";
        try {
            IHttpClient webClient = WebFactory.newHttpClient("https://translation.googleapis.com/language/translate/v2");
            webClient.addHeader("Content-Type", "application/json");
            ArrayList<Pair<String, String>> queryParams = new ArrayList<>();
            queryParams.add(new Pair<>("key", "AIzaSyBvA2MEM8B-2a1cv60gReF1tLyqyPIRZQw"));
            queryParams.add(new Pair<>("q", text));
            queryParams.add(new Pair<>("source", from.toString()));
            queryParams.add(new Pair<>("target", to.toString()));
            queryParams.add(new Pair<>("format", "text"));

            response = webClient.get(queryParams);

            JSONObject jsonObject = new JSONObject(response);
            JSONArray translations = jsonObject.getJSONObject("data").getJSONArray("translations");
            response = translations.getJSONObject(0).getString("translatedText");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }
}

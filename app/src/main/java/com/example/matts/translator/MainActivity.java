package com.example.matts.translator;

import android.content.Context;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import components.translation.ITranslator;
import components.translation.Language;
import components.translation.TranslationFactory;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;
import java.util.PriorityQueue;

import android.os.AsyncTask;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.text.TextRecognizer;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private final int REQ_CODE_SPEECH_INPUT = 100;
    private FloatingActionButton btnSpeak;
    private ImageButton speakBtn;
    private TextView txtSpeechInput;
    private Spinner sourceDropdown;
    private Spinner targetDropdown;
    public static String LOG_TAG = "CUSTOM-TAG-BY-ME";
    private Language source = Language.ENGLISH;
    private Language target = Language.ENGLISH;
    private String[] languages;
    private TextToSpeech tts;
    private CameraSource cameraSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtSpeechInput = findViewById(R.id.txtSpeechInput);
        btnSpeak = findViewById(R.id.btnSpeak);
        sourceDropdown = findViewById(R.id.sourceDropdown);
        targetDropdown = findViewById(R.id.targetDropdown);
        speakBtn = findViewById(R.id.speakBtn);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                }
            }
        });
        speakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                speak();
//                Toast.makeText(getApplicationContext(), "Speaking the words", Toast.LENGTH_LONG).show();
            }
        });
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (source != target) testInput();
                if (source != target) promptSpeechInput();
                else Toast.makeText(getApplicationContext(), "Please select different languages.", Toast.LENGTH_LONG).show();

            }
        });
        sourceDropdown.setOnItemSelectedListener(this);
        targetDropdown.setOnItemSelectedListener(this);

        this.initializeDropdowns();
    }

    private void testInput() {
        new RetrieveTranslation().execute(
                "As they rounded a bend in the path that ran beside the river, Lara recognized the silhouette of a fig tree atop a nearby hill. The weather was hot and the days were long. The fig tree was in full leaf, but not yet bearing fruit.\n" +
                        "Soon Lara spotted other landmarks—an outcropping of limestone beside the path that had a silhouette like a man’s face, a marshy spot beside the river where the waterfowl were easily startled, a tall tree that looked like a man with his arms upraised. They were drawing near to the place where there was an island in the river. The island was a good spot to make camp. They would sleep on the island tonight.\n" +
                        "Lara had been back and forth along the river path many times in her short life. Her people had not created the path—it had always been there, like the river—but their deerskin-shod feet and the wooden wheels of their handcarts kept the path well worn. Lara’s people were salt traders, and their livelihood took them on a continual journey.\n" +
                        "At the mouth of the river, the little group of half a dozen intermingled families gathered salt from the great salt beds beside the sea. They groomed and sifted the salt and loaded it into handcarts. When the carts were full, most of the group would stay behind, taking shelter amid rocks and simple lean-tos, while a band of fifteen or so of the heartier members set out on the path that ran alongside the river.\n" +
                        "With their precious cargo of salt, the travelers crossed the coastal lowlands and traveled toward the mountains. But Lara’s people never reached the mountaintops; they traveled only as far as the foothills. Many people lived in the forests and grassy meadows of the foothills, gathered in small villages. In return for salt, these people would give Lara’s people dried meat, animal skins, cloth spun from wool, clay pots, needles and scraping tools carved from bone, and little toys made of wood.\n" +
                        "Their bartering done, Lara and her people would travel back down the river path to the sea. The cycle would begin again.\n" +
                        "It had always been like this. Lara knew no other life. She traveled back and forth, up and down the river path. No single place was home. She liked the seaside, where there was always fish to eat, and the gentle lapping of the waves lulled her to sleep at night. She was less fond of the foothills, where the path grew steep, the nights could be cold, and views of great distances made her dizzy. She felt uneasy in the villages, and was often shy around strangers. The path itself was where she felt most at home. She loved the smell of the river on a hot day, and the croaking of frogs at night. Vines grew amid the lush foliage along the river, with berries that were good to eat. Even on the hottest day, sundown brought a cool breeze off the water, which sighed and sang amid the reeds and tall grasses.\n" +
                        "Of all the places along the path, the area they were approaching, with the island in the river, was Lara’s favorite.\n" +
                        "The terrain along this stretch of the river was mostly flat, but in the immediate vicinity of the island, the land on the sunrise side was like a rumpled cloth, with hills and ridges and valleys. Among Lara’s people, there was a wooden baby’s crib, suitable for strapping to a cart, that had been passed down for generations. The island was shaped like that crib, longer than it was wide and pointed at the upriver end, where the flow had eroded both banks. The island was like a crib, and the group of hills on the sunrise side of the river were like old women mantled in heavy cloaks gathered to have a look at the baby in the crib—that was how Lara’s father had once described the lay of the land.\n" +
                        "Larth spoke like that all the time, conjuring images of giants and monsters in the landscape. He could perceive the spirits, called numina, that dwelled in rocks and trees. Sometimes he could speak to them and hear what they had to say. The river was his oldest friend and told him where the fishing would be best. From whispers in the wind he could foretell the next day’s weather. Because of such skills, Larth was the leader of the group.\n" +
                        "“We’re close to the island, aren’t we, Papa?” said Lara.\n" +
                        "“How did you know?”\n" +
                        "“The hills. First we start to see the hills, off to the right. The hills grow bigger. And just before we come to the island, we can see the silhouette of that fig tree up there, along the crest of that hill.”\n" +
                        "“Good girl!” said Larth, proud of his daughter’s memory and powers of observation. He was a strong, handsome man with flecks of gray in his black beard. His wife had borne several children, but all had died very young except Lara, the last, whom his wife had died bearing. Lara was very precious to him. Like her mother, she had golden hair. Now that she had reached the age of childbearing, Lara was beginning to display the fullness of a woman’s hips and breasts. It was Larth’s greatest wish that he might live to see his own grandchildren. Not every man lived that long, but Larth was hopeful. He had been healthy all his life, partly, he believed, because he had always been careful to show respect to the numina he encountered on his journeys.\n" +
                        "Respecting the numina was important. The numen of the river could suck a man under and drown him. The numen of a tree could trip a man with its roots, or drop a rotten branch on his head. Rocks could give way underfoot, chuckling with amusement at their own treachery. Even the sky, with a roar of fury, sometimes sent down fingers of fire that could roast a man like a rabbit on a spit, or worse, leave him alive but robbed of his senses. Larth had heard that the earth itself could open and swallow a man; though he had never actually seen such a thing, he nevertheless performed a ritual each morning, asking the earth’s permission before he went striding across it."
        );
    }

    private void createCameraSource(boolean autoFocus, boolean useFlash) {
        Context context = getApplicationContext();

        // TODO: Create the TextRecognizer
        TextRecognizer textRecognizer = new TextRecognizer.Builder(context).build();
        // TODO: Set the TextRecognizer's Processor.

        // TODO: Check if the TextRecognizer is operational.
        if (!textRecognizer.isOperational()) {
            Log.w(LOG_TAG, "Detector dependencies are not yet available.");

            // Check for low storage.  If there is low storage, the native library will not be
            // downloaded, so detection will not become operational.
            IntentFilter lowStorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = registerReceiver(null, lowStorageFilter) != null;

            if (hasLowStorage) {
                String lowStorageError = "Your device does not have enough storage";
                Toast.makeText(this, lowStorageError, Toast.LENGTH_LONG).show();
                Log.w(LOG_TAG, lowStorageError);
            }
        }

        // TODO: Create the cameraSource using the TextRecognizer.
        cameraSource =
                new CameraSource.Builder(getApplicationContext(), textRecognizer)
                        .setFacing(CameraSource.CAMERA_FACING_BACK)
                        .setRequestedPreviewSize(1280, 1024)
                        .setRequestedFps(15.0f)
                        .setAutoFocusEnabled(autoFocus)
                        //.setFlashMode(useFlash ? Camera.Parameters.FLASH_MODE_TORCH : null)
                       // .setFocusMode(autoFocus ? Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO : null)
                        .build();
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
        if (parent.getId() == R.id.sourceDropdown) {
            this.source = Language.valueOf(this.languages[position]);
        } else {
            this.target = Language.valueOf(this.languages[position]);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    private void speak() {
        tts.speak(txtSpeechInput.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
    }

    private void initializeDropdowns() {
        languages = new String[Language.values().length];
        int index = 0;
        // Sort languages alphabetically
        PriorityQueue<String> sortedLanguages = new PriorityQueue<>();
        for (Language language : Language.values()) {
            sortedLanguages.add(language.name());
            Log.d(LOG_TAG, language.name());
        }
        // Put them all into the languages list.
        while (!sortedLanguages.isEmpty()) {
            languages[index] = sortedLanguages.remove();
            index++;
        }
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, languages);

        sourceDropdown.setAdapter(aa);
        targetDropdown.setAdapter(aa);

        sourceDropdown.setSelection(20, true);
    }



    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, source.toString());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        "Speak Now");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                           "Speech not supported",
                           Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);


                    new RetrieveTranslation().execute(result.get(0));

                    speakBtn.setVisibility(View.VISIBLE);
                }
                break;
            }

        }
    }

    private class RetrieveTranslation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            ITranslator translator = TranslationFactory.newTranslator();
            String result = "Error processing speech";
            try {
                result = translator.translate(params[0], source, target);
                //result = translator.translate("Where is the bathroom?", Language.ENGLISH, Language.SPANISH);
            } catch(Exception e) {
                StackTraceElement[] stackTrace = e.getStackTrace();
                for (StackTraceElement element : stackTrace) {
                    Log.d(LOG_TAG, element.toString() + "  line: " +element.getLineNumber());
                }

            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            txtSpeechInput.setText(result);
        }

    }

}

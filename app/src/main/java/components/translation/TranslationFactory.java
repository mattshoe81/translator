package components.translation;

public class TranslationFactory {
    public static ITranslator newTranslator() {
        return new GoogleTranslator();
    }

}

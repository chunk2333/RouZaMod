package RouZaMod;

import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

@SpireInitializer
public class RouZaMod {

    private static final String modID = "RouZa";

    public static String makeModID(String name){
        return modID + ":" + name;
    }

    public static void initialize() {
        new RouZaMod();
    }
}

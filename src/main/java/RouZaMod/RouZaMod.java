package RouZaMod;

import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpireInitializer
public class RouZaMod {

    private static final String modID = "RouZa";

    public static final Logger logger = LogManager.getLogger(RouZaMod.class.getName());

    public static String makeModID(String name){
        return modID + ":" + name;
    }

    public static void initialize() {
        new RouZaMod();
    }
}

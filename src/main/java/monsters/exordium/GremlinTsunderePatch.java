package monsters.exordium;
//持盾地精，加4血。
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.monsters.exordium.GremlinTsundere;

public class GremlinTsunderePatch {
    @SpirePatch(clz = GremlinTsundere.class, method = SpirePatch.CONSTRUCTOR)
    public static class GremlinTsundereHpFix{
        @SpirePostfixPatch
        public static void PostFix(GremlinTsundere __instance){
            __instance.maxHealth += 4;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
}

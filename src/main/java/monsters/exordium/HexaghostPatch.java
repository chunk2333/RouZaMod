package monsters.exordium;
//六火亡魂，加10血，固定塞灼伤+ 。
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.monsters.exordium.Hexaghost;

public class HexaghostPatch {
    @SpirePatch(clz = Hexaghost.class, method = SpirePatch.CONSTRUCTOR)
    public static class HexaghostHpFix{
        @SpirePostfixPatch
        public static void PostFix(Hexaghost __instance){
            __instance.maxHealth += 10;
            __instance.currentHealth = __instance.maxHealth;
        }

        @SpirePatch2(clz = Hexaghost.class, method = "usePreBattleAction")
        public static class usePreBattleAction{
            @SpireInsertPatch(rloc = 1, localvars = {"burnUpgraded"})
            public static void PreFix(Hexaghost __instance, @ByRef boolean[] ___burnUpgraded){
                ___burnUpgraded[0] = true;
            }
        }
    }
}

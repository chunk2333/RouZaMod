package monsters.exordium;
//虱虫，加2血，开局获得壁垒。
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.LouseDefensive;
import com.megacrit.cardcrawl.powers.BarricadePower;

public class LouseDefensivePatch {
    @SpirePatch(clz = LouseDefensive.class, method = SpirePatch.CONSTRUCTOR)
    public static class FuzzyLouseDefensiveHpFix{
        @SpirePostfixPatch
        public static void PostFix(LouseDefensive __instance){
            __instance.maxHealth += 2;
            __instance.currentHealth = __instance.maxHealth;
        }
    }

    @SpirePatch(clz = LouseDefensive.class, method = "usePreBattleAction")
    public static class usePreBattleAction{
        @SpirePrefixPatch
        public static void PreFix(LouseDefensive __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new BarricadePower(__instance)));
        }
    }
}

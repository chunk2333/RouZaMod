package monsters.beyond;
//爆炸机：加5血，开局获得一层无实体。
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.beyond.Exploder;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

public class ExploderPatch {
    @SpirePatch(clz = Exploder.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix {
        @SpirePostfixPatch
        public static void PostFix(Exploder __instance) {
            __instance.maxHealth += 5;
            __instance.currentHealth = __instance.maxHealth;
        }
    }

    @SpirePatch(clz = Exploder.class, method = "usePreBattleAction")
    public static class PreBattleActionFix{
        @SpirePrefixPatch
        public static void PreFix(Exploder __instance){
             AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new IntangiblePlayerPower(__instance, 1), 1));
        }
    }
}

package monsters.exordium;
//抢劫的，开局获得2点力量，加2血。
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.Looter;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class LooterPatch {

    @SpirePatch(clz = Looter.class, method = SpirePatch.CONSTRUCTOR)
    public static class LooterHpFix{
        @SpirePostfixPatch
        public static void PostFix(Looter __instance){
            __instance.maxHealth += 2;
            __instance.currentHealth = __instance.maxHealth;
        }
    }

    @SpirePatch(clz = Looter.class, method = "usePreBattleAction")
    public static class usePreBattleAction{
        @SpirePrefixPatch
        public static void PreFix(Looter __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new StrengthPower(__instance, 2), 2));
        }
    }
}

package monsters.beyond;
//钉刺姬：获得蹲酱同款反伤 +5血
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.beyond.Spiker;
import com.megacrit.cardcrawl.powers.SharpHidePower;

public class SpikerPatch {
    @SpirePatch(clz = Spiker.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix {
        @SpirePostfixPatch
        public static void PostFix(Spiker __instance) {
            __instance.maxHealth += 5;
            __instance.currentHealth = __instance.maxHealth;
        }
    }

    @SpirePatch(clz = Spiker.class, method = "usePreBattleAction")
    public static class usePreBattleAction{
        @SpirePrefixPatch
        public static void PreFix(Spiker __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new SharpHidePower(__instance, 2)));
        }
    }
}

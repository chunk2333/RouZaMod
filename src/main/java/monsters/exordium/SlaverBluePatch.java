package monsters.exordium;
//奴隶贩子（蓝），加3血，给予虚弱时同时偷取你一张卡牌（铜球逻辑）并且获得2点力量。
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.ApplyStasisAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.SlaverBlue;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class SlaverBluePatch {
    @SpirePatch(clz = SlaverBlue.class, method = SpirePatch.CONSTRUCTOR)
    public static class SlaverBlueHpFix{
        @SpirePostfixPatch
        public static void PostFix(SlaverBlue __instance){
            __instance.maxHealth += 3;
            __instance.currentHealth = __instance.maxHealth;
        }
    }

    @SpirePatch(clz = SlaverBlue.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 79)
        public static void InsertFix(SlaverBlue __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyStasisAction(__instance));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new StrengthPower(__instance, 2), 2));
        }
    }

}

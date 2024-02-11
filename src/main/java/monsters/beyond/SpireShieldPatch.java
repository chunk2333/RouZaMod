package monsters.beyond;
//高塔之盾：开局获得30格挡，壁垒，加20血 重击给予玩家应急按钮的debuff  不能从卡牌中获得格挡
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.ending.SpireShield;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.NoBlockPower;

public class SpireShieldPatch {
    @SpirePatch(clz = SpireShield.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix {
        @SpirePostfixPatch
        public static void PostFix(SpireShield __instance) {
            __instance.maxHealth += 20;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = SpireShield.class, method = "usePreBattleAction")
    public static class PreBattleActionFix{
        @SpirePrefixPatch
        public static void PreFix(SpireShield __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new BarricadePower(__instance)));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(__instance, __instance, 30));
        }
    }
    @SpirePatch(clz = SpireShield.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 113)
        public static void InsertFix01(SpireShield __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new NoBlockPower(AbstractDungeon.player, 1, true), 1));
        }
    }


}

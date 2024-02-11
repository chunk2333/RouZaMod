package monsters.beyond;
//高塔之矛：加20血，开局获得壁垒和1点力量，塞牌同三柱，4段攻击后给予玩家一层脆弱。
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.ending.SpireSpear;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import helpers.RouZaHelper;

public class SpireSpearPatch {
    @SpirePatch(clz = SpireSpear.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix {
        @SpirePostfixPatch
        public static void PostFix(SpireSpear __instance) {
            __instance.maxHealth += 20;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = SpireSpear.class, method = "usePreBattleAction")
    public static class PreBattleActionFix{
        @SpirePrefixPatch
        public static void PreFix(SpireSpear __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new StrengthPower(__instance, 1), 1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new BarricadePower(__instance)));
        }
    }
    @SpirePatch(clz = SpireSpear.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 88)
        public static SpireReturn<Void> InsertFix01(SpireSpear __instance){
            if (AbstractDungeon.ascensionLevel >= 18) {
                for (int i = 0; i < 2; i++){
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(RouZaHelper.getRandomStatusOrCurseCard(), 1, false, true));
                }
            } else {
                for (int i = 0; i < 2; i++){
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(RouZaHelper.getRandomStatusOrCurseCard(), 1));
                }
            }
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(__instance));
            return SpireReturn.Return();
        }
        @SpireInsertPatch(loc = 103)
        public static void InsertFix02(SpireSpear __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new FrailPower(AbstractDungeon.player, 1, true), 1));
        }
    }
}

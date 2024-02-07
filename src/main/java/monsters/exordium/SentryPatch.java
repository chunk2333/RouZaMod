package monsters.exordium;
//哨卫，加2血，开局获得1点力量，塞3张随机状态/诅咒牌来替代之前的塞牌。
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.Sentry;
import com.megacrit.cardcrawl.powers.StrengthPower;
import helpers.RouZaHelper;

public class SentryPatch {
    @SpirePatch(clz = Sentry.class, method = SpirePatch.CONSTRUCTOR)
    public static class SentryHpFix{
        @SpirePostfixPatch
        public static void PostFix(Sentry __instance){
            __instance.maxHealth += 2;
            __instance.currentHealth = __instance.maxHealth;
        }
    }

    @SpirePatch(clz = Sentry.class, method = "usePreBattleAction")
    public static class PreBattleActionFix{
        @SpirePrefixPatch
        public static void PreFix(Sentry __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new StrengthPower(__instance, 1), 1));
        }
    }


    @SpirePatch(clz = Sentry.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 104)
        public static SpireReturn<Void> InsertFix(Sentry __instance){
            AbstractCard c;
            for(int i = 0; i < 3;i++){
                if(AbstractDungeon.monsterRng.randomBoolean()){
                    c = RouZaHelper.getRandomCurseCard();
                } else {
                    c = RouZaHelper.getRandomStatusCard();
                }
                if(c.cardID.equals("Burn")){
                    if(AbstractDungeon.monsterRng.randomBoolean()){
                        c.upgrade();
                    }
                }
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(c, 1));
            }
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(__instance));
            return SpireReturn.Return();
        }
    }
}

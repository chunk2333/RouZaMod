package monsters.city;
//奴隶头子，+4HP，塞伤口换成灼伤同时回复3hp。
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.Taskmaster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class SlaverBossPatch {
    @SpirePatch(clz = Taskmaster.class, method = SpirePatch.CONSTRUCTOR)
    public static class TaskmasterHpFix{
        @SpirePostfixPatch
        public static void PostFix(Taskmaster __instance){
            __instance.maxHealth += 4;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = Taskmaster.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 72, localvars = {"woundCount"})
        public static SpireReturn<Void> InsertFix01(Taskmaster __instance, int ___woundCount){
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Burn(), ___woundCount));
            AbstractDungeon.actionManager.addToBottom(new HealAction(__instance, __instance, 3));
            if (AbstractDungeon.ascensionLevel >= 18)
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new StrengthPower(__instance, 1), 1));
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(__instance));
            return SpireReturn.Return();
        }
    }
}

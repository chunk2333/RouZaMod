package monsters.beyond;
//反冲机：加5血，塞牌同三柱。
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.beyond.Repulsor;
import helpers.RouZaHelper;

public class RepulsorPatch {
    @SpirePatch(clz = Repulsor.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix {
        @SpirePostfixPatch
        public static void PostFix(Repulsor __instance) {
            __instance.maxHealth += 5;
            __instance.currentHealth = __instance.maxHealth;
        }
    }

    @SpirePatch(clz = Repulsor.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 69, localvars = {"dazeAmt"})
        public static SpireReturn<Void> InsertFix(Repulsor __instance, int ___dazeAmt){
            for (int i = 0; i < ___dazeAmt; i++){
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(RouZaHelper.getRandomStatusOrCurseCard(), 1, true, true));
            }
            return SpireReturn.Return();
        }
    }
}

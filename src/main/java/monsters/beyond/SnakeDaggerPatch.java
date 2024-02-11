package monsters.beyond;
//匕首：加2血，死亡时回复场上全部怪物25血，给予蛇女一层缓冲，塞牌同三柱。
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.SnakeDagger;
import com.megacrit.cardcrawl.powers.BufferPower;
import helpers.RouZaHelper;

public class SnakeDaggerPatch {
    @SpirePatch(clz = SnakeDagger.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix {
        @SpirePostfixPatch
        public static void PostFix(SnakeDagger __instance) {
            __instance.maxHealth += 2;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = SnakeDagger.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 61)
        public static SpireReturn<Void> InsertFix01(SnakeDagger __instance){
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(RouZaHelper.getRandomStatusOrCurseCard(), 1));
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(__instance));
            return SpireReturn.Return();
        }
        @SpireInsertPatch(loc = 69)
        public static void InsertFix02(SnakeDagger __instance){
            for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                if (!m.isDying && !m.isEscaping)
                    AbstractDungeon.actionManager.addToBottom(new HealAction(m, __instance, 25));
            }
            AbstractMonster Reptomancer = AbstractDungeon.getCurrRoom().monsters.getMonster("Reptomancer");
            if(Reptomancer != null){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Reptomancer, __instance, new BufferPower(Reptomancer, 1), 1));
            }

        }
    }
}

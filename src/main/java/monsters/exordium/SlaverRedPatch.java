package monsters.exordium;
////奴隶贩子（红），加3血，丢网的同时给场上所有怪物回复5血。
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.SlaverRed;

public class SlaverRedPatch {
    @SpirePatch(clz = SlaverRed.class, method = SpirePatch.CONSTRUCTOR)
    public static class SlaverRedHpFix{
        @SpirePostfixPatch
        public static void PostFix(SlaverRed __instance){
            __instance.maxHealth += 3;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = SlaverRed.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 92)
        public static void InsertFix(SlaverRed __instance){
            for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                if (!m.isDying && !m.isEscaping)
                    AbstractDungeon.actionManager.addToBottom(new HealAction(m, __instance, 5));
            }
        }
    }
}

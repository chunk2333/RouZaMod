package monsters.exordium;
//胖地精，减2血，给予虚弱时同时给予一层易伤。
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.GremlinFat;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class GremlinFatPatch {
    @SpirePatch(clz = GremlinFat.class, method = SpirePatch.CONSTRUCTOR)
    public static class GremlinFatHpFix{
        @SpirePostfixPatch
        public static void PostFix(GremlinFat __instance){
            __instance.maxHealth -= 2;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = GremlinFat.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 71)
        public static void InsertFix(GremlinFat __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new VulnerablePower(AbstractDungeon.player, 1, true), 1));
        }
    }
}

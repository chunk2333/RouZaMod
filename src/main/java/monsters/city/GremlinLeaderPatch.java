package monsters.city;
//地精首领：加10血，加8再生，1好奇
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.GremlinLeader;
import com.megacrit.cardcrawl.powers.CuriosityPower;
import com.megacrit.cardcrawl.powers.RegenerateMonsterPower;

public class GremlinLeaderPatch {
    @SpirePatch(clz = GremlinLeader.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix{
        @SpirePostfixPatch
        public static void PostFix(GremlinLeader __instance){
            __instance.maxHealth += 10;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = GremlinLeader.class, method = "usePreBattleAction")
    public static class usePreBattleAction{
        @SpirePrefixPatch
        public static void PreFix(GremlinLeader __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new CuriosityPower(__instance, 1)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new RegenerateMonsterPower(__instance, 8)));
        }
    }
}

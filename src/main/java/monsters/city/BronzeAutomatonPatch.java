package monsters.city;
//铜人：社保完毕会偷取你身上的所有正面buff。+20血   铜人开局获得10点再生
import actions.StealBuffAction;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.BronzeAutomaton;
import com.megacrit.cardcrawl.powers.RegenerateMonsterPower;

public class BronzeAutomatonPatch {
    @SpirePatch(clz = BronzeAutomaton.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix {
        @SpirePostfixPatch
        public static void PostFix(BronzeAutomaton __instance) {
            __instance.maxHealth += 20;
            __instance.currentHealth = __instance.maxHealth;
        }
    }

    @SpirePatch(clz = BronzeAutomaton.class, method = "usePreBattleAction")
    public static class usePreBattleAction{
        @SpirePrefixPatch
        public static void PreFix(BronzeAutomaton __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new RegenerateMonsterPower(__instance, 10)));
        }
    }

    @SpirePatch(clz = BronzeAutomaton.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 136)
        public static void InsertFix01(BronzeAutomaton __instance){
            AbstractDungeon.actionManager.addToBottom(new StealBuffAction(__instance, AbstractDungeon.player.powers.size()));
        }
    }
}

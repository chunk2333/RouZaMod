package monsters.beyond;
//塔内增生组织：加20血，开局给予12层缠绕。
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.SpireGrowth;
import com.megacrit.cardcrawl.powers.ConstrictedPower;

public class SerpentPatch {
    @SpirePatch(clz = SpireGrowth.class, method = SpirePatch.CONSTRUCTOR)
    public static class FixHealth{
        @SpirePostfixPatch
        public static void PostFixHp(SpireGrowth __instance){
            __instance.maxHealth += 20;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = AbstractMonster.class, method = "usePreBattleAction")
    public static class PreBattleActionFix{
        @SpirePrefixPatch
        public static void PreFix(AbstractMonster __instance){
            if (__instance instanceof SpireGrowth) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new ConstrictedPower(AbstractDungeon.player, __instance, 12)));
            }

        }
    }
}

package monsters.beyond;
//大脑袋：加20血，移除缓慢，重击会给你上一层虚弱或者脆弱或者易伤。
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.beyond.GiantHead;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class GiantHeadPatch {
    @SpirePatch(clz = GiantHead.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix {
        @SpirePostfixPatch
        public static void PostFix(GiantHead __instance) {
            __instance.maxHealth += 20;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = GiantHead.class, method = "usePreBattleAction")
    public static class PostBattleActionFix{
        @SpirePostfixPatch
        public static void PostFix(GiantHead __instance){
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(__instance, __instance, "Slow"));
        }
    }
    @SpirePatch(clz = GiantHead.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 113)
        public static void InsertFix(GiantHead __instance){
            int num = AbstractDungeon.monsterRng.random(1, 3);
            switch (num) {
                case 1:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new FrailPower(AbstractDungeon.player, 1, true), 1));
                    break;
                case 2:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new WeakPower(AbstractDungeon.player, 1, true), 1));
                    break;
                case 3:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new VulnerablePower(AbstractDungeon.player, 1, true), 1));
                    break;
                default:
                    break;
            }
        }
    }
}

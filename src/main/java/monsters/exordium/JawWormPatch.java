package monsters.exordium;
//大颚虫，加1血，首次攻击给予3层脆弱。
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.JawWorm;
import com.megacrit.cardcrawl.powers.FrailPower;

public class JawWormPatch {
    @SpirePatch(clz = JawWorm.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {float.class, float.class, boolean.class})
    public static class JawWormHpFix{
        @SpirePostfixPatch
        public static void PostFix(JawWorm __instance){
            __instance.maxHealth += 1;
            __instance.currentHealth = __instance.maxHealth;
        }
    }

    @SpirePatch(clz = JawWorm.class, method = "usePreBattleAction")
    public static class usePreBattleAction{
        @SpirePrefixPatch
        public static void PreFix(JawWorm __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new FrailPower(AbstractDungeon.player, 3, true), 3));
        }
    }

}

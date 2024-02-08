package monsters.city;
//圆球守护者，获得4多层护甲3金属化加2血。
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.SphericGuardian;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

public class SphericGuardianPatch {
    @SpirePatch(clz = SphericGuardian.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {float.class, float.class})
    public static class HpFix{
        @SpirePostfixPatch
        public static void PostFix(SphericGuardian __instance){
            __instance.maxHealth += 4;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = SphericGuardian.class, method = "usePreBattleAction")
    public static class usePreBattleAction{
        @SpirePrefixPatch
        public static void PreFix(SphericGuardian __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new PlatedArmorPower(__instance, 4)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new MetallicizePower(__instance, 3)));
        }
    }
}

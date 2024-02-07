package monsters.exordium;
//真菌兽，加3血，开局获得一层反伤。
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.FungiBeast;
import com.megacrit.cardcrawl.powers.ThornsPower;


public class FungiBeastPatch {


    @SpirePatch(clz = FungiBeast.class, method = SpirePatch.CONSTRUCTOR)
    public static class FungiBeastHpFix{
        @SpirePostfixPatch
        public static void PostFix(FungiBeast __instance){
            __instance.maxHealth += 3;
            __instance.currentHealth = __instance.maxHealth;
        }
    }


    @SpirePatch(clz = FungiBeast.class, method = "usePreBattleAction")
    public static class PreBattleActionFix{
        @SpirePostfixPatch
        public static void PostFix01(FungiBeast __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new ThornsPower(__instance, 1)));
        }
    }

}

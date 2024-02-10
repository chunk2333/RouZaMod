package monsters.beyond;
//巨口：加100血，获得1层激怒和生气。
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Maw;
import com.megacrit.cardcrawl.powers.AngerPower;
import com.megacrit.cardcrawl.powers.AngryPower;

public class MawPatch {
    @SpirePatch(clz = Maw.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix {
        @SpirePostfixPatch
        public static void PostFix(Maw __instance) {
            __instance.maxHealth += 100;
            __instance.currentHealth = __instance.maxHealth;
        }
    }

    @SpirePatch(clz = AbstractMonster.class, method = "usePreBattleAction")
    public static class PreBattleActionFix{
        @SpirePrefixPatch
        public static void PreFix(AbstractMonster __instance){
            if (__instance instanceof Maw) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new AngerPower(__instance, 1), 1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new AngryPower(__instance, 1), 1));
            }

        }
    }
}

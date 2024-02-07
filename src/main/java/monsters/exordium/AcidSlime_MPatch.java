package monsters.exordium;
//酸液（绿）史莱姆（中），加4血，获得1点力量，塞黏液时多塞一张灼伤到弃牌堆，给予虚弱时同时给予一层易伤。
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_M;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class AcidSlime_MPatch {
    @SpirePatch(clz = AcidSlime_M.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {float.class, float.class, int.class, int.class})
    public static class AcidSlime_MHpFix{
        @SpirePostfixPatch
        public static void PostFix(AcidSlime_M __instance){
            __instance.maxHealth += 4;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = AcidSlime_M.class, method = "takeTurn")
    public static class takeTurnFix{

        @SpireInsertPatch(loc = 80)
        public static void InsertFix01(AcidSlime_M __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new VulnerablePower(AbstractDungeon.player, 1, true), 1));
        }

        @SpireInsertPatch(loc = 92)
        public static void InsertFix02(AcidSlime_M __instance){
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Burn(), 1));
        }
    }
    @SpirePatch(clz = AbstractMonster.class, method = "usePreBattleAction")
    public static class PreBattleActionFix{
        @SpirePrefixPatch
        public static void PreFix(AbstractMonster __instance){
            if (__instance instanceof AcidSlime_M) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new StrengthPower(__instance, 1), 1));
            }

        }
    }

}

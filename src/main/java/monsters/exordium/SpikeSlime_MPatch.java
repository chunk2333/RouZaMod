package monsters.exordium;
//尖刺（蓝）史莱姆（中），加4血，开局获得5点格挡，给予脆弱时额外给予一层并获得2力量，塞黏液时额外塞一张伤口到抽牌堆顶部。
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.SpikeSlime_M;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class SpikeSlime_MPatch {
    @SpirePatch(clz = SpikeSlime_M.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {float.class, float.class, int.class, int.class})
    public static class SpikeSlime_MHpFix{
        @SpirePostfixPatch
        public static void PostFix(SpikeSlime_M __instance){
            __instance.maxHealth += 4;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = SpikeSlime_M.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 76)
        public static void InsertFix01(SpikeSlime_M __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new FrailPower(AbstractDungeon.player, 1, true), 1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new StrengthPower(__instance, 2), 2));
        }
        @SpireInsertPatch(loc = 87)
        public static void InsertFix02(SpikeSlime_M __instance){
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Wound(), 1, false, true));
        }
    }
    @SpirePatch(clz = AbstractMonster.class, method = "usePreBattleAction")
    public static class PreBattleActionFix{
        @SpirePrefixPatch
        public static void PreFix(AbstractMonster __instance){
            if (__instance instanceof SpikeSlime_M) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(__instance, __instance, 5));
            }

        }
    }
}

package monsters.ending;
//腐败之心：加100血，开局获得15层再生，塞牌时在你的抽牌堆顶部塞入一张随机诅咒牌并且召唤一只异蛇，重击时获得2层缓冲，获得人工的同时获得蹲酱同款反伤buff，获得疼痛戳刺时获得三层荆棘
import actions.MonsterUsePreBattleAction;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Snecko;
import com.megacrit.cardcrawl.monsters.ending.CorruptHeart;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.RegenerateMonsterPower;
import com.megacrit.cardcrawl.powers.SharpHidePower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import helpers.RouZaHelper;

public class CorruptHeartPatch {
    @SpirePatch(clz = CorruptHeart.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix {
        @SpirePostfixPatch
        public static void PostFix(CorruptHeart __instance) {
            __instance.maxHealth += 100;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = CorruptHeart.class, method = "usePreBattleAction")
    public static class PreBattleActionFix{
        @SpirePrefixPatch
        public static void PreFix(CorruptHeart __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new RegenerateMonsterPower(__instance, 15)));
        }
    }
    @SpirePatch(clz = CorruptHeart.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 158)
        public static void InsertFix01(CorruptHeart __instance){
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(RouZaHelper.getRandomCurseCard(), 1, false, true));
            AbstractMonster Snecko = new Snecko(-455.0F, 0.0F);
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(Snecko, false));
            AbstractDungeon.actionManager.addToBottom(new MonsterUsePreBattleAction(Snecko));
        }
        @SpireInsertPatch(loc = 243)
        public static void InsertFix02(CorruptHeart __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new BufferPower(__instance, 2), 2));
        }
        @SpireInsertPatch(loc = 186)
        public static void InsertFix03(CorruptHeart __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new SharpHidePower(__instance, 4)));
        }
        @SpireInsertPatch(loc = 194)
        public static void InsertFix04(CorruptHeart __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new ThornsPower(__instance, 3)));
        }
    }
}

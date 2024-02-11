package monsters.beyond;
//觉醒者：加20血，每次攻击都会塞虚空，觉醒后的多段攻击会清除玩家身上的所有buff，Kakaa每次行动都会给予场上所有怪物7护甲。
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class AwakenedOnePatch {
    @SpirePatch(clz = AwakenedOne.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix {
        @SpirePostfixPatch
        public static void PostFix(AwakenedOne __instance) {
            __instance.maxHealth += 30;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = AwakenedOne.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 167)
        public static void InsertFix01(AwakenedOne __instance){
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new VoidCard(), 1, true, true));
        }
        @SpireInsertPatch(loc = 169)
        public static void InsertFix02(AwakenedOne __instance){
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new VoidCard(), 1, true, true));
        }
        @SpireInsertPatch(loc = 208)
        public static void InsertFix03(AwakenedOne __instance){
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    ArrayList<AbstractPower> power = AbstractDungeon.player.powers;
                    for (AbstractPower po : power) {
                        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, po.ID));
                    }
                }
            });
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new VoidCard(), 1, true, true));
        }

    }
}

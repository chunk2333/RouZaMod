package monsters.exordium;
//酸液（绿）史莱姆（小），加2血，给予虚弱时额外给予一层虚弱，攻击时会塞一张虚空到弃牌堆。
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_S;
import com.megacrit.cardcrawl.powers.WeakPower;

public class AcidSlime_SPatch {
    @SpirePatch(clz = AcidSlime_S.class, method = SpirePatch.CONSTRUCTOR)
    public static class AcidSlime_SHpFix{
        @SpirePostfixPatch
        public static void PostFix(AcidSlime_S __instance){
            __instance.maxHealth += 2;
            __instance.currentHealth = __instance.maxHealth;
        }
    }

    @SpirePatch(clz = AcidSlime_S.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 68)
        public static void InsertFix01(AcidSlime_S __instance){
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new VoidCard(), 1));
        }

        @SpireInsertPatch(loc = 72)
        public static void InsertFix02(AcidSlime_S __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new WeakPower(AbstractDungeon.player, 1, true), 1));
        }
    }
}

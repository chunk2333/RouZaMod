package monsters.exordium;
//尖刺（蓝）史莱姆（小），加2血，攻击时给予一层易伤，将一张粘液塞到抽牌堆顶部。
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.SpikeSlime_S;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class SpikeSlime_SPatch {
    @SpirePatch(clz = SpikeSlime_S.class, method = SpirePatch.CONSTRUCTOR)
    public static class SpikeSlime_SHpFix{
        @SpirePostfixPatch
        public static void PostFix(SpikeSlime_S __instance){
            __instance.maxHealth += 2;
            __instance.currentHealth = __instance.maxHealth;
        }

        @SpirePatch(clz = SpikeSlime_S.class, method = "takeTurn")
        public static class takeTurnFix{
            @SpireInsertPatch(loc = 68)
            public static void InsertFix01(SpikeSlime_S __instance){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new VulnerablePower(AbstractDungeon.player, 1, true), 1));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Slimed(), 1, false, true));

            }
        }
    }
    @SpirePatch(clz = SpikeSlime_S.class, method = "getMove")//修改意图图标
    public static class SpikeSlime_SPatchInsertFix1{
        @SpireInsertPatch(loc = 75)
        public static SpireReturn<Void> Insert(SpikeSlime_S __instance){
            __instance.setMove((byte)1, AbstractMonster.Intent.ATTACK_DEBUFF, (__instance.damage.get(0)).base);
            return SpireReturn.Return();
        }
    }
}

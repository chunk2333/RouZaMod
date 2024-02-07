package monsters.exordium;
//尖刺（蓝）史莱姆（大），加5血，分裂后，给全体怪物回复10HP。
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.SpikeSlime_L;

public class SpikeSlime_LPatch {
    @SpirePatch(clz = SpikeSlime_L.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {float.class, float.class, int.class, int.class})
    public static class SpikeSlime_LHpFix{
        @SpirePostfixPatch
        public static void PostFix(SpikeSlime_L __instance){
            __instance.maxHealth += 5;
            __instance.currentHealth = __instance.maxHealth;
        }
        @SpirePatch(clz = SpikeSlime_L.class, method = "takeTurn")
        public static class takeTurnFix{
            @SpireInsertPatch(loc = 136)
            public static void InsertFix01(SpikeSlime_L __instance){
                for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                    if (!m.isDying && !m.isEscaping)
                        AbstractDungeon.actionManager.addToBottom(new HealAction(m, __instance, 10));
                }
            }
        }
    }
}

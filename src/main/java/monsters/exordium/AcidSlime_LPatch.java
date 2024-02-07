package monsters.exordium;
//酸液（绿）史莱姆（大），加5血，分裂后，给全体怪物加2力量(不包括已分裂的)。
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_L;
import com.megacrit.cardcrawl.powers.StrengthPower;


public class AcidSlime_LPatch {
    @SpirePatch(clz = AcidSlime_L.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {float.class, float.class, int.class, int.class})
    public static class AcidSlime_LHpFix{
        @SpirePostfixPatch
        public static void PostFix(AcidSlime_L __instance){
            __instance.maxHealth += 5;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = AcidSlime_L.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 140)
        public static void InsertFix01(AcidSlime_L __instance){
            for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                if (!m.isDying && !m.isEscaping)
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, __instance, new StrengthPower(m, 2), 2));
            }
        }
    }
}

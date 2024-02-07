package monsters.exordium;
//地精大块头，加4血，开局获得一层激怒。
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.GremlinNob;
import com.megacrit.cardcrawl.powers.AngerPower;

public class GremlinNobPatch {
    @SpirePatch(clz = GremlinNob.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {float.class, float.class, boolean.class})
    public static class GremlinNobHpFix{
        @SpirePostfixPatch
        public static void PostFix(GremlinNob __instance){
            __instance.maxHealth += 4;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = AbstractMonster.class, method = "usePreBattleAction")
    public static class PreBattleActionFix{
        @SpirePrefixPatch
        public static void PreFix(AbstractMonster __instance){
            if (__instance instanceof GremlinNob) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new AngerPower(__instance, 1), 1));
            }

        }
    }
}

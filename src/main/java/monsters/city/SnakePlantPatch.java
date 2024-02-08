package monsters.city;
//蛇花，获得一点力量，加2血，开局给你一层脆弱
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.SnakePlant;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class SnakePlantPatch {
    @SpirePatch(clz = SnakePlant.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix{
        @SpirePostfixPatch
        public static void PostFix(SnakePlant __instance){
            __instance.maxHealth += 2;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = SnakePlant.class, method = "usePreBattleAction")
    public static class usePreBattleAction{
        @SpirePrefixPatch
        public static void PreFix(SnakePlant __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new FrailPower(AbstractDungeon.player, 1, true), 1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new StrengthPower(__instance, 1), 1));
        }
    }
}

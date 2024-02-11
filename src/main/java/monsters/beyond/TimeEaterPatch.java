package monsters.beyond;
//时间吞噬者：300血无限愚蠢(愚蠢、意图1、意图2、如果<300 hp，愚蠢)，加20血。
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.TimeEater;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class TimeEaterPatch {
    @SpirePatch(clz = TimeEater.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix {
        @SpirePostfixPatch
        public static void PostFix(TimeEater __instance) {
            __instance.maxHealth += 20;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = TimeEater.class, method = "usePreBattleAction")
    public static class usePreBattleAction{
        @SpirePrefixPatch
        public static void PreFix(TimeEater __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new StrengthPower(__instance, 2), 2));
        }
    }
    @SpirePatch(clz = TimeEater.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 169)
        public static SpireReturn<Void> InsertFix01(TimeEater __instance){
            MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("TimeEater");
            String[] DIALOG = monsterStrings.DIALOG;
            AbstractDungeon.actionManager.addToBottom(new ShoutAction(__instance, DIALOG[1], 0.5F, 2.0F));
            AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(__instance));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(__instance, __instance, "Shackled"));
            AbstractDungeon.actionManager.addToBottom(new HealAction(__instance, __instance, 300 - __instance.currentHealth));
            int headSlamDmg;
            if (AbstractDungeon.ascensionLevel >= 4){
                headSlamDmg = 32;
            } else {
                headSlamDmg = 26;
            }
            if (AbstractDungeon.ascensionLevel >= 19)
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(__instance, __instance, headSlamDmg));
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(__instance));
            return SpireReturn.Return();
        }
    }
    @SpirePatch2(clz = TimeEater.class, method = "getMove")
    public static class getMoveFix{
        @SpireInsertPatch(rloc = 0, localvars = {"usedHaste"})
        public static SpireReturn<Void> InsertFix(TimeEater __instance, @ByRef boolean[] ___usedHaste){
            ___usedHaste[0] = false;
            if (300 < __instance.maxHealth / 2) {
                __instance.setMove((byte)5, AbstractMonster.Intent.BUFF);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
}

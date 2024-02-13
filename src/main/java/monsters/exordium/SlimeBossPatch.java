package monsters.exordium;
//史莱姆老大，增加10血，<=100血分裂。
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.SlimeBoss;

public class SlimeBossPatch {

    @SpirePatch(clz = SlimeBoss.class, method = SpirePatch.CONSTRUCTOR)
    public static class FixHealth{
        @SpirePostfixPatch
        public static void PostFixHp(SlimeBoss __instance){
            __instance.maxHealth += 10;
            __instance.currentHealth = __instance.maxHealth;
        }
    }

//    @SpirePatch(clz = SlimeBoss.class, method = "damage")
//    public static class PatchSplit{
//        @SpireInsertPatch(loc = 175)
//        public static void InsertFix(SlimeBoss __instance){
//            if (!__instance.isDying && __instance.currentHealth <= 100 && __instance.nextMove != 3) {
//                MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SlimeBoss");
//                String[] MOVES = monsterStrings.MOVES;
//                String SPLIT_NAME = MOVES[2];
//                __instance.setMove(SPLIT_NAME, (byte)3, AbstractMonster.Intent.UNKNOWN);
//                __instance.createIntent();
//                AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(__instance, TextAboveCreatureAction.TextType.INTERRUPTED));
//                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(__instance, SPLIT_NAME, (byte)3, AbstractMonster.Intent.UNKNOWN));
//            }
//        }
//    }
}

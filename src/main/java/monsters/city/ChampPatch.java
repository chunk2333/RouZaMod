package monsters.city;
//第勇：加1血，第二回合召唤狗男女，获得壁垒。
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.*;
import com.megacrit.cardcrawl.powers.BarricadePower;

public class ChampPatch {
    @SpirePatch(clz = Champ.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix {
        @SpirePostfixPatch
        public static void PostFix(Champ __instance) {
            __instance.maxHealth += 1;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch2(clz = Champ.class, method = "getMove")
    public static class GetMoveFix{
        @SpireInsertPatch(loc = 295, localvars = {"numTurns"})
        public static SpireReturn<Void> InsertGetMoveFix(Champ __instance, int ___numTurns){
            if (GameActionManager.turn == 3){
                __instance.setMove((byte)114514, AbstractMonster.Intent.UNKNOWN);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = Champ.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 149)
        public static SpireReturn<Void> Fix(Champ __instance){
            if (__instance.nextMove == (byte)114514){
                AbstractDungeon.actionManager.addToBottom(new AnimateShakeAction(__instance, 1.0F, 0.1F));
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new Healer(200.0F, 20.0F), false));
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new Centurion(-455.0F, 0.0F), false));
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(__instance));
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = Champ.class, method = "usePreBattleAction")
    public static class usePreBattleAction{
        @SpirePrefixPatch
        public static void PreFix(Champ __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new BarricadePower(__instance)));
        }
    }
}

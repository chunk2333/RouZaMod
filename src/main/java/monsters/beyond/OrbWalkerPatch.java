package monsters.beyond;
//圆球行者：加10血，第二回合召唤一只圆球行者。
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.beyond.OrbWalker;

public class OrbWalkerPatch {
    @SpirePatch(clz = OrbWalker.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix {
        @SpirePostfixPatch
        public static void PostFix(OrbWalker __instance) {
            __instance.maxHealth += 10;
            __instance.currentHealth = __instance.maxHealth;
        }
    }

    @SpirePatch(clz = OrbWalker.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpirePrefixPatch
        public static void PreFix(OrbWalker __instance){
            if(GameActionManager.turn == 2){
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new OrbWalker(-500.0F, 0.0F), false));
            }
        }
    }
}

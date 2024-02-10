package monsters.beyond;
//小黑：加15血，生命链接回复满血
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import com.megacrit.cardcrawl.powers.RegrowPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class DarklingPatch {
    @SpirePatch(clz = Darkling.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix {
        @SpirePostfixPatch
        public static void PostFix(Darkling __instance) {
            __instance.maxHealth += 15;
            __instance.currentHealth = __instance.maxHealth;
        }
    }

    @SpirePatch(clz = Darkling.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpirePrefixPatch
        public static SpireReturn<Void> PreFix(Darkling __instance){
            if (__instance.nextMove == 5){
                if (MathUtils.randomBoolean()) {
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("DARKLING_REGROW_2",
                            MathUtils.random(-0.1F, 0.1F)));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("DARKLING_REGROW_1",
                            MathUtils.random(-0.1F, 0.1F)));
                }
                AbstractDungeon.actionManager.addToBottom(new HealAction(__instance, __instance, __instance.maxHealth));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(__instance, "REVIVE"));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new RegrowPower(__instance), 1));
                for (AbstractRelic r : AbstractDungeon.player.relics)
                    r.onSpawnMonster(__instance);
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(__instance));
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
}

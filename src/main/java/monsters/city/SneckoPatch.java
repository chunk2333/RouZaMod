package monsters.city;
//异蛇：开局给你斋戒，加5血。
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Snecko;
import com.megacrit.cardcrawl.powers.watcher.EnergyDownPower;
import com.megacrit.cardcrawl.vfx.combat.FastingEffect;

public class SneckoPatch {
    @SpirePatch(clz = Snecko.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {float.class, float.class})
    public static class HpFix {
        @SpirePostfixPatch
        public static void PostFix(Snecko __instance) {
            __instance.maxHealth += 5;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = AbstractMonster.class, method = "usePreBattleAction")
    public static class PreBattleActionFix{
        @SpirePrefixPatch
        public static void PreFix(AbstractMonster __instance){
            if (__instance instanceof Snecko) {
                AbstractPlayer p = AbstractDungeon.player;
                if (p != null)
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new FastingEffect(p.hb.cX, p.hb.cY, Color.CHARTREUSE)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, __instance, new EnergyDownPower(p, 1, true), 1));
            }

        }
    }
}

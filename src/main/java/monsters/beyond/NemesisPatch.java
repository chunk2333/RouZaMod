package monsters.beyond;
//天罚：加20血，获得仪式buff，塞牌同三柱。
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Nemesis;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import helpers.RouZaHelper;

public class NemesisPatch {
    @SpirePatch(clz = Nemesis.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix {
        @SpirePostfixPatch
        public static void PostFix(Nemesis __instance) {
            __instance.maxHealth += 20;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = AbstractMonster.class, method = "usePreBattleAction")
    public static class PreBattleActionFix{
        @SpirePrefixPatch
        public static void PreFix(AbstractMonster __instance){
            if (__instance instanceof Nemesis) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new RitualPower(__instance, 1, false), 1));
            }

        }
    }
    @SpirePatch(clz = Nemesis.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpirePrefixPatch
        public static SpireReturn<Void> PreFix(Nemesis __instance){
            if (__instance.nextMove == 4){
                AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_NEMESIS_1C"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(__instance, new ShockWaveEffect(__instance.hb.cX, __instance.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 1.5F));
                for(int i = 0; i < 3;i++){
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(RouZaHelper.getRandomStatusOrCurseCard(), 1));
                }
                if (AbstractDungeon.ascensionLevel >= 18) {
                    for(int i = 0; i < 2;i++){
                        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(RouZaHelper.getRandomStatusOrCurseCard(), 1));
                    }
                }
                if (!__instance.hasPower("Intangible")){
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new IntangiblePower(__instance, 1)));
                }
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(__instance));
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();

        }

    }
}

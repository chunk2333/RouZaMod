package monsters.exordium;
//守护者，加10血，受到打出攻击牌反伤的效果：减少1点最大生命值，开局获得1力量。
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.TheGuardian;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.OfferingEffect;

public class TheGuardianPatch {
    @SpirePatch(clz = TheGuardian.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix{
        @SpirePostfixPatch
        public static void PostFix(TheGuardian __instance){
            __instance.maxHealth += 10;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = TheGuardian.class, method = "usePreBattleAction")
    public static class usePreBattleAction{
        @SpirePrefixPatch
        public static void PreFix(TheGuardian __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new StrengthPower(__instance, 1), 1));
        }
    }
    @SpirePatch(clz = AbstractPower.class, method = "onAttacked")
    public static class onAttackedFix{
        @SpireInsertPatch(rloc = 0, localvars = {"info", "damageAmount"})
        public static SpireReturn<Integer> Fix(AbstractPower __instance, DamageInfo ___info, int ___damageAmount){
            if(___info.owner == __instance.owner){
                return SpireReturn.Return(___damageAmount);
            }
            if(!__instance.ID.equals("Sharp Hide")){
                return SpireReturn.Return(___damageAmount);
            }
            if(AbstractDungeon.player.currentBlock <= ___damageAmount){
                if (Settings.FAST_MODE) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new OfferingEffect(), 0.1F));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new OfferingEffect(), 0.5F));
                }
                AbstractDungeon.player.decreaseMaxHealth(1);
            }
            return SpireReturn.Return(___damageAmount);
        }

    }
//    @SpirePatch(clz = SharpHidePower.class, method = "onUseCard")
//    public static class SharpHidePowerFix{
//        @SpireInsertPatch(loc = 39)
//        public static void Fix(SharpHidePower __instance){
//            int num = __instance.amount;
//            if(AbstractDungeon.player.hasRelic("TungstenRod")){
//                num -= 1;
//            }
//            if(AbstractDungeon.player.currentBlock <= num){
//                if (Settings.FAST_MODE) {
//                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new OfferingEffect(), 0.1F));
//                } else {
//                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new OfferingEffect(), 0.5F));
//                }
//                AbstractDungeon.player.decreaseMaxHealth(1);
//            }
//        }
//    }
}

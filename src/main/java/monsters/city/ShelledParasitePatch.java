package monsters.city;
//带壳寄生怪，每回合开始获得一层多层护甲（战斗开始除外），攻击全带吸血（全额）。
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.ShelledParasite;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

public class ShelledParasitePatch {
    @SpirePatch(clz = ShelledParasite.class, method = "takeTurn")
    public static class takeTurnFix{

        @SpirePrefixPatch
        public static void PreFix01(ShelledParasite __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new PlatedArmorPower(__instance, 1)));
        }


        @SpireInsertPatch(loc = 105)
        public static void InsertFix01(ShelledParasite __instance){
            AbstractDungeon.actionManager.addToBottom(new HealAction(__instance, __instance, __instance.damage.get(1).base));
        }
        @SpireInsertPatch(loc = 118)
        public static void InsertFix02(ShelledParasite __instance){
            AbstractDungeon.actionManager.addToBottom(new HealAction(__instance, __instance, __instance.damage.get(0).base));
        }
    }
}

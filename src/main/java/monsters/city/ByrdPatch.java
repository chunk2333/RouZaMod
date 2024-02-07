package monsters.city;
//异鸟，开局获得一层好奇。
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.Byrd;
import com.megacrit.cardcrawl.powers.CuriosityPower;

public class ByrdPatch {
    @SpirePatch(clz = Byrd.class, method = "usePreBattleAction")
    public static class usePreBattleAction{
        @SpirePrefixPatch
        public static void PreFix(Byrd __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new CuriosityPower(__instance, 1)));
        }
    }
}

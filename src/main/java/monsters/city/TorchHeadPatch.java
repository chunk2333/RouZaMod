package monsters.city;
//火炬头：攻击时会偷取一项buff（最多仨），加10血
import actions.StealBuffAction;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.TorchHead;

public class TorchHeadPatch {
    @SpirePatch(clz = TorchHead.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix {
        @SpirePostfixPatch
        public static void PostFix(TorchHead __instance) {
            __instance.maxHealth += 10;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = TorchHead.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 62)
        public static void InsertFix01(TorchHead __instance){
            if(__instance.powers.size() < 5){
                AbstractDungeon.actionManager.addToBottom(new StealBuffAction(__instance, 1));
            }
        }
    }
}

package monsters.beyond;
//倏忽魔：开局获得2层缓冲，后续每次攻击后都会获得一层缓冲。
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.beyond.Transient;
import com.megacrit.cardcrawl.powers.BufferPower;

public class TransientPatch {
    @SpirePatch(clz = Transient.class, method = "usePreBattleAction")
    public static class usePreBattleAction{
        @SpirePrefixPatch
        public static void PreFix(Transient __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new BufferPower(__instance, 2), 2));
        }
    }
    @SpirePatch(clz = Transient.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 83)
        public static void InsertFix01(Transient __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new BufferPower(__instance, 1), 1));
        }
    }
}

package monsters.beyond;
//拜蛇术士：开局获得疼痛戳刺，加10血。
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.beyond.Reptomancer;
import com.megacrit.cardcrawl.powers.PainfulStabsPower;

public class ReptomancerPatch {
    @SpirePatch(clz = Reptomancer.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix {
        @SpirePostfixPatch
        public static void PostFix(Reptomancer __instance) {
            __instance.maxHealth += 10;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = Reptomancer.class, method = "usePreBattleAction")
    public static class usePreBattleAction{
        @SpirePrefixPatch
        public static void PreFix(Reptomancer __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new PainfulStabsPower(__instance)));
        }
    }
}

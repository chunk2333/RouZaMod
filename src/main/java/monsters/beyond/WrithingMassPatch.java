package monsters.beyond;
//扭曲团块：加10血，死亡时固定塞一张寄生到牌组。
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.curses.Parasite;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.WrithingMass;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class WrithingMassPatch {
    @SpirePatch(clz = WrithingMass.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix {
        @SpirePostfixPatch
        public static void PostFix(WrithingMass __instance) {
            __instance.maxHealth += 10;
            __instance.currentHealth = __instance.maxHealth;
        }
    }
    @SpirePatch(clz = WrithingMass.class, method = "usePreBattleAction")
    public static class PreBattleActionFix{
        @SpirePrefixPatch
        public static void PreFix(WrithingMass __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new BarricadePower(__instance)));
        }
    }
    @SpirePatch(clz = AbstractMonster.class, method = "die", paramtypez = boolean.class)
    public static class dieFix{
        @SpirePostfixPatch
        public static void PostFix(AbstractMonster __instance){
            if (__instance instanceof WrithingMass){
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Parasite(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            }
        }
    }
}

package monsters.city;
//扎人的书，加10血，塞牌同三柱神的塞牌逻辑。
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.BookOfStabbing;
import com.megacrit.cardcrawl.powers.PainfulStabsPower;
import helpers.RouZaHelper;

public class BookOfStabbingPatch {
    @SpirePatch(clz = BookOfStabbing.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix {
        @SpirePostfixPatch
        public static void PostFix(BookOfStabbing __instance) {
            __instance.maxHealth += 10;
            __instance.currentHealth = __instance.maxHealth;
        }
    }

    @SpirePatch(clz = PainfulStabsPower.class, method = "onInflictDamage")
    public static class takeTurnFix {
        @SpireInsertPatch(loc = 32)
        public static SpireReturn<Void> InsertFix01(PainfulStabsPower __instance) {
            if(__instance.owner.id.equals("BookOfStabbing")){
                AbstractCard c;
                if(AbstractDungeon.monsterRng.randomBoolean()){
                    c = RouZaHelper.getRandomCurseCard();
                } else {
                    c = RouZaHelper.getRandomStatusCard();
                }
                if(c.cardID.equals("Burn")){
                    if(AbstractDungeon.monsterRng.randomBoolean()){
                        c.upgrade();
                    }
                }
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(c, 1));
                return SpireReturn.Return();
            }

            return SpireReturn.Continue();
        }
    }
}

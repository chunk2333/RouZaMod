package monsters.exordium;
//火大地精，每次攻击时都会将一张随机诅咒牌加入到抽牌堆顶部。
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.GremlinWarrior;
import helpers.RouZaHelper;

public class GremlinWarriorPatch {
    @SpirePatch(clz = GremlinWarrior.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 77)
        public static void InsertFix(GremlinWarrior __instance){
            AbstractCard c = RouZaHelper.getRandomCurseCard();
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c, 1, false, true));
        }
    }
}

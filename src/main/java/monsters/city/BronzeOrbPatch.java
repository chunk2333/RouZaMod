package monsters.city;
//铜球：偷牌时会额外偷一张。
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.unique.ApplyStasisAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.BronzeOrb;

public class BronzeOrbPatch {
    @SpirePatch(clz = BronzeOrb.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 76)
        public static void InsertFix(BronzeOrb __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyStasisAction(__instance));
        }
    }
}

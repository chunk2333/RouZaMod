package monsters.city;
//收藏家：你是我的了的攻击会偷一张牌，召唤的火炬头额外+1
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.unique.ApplyStasisAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.TheCollector;
import com.megacrit.cardcrawl.monsters.city.TorchHead;

public class TheCollectorPatch {
    @SpirePatch(clz = TheCollector.class, method = "takeTurn")
    public static class takeTurnFix{

        public static int getNum(){
            int i = 0;
            for (AbstractMonster m :AbstractDungeon.getCurrRoom().monsters.monsters){
                if (m.id.equals("TorchHead")){
                    i+=1;
                }
            }
            return i;
        }

        @SpireInsertPatch(loc = 152)
        public static void InsertFix01(TheCollector __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyStasisAction(__instance));
        }

        @SpireInsertPatch(loc = 126)
        public static void InsertFix02(TheCollector __instance){
            if(getNum() < 3){
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new TorchHead(-700, MathUtils.random(-5.0F, 25.0F)), true));
            }
        }

        @SpireInsertPatch(loc = 180)
        public static void InsertFix03(TheCollector __instance){
            if(getNum() < 3){
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new TorchHead(-700, MathUtils.random(-5.0F, 25.0F)), true));
            }
        }
    }
}

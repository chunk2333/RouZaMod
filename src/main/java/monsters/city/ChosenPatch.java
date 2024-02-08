package monsters.city;
//被拣选者，上蟹粥buff的同时随机召唤一只邪教徒或者异鸟。
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.Byrd;
import com.megacrit.cardcrawl.monsters.city.Chosen;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;

public class ChosenPatch {

    @SpirePatch(clz = Chosen.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 134)
        public static void InsertFix01(Chosen __instance){
            if(AbstractDungeon.monsterRng.randomBoolean()){
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new Cultist(-500.0F, 0.0F), false));
            }else {
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new Byrd(-500.0F, 0.0F), false));
            }

        }
    }
}

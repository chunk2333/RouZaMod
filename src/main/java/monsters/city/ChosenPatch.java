package monsters.city;
//被拣选者，上蟹粥buff的同时随机召唤一只邪教徒或者异鸟。
import actions.MonsterUsePreBattleAction;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Byrd;
import com.megacrit.cardcrawl.monsters.city.Chosen;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;

public class ChosenPatch {

    @SpirePatch(clz = Chosen.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpireInsertPatch(loc = 134)
        public static void InsertFix01(Chosen __instance){
            AbstractMonster m;
            if(AbstractDungeon.monsterRng.randomBoolean()){
                m = new Cultist(-500.0F, 0.0F);
            }else {
                m = new Byrd(-500.0F, 0.0F);
            }
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(m, false));
            AbstractDungeon.actionManager.addToBottom(new MonsterUsePreBattleAction(m));
        }
    }
}

package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MonsterUsePreBattleAction extends AbstractGameAction {
    private AbstractMonster m;
    public MonsterUsePreBattleAction(AbstractMonster m){
        this.m = m;
    }

    @Override
    public void update(){
        m.usePreBattleAction();
        this.tickDuration();
    }
}

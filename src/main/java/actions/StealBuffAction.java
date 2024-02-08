package actions;
//怪物专属偷取一项正面buff
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class StealBuffAction extends AbstractGameAction {

    private final AbstractMonster m;

    private final AbstractPlayer p = AbstractDungeon.player;

    private int num = 1;

    private int count;

    public StealBuffAction(AbstractMonster toGive, int count){
        this.m = toGive;
        this.count = count;
    }

    @Override
    public void update(){

        if (p.powers.isEmpty()){
            tickDuration();
            return;
        }


        for(int i = 0; i < this.count; i++){
            int random;
            int buffSize = 0;
            ArrayList<AbstractPower> powersBuff = new ArrayList();
            AbstractPower FinalBuff = null;
            //addToBot(new WaitActionYibaAction(0.5F));
            for (AbstractPower power : p.powers){
                if (power.type == AbstractPower.PowerType.BUFF){
                    buffSize += 1;
                    powersBuff.add(power);
                }
            }

            if(buffSize == 0){
                tickDuration();
                return;
            }
            if (buffSize > 1){
                random = AbstractDungeon.monsterRng.random(0, buffSize - 1);
                FinalBuff = powersBuff.get(random);
            } else {
                FinalBuff = powersBuff.get(0);
            }

            FinalBuff.owner = m;
            addToBot(new RemoveSpecificPowerAction(p, p, FinalBuff.ID));
            addToBot(new ApplyPowerAction(m, m, FinalBuff));
            //addToBot(new WaitActionYibaAction(0.5F));
            tickDuration();
        }



    }
}

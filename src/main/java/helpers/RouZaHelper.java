package helpers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;
import java.util.Map;

public class RouZaHelper {

    public static ArrayList<AbstractCard> statusCards = new ArrayList<>();

    public static AbstractCard getRandomCurseCard(){
        //取随机诅咒牌
        ArrayList<AbstractCard> list = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.curseCardPool.group) {
            if(c.type== AbstractCard.CardType.CURSE){
                list.add(c);
            }
        }
        return list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1));
    }
    public static AbstractCard getRandomStatusCard(){
        //取随机状态牌
        ArrayList<AbstractCard> list = new ArrayList<>();
        if(statusCards.isEmpty()){
            for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
                AbstractCard card = c.getValue();
                statusCards.add(card);
            }
        }

        for (AbstractCard c : statusCards) {
            if(c.type == AbstractCard.CardType.STATUS){
                list.add(c);
            }
        }
        return list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1));
    }

    public static AbstractCard getRandomStatusOrCurseCard(){
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
        return c;
    }
}

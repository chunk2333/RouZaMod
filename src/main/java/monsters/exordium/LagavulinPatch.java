package monsters.exordium;
//乐加维林，睡觉回合结束后，会给你塞两张眩晕到弃牌堆。
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.animations.FastShakeAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.Lagavulin;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

@SpirePatch(clz = Lagavulin.class, method = "takeTurn")
public class LagavulinPatch {
    @SpireInsertPatch(loc = 156)
    public static void InsertFix01(Lagavulin __instance){
        add(__instance);
    }

    @SpireInsertPatch(loc = 160)
    public static void InsertFix02(Lagavulin __instance){
        add(__instance);
    }

    public static void add(Lagavulin la){
        AbstractDungeon.actionManager.addToBottom(new SFXAction("THUNDERCLAP"));
        if (!Settings.FAST_MODE) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(la, new ShockWaveEffect(la.hb.cX, la.hb.cY, Color.ROYAL, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.5F));
            AbstractDungeon.actionManager.addToBottom(new FastShakeAction(AbstractDungeon.player, 0.6F, 0.2F));
        } else {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(la, new ShockWaveEffect(la.hb.cX, la.hb.cY, Color.ROYAL, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.1F));
            AbstractDungeon.actionManager.addToBottom(new FastShakeAction(AbstractDungeon.player, 0.6F, 0.15F));
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Dazed(), 2));
    }
}

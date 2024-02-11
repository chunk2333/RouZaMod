package monsters.beyond;
//八体
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Deca;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RegrowPower;
import com.megacrit.cardcrawl.powers.SurroundedPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class DecaPatch {
    @SpirePatch(clz = Deca.class, method = "usePreBattleAction")
    public static class PreBattleActionFix{
        @SpirePrefixPatch
        public static void PreFix(Deca __instance){
            __instance.drawX = (float) Settings.WIDTH * 0.75F + -1000 * Settings.xScale;
            __instance.drawY = AbstractDungeon.floorY + 15 * Settings.yScale;
            __instance.flipHorizontal = true;
            AbstractDungeon.player.movePosition(Settings.WIDTH / 2.0F, 340.0F * Settings.yScale);
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new SurroundedPower(AbstractDungeon.player)));
            (AbstractDungeon.getCurrRoom()).cannotLose = true;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new RegrowPower(__instance)));
        }
    }
    @SpirePatch(clz = Deca.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpirePrefixPatch
        public static void PreFix(Deca __instance){
            if (__instance.nextMove == 114){
                MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Darkling");
                String[] DIALOG = monsterStrings.DIALOG;
                AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(__instance, DIALOG[0]));
            }
            if (__instance.nextMove == 115){
                if (MathUtils.randomBoolean()) {
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("DARKLING_REGROW_2",
                            MathUtils.random(-0.1F, 0.1F)));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("DARKLING_REGROW_1",
                            MathUtils.random(-0.1F, 0.1F)));
                }
                AbstractDungeon.actionManager.addToBottom(new HealAction(__instance, __instance, __instance.maxHealth / 2));
                __instance.halfDead = false;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new RegrowPower(__instance), 1));
                for (AbstractRelic r : AbstractDungeon.player.relics)
                    r.onSpawnMonster(__instance);
            }
        }
    }
    @SpirePatch(clz = Deca.class, method = "getMove")
    public static class getMove{
        @SpirePrefixPatch
        public static SpireReturn<Void> PreFix01(Deca __instance){
            if (__instance.halfDead) {
                __instance.setMove((byte)115, AbstractMonster.Intent.BUFF);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

    }
    @SpirePatch(clz = Deca.class, method = "damage")
    public static class damage{
        @SpireInsertPatch(loc = 92, localvars = {"info"})
        public static SpireReturn<Void> PreFix(Deca __instance, DamageInfo ___info){
            if (__instance.currentHealth <= 0 && !__instance.halfDead) {
                __instance.halfDead = true;
                for (AbstractPower p : __instance.powers)
                    p.onDeath();
                for (AbstractRelic r : AbstractDungeon.player.relics)
                    r.onMonsterDeath(__instance);
                __instance.powers.clear();
                boolean allDead = true;
                for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                    if (m.id.equals("Donu") && !m.halfDead)
                        allDead = false;
                }
                if (!allDead) {
                    if (__instance.nextMove != 114) {
                        __instance.setMove((byte)114, AbstractMonster.Intent.UNKNOWN);
                        __instance.createIntent();
                        AbstractDungeon.actionManager.addToBottom(new SetMoveAction(__instance, (byte)114, AbstractMonster.Intent.UNKNOWN));
                    }
                } else {
                    (AbstractDungeon.getCurrRoom()).cannotLose = false;
                    __instance.halfDead = false;
                    for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters)
                        m.die();
                }
                return SpireReturn.Return();
            } else if (___info.owner != null && ___info.type != DamageInfo.DamageType.THORNS && ___info.output > 0) {
                __instance.state.setAnimation(0, "Hit", false);
                __instance.state.addAnimation(0, "Idle", true, 0.0F);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
    @SpirePatch(clz = Deca.class, method = "die")
    public static class die{
        @SpirePrefixPatch
        public static SpireReturn<Void> PreFix(Deca __instance){
            if (!(AbstractDungeon.getCurrRoom()).cannotLose){
                for (AbstractMonster m : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                    if (!m.isDead && !m.isDying) {
                        if (AbstractDungeon.player.hasPower("Surrounded")) {
                            AbstractDungeon.player.flipHorizontal = (m.drawX < AbstractDungeon.player.drawX);
                            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, "Surrounded"));
                        }
                        if (m.hasPower("BackAttack"))
                            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, m, "BackAttack"));
                    }
                }
                return SpireReturn.Continue();
            } else {
                AbstractDungeon.player.flipHorizontal = false;
                return SpireReturn.Return();
            }
        }
    }
}

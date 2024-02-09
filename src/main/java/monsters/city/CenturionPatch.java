package monsters.city;
//百夫长：加5血，获得生命链接，百夫长多段后获得一层幻杀。
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Centurion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PhantasmalPower;
import com.megacrit.cardcrawl.powers.RegrowPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class CenturionPatch {
    @SpirePatch(clz = Centurion.class, method = SpirePatch.CONSTRUCTOR)
    public static class HpFix {
        @SpirePostfixPatch
        public static void PostFix(Centurion __instance) {
            __instance.maxHealth += 5;
            __instance.currentHealth = __instance.maxHealth;
        }
    }

    @SpirePatch(clz = AbstractMonster.class, method = "usePreBattleAction")
    public static class PreBattleActionFix{
        @SpirePrefixPatch
        public static void PreFix(AbstractMonster __instance){
            if (__instance instanceof Centurion) {
                (AbstractDungeon.getCurrRoom()).cannotLose = true;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new RegrowPower(__instance)));
            }

        }
    }


    @SpirePatch(clz = Centurion.class, method = "takeTurn")
    public static class takeTurnFix{
        @SpirePrefixPatch
        public static void PreFix(Centurion __instance){
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
        @SpireInsertPatch(loc = 85)
        public static void InsertFix(Centurion __instance){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new PhantasmalPower(__instance, 1), 1));
        }
    }
    @SpirePatch(clz = Centurion.class, method = "getMove")
    public static class getMove{
        @SpirePrefixPatch
        public static SpireReturn<Void> PreFix(Centurion __instance){
            if (__instance.halfDead) {
                __instance.setMove((byte)115, AbstractMonster.Intent.BUFF);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
        @SpireInsertPatch(loc = 129, localvars = {"furyHits"})
        public static SpireReturn<Void> InsertFix01(Centurion __instance, int ___furyHits){
            int i = 0;
            for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                if (!m.halfDead)
                    i++;
            }
            if (i > 1) {
                __instance.setMove((byte)2, AbstractMonster.Intent.DEFEND);
                return SpireReturn.Return();
            }
            __instance.setMove((byte)3, AbstractMonster.Intent.ATTACK, (__instance.damage.get(1)).base, ___furyHits, true);
            return SpireReturn.Return();
        }

        @SpireInsertPatch(loc = 151, localvars = {"furyHits"})
        public static SpireReturn<Void> InsertFix02(Centurion __instance, int ___furyHits){
            int aliveCount = 0;
            for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                if (!m.halfDead)
                    aliveCount++;
            }
            if (aliveCount > 1) {
                __instance.setMove((byte)2, AbstractMonster.Intent.DEFEND);
                return SpireReturn.Return();
            }
            __instance.setMove((byte)3, AbstractMonster.Intent.ATTACK, (__instance.damage.get(1)).base, ___furyHits, true);
            return SpireReturn.Continue();
        }
    }


    @SpirePatch(clz = Centurion.class, method = "damage")
    public static class damage{
        @SpireInsertPatch(loc = 173, localvars = {"info"})
        public static SpireReturn<Void> PreFix(Centurion __instance, DamageInfo ___info){
            if (__instance.currentHealth <= 0 && !__instance.halfDead) {
                __instance.halfDead = true;
                for (AbstractPower p : __instance.powers)
                    p.onDeath();
                for (AbstractRelic r : AbstractDungeon.player.relics)
                    r.onMonsterDeath(__instance);
                __instance.powers.clear();
                boolean allDead = true;
                for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                    if (m.id.equals("Healer") && !m.halfDead)
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



    @SpirePatch(clz = Centurion.class, method = "die")
    public static class die{
        @SpirePrefixPatch
        public static SpireReturn<Void> PreFix(){
            if (!(AbstractDungeon.getCurrRoom()).cannotLose){
                return SpireReturn.Continue();
            } else {
                return SpireReturn.Return();
            }
        }
    }
}

package monsters.exordium;
//邪教徒，第2/3次攻击会给予易伤/虚弱持续一回合，并且修改其意图图标。增加5点血量。
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class CultistPatch {

    @SpirePatch(clz = Cultist.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {float.class, float.class, boolean.class})
    public static class CultistPatchPostFix{
        @SpirePostfixPatch
        public static void PostFix(Cultist __instance){
            __instance.maxHealth += 5;
            __instance.currentHealth = __instance.maxHealth;
        }
    }

    @SpirePatch(clz = Cultist.class, method = "takeTurn")
    public static class CultistPatchInsertFix{
        @SpirePrefixPatch
        public static void PreFix(Cultist __instance){
            boolean hasAwakenedOne = false;
            for (AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters){
                if (m.id.equals("AwakenedOne")) {
                    hasAwakenedOne = true;
                    break;
                }
            }
            for (AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters){
                if (!m.isDead && !m.isDying && hasAwakenedOne){
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, __instance, 7));
                }
            }
        }

        @SpireInsertPatch(loc = 106)
        public static void Insert(Cultist __instance){
            if(GameActionManager.turn == 3){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new VulnerablePower(AbstractDungeon.player, 1, true), 1));
            }

            if(GameActionManager.turn == 4){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new WeakPower(AbstractDungeon.player, 1, true), 1));
            }

        }
    }

    @SpirePatch(clz = Cultist.class, method = "getMove")
    public static class CultistPatchInsertFix1{
        @SpireInsertPatch(loc = 162)
        public static SpireReturn<Void> Insert(Cultist __instance){
            if(GameActionManager.turn == 2 || GameActionManager.turn == 3){
                __instance.setMove((byte)1, AbstractMonster.Intent.ATTACK_DEBUFF, (__instance.damage.get(0)).base);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
}

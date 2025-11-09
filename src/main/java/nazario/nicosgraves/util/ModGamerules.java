package nazario.nicosgraves.util;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class ModGamerules {
    public static final GameRules.Key<GameRules.BooleanRule> SPAWN_PLAYER_GRAVES = GameRuleRegistry.register("nicos_graves:spawn_player_graves", GameRules.Category.PLAYER, GameRules.BooleanRule.create(true));
    public static final GameRules.Key<GameRules.BooleanRule> ONLY_OWNER_ACCESS = GameRuleRegistry.register("nicos_graves:only_owner_access", GameRules.Category.PLAYER, GameRules.BooleanRule.create(false));

    public static void register() {

    }
}

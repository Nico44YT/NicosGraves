package nazario.nicosgraves.util;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class ModGamerules {
    public static final GameRules.Key<GameRules.BooleanRule> SPAWN_PLAYER_GRAVES = GameRuleRegistry.register("nicos_graves:spawn_player_graves", GameRules.Category.PLAYER, GameRules.BooleanRule.create(true));

    public static void register() {

    }
}

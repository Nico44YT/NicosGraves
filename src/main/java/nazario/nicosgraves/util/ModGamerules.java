package nazario.nicosgraves.util;

import net.minecraft.world.GameRules;

public class ModGamerules {
    public static final GameRules.RuleKey<GameRules.BooleanRule> SPAWN_PLAYER_GRAVES = GameRules.register("nicos_graves:spawn_player_graves", GameRules.BooleanRule.create(true));

    public static void register() {

    }
}

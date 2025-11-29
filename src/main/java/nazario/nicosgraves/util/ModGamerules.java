package nazario.nicosgraves.util;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.EnumRule;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.world.GameRules;

public class ModGamerules {
    public static final GameRules.Key<GameRules.BooleanRule> SPAWN_PLAYER_GRAVES = GameRuleRegistry.register("nicos_graves:spawn_player_graves", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<EnumRule<GraveAccessibility>> GRAVE_ACCESS = GameRuleRegistry.register("nicos_graves:grave_access", GameRules.Category.PLAYER, GameRuleFactory.createEnumRule(GraveAccessibility.EVERYONE));

    public static void register() {

    }

    public enum GraveAccessibility implements StringIdentifiable {
        OWNER_ONLY("owner_only"),
        TEAM_ONLY("team_only"),
        EVERYONE("everyone");

        final String value;
        GraveAccessibility(String value) {
            this.value = value;
        }


        @Override
        public String toString() {
            return value;
        }

        @Override
        public String asString() {
            return value;
        }
    }
}
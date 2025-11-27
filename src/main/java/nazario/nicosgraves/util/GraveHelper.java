package nazario.nicosgraves.util;

import nazario.nicosgraves.entity.custom.PlayerGraveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.world.ServerWorld;

public class GraveHelper {
    public static boolean hasPlayerAccess(PlayerGraveEntity grave, PlayerEntity player, ServerWorld serverWorld) {

        return switch(serverWorld.getGameRules().get(ModGamerules.GRAVE_ACCESS).get()) {
            case OWNER_ONLY -> grave.getOwnerUUID().equals(player.getUuid());
            case TEAM_ONLY -> {
                Team graveTeam = grave.getScoreboardTeam();
                Team playerTeam = player.getScoreboardTeam();

                if(graveTeam == null) yield grave.getOwnerUUID().equals(player.getUuid());

                if(playerTeam != null) {
                    yield graveTeam.equals(playerTeam);
                }

                yield false;
            }
            case EVERYONE -> true;
        };
    }
}

package nazario.nicosgraves;

import nazario.nicosgraves.entity.ModEntities;
import nazario.nicosgraves.util.ModGamerules;
import nazario.nicosgraves.util.ModTags;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NicosGraves implements ModInitializer {

    public static final String MOD_ID = "nicos_graves";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModEntities.register();
        ModGamerules.register();
        ModTags.register();
    }

    public static Identifier id(String name) {
        return new Identifier(MOD_ID, name);
    }
}

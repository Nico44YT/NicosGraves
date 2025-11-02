package nazario.nicosgraves;

import nazario.nicosgraves.entity.ModEntities;
import nazario.nicosgraves.util.ModGamerules;
import nazario.nicosgraves.util.ModTags;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class NicosGraves implements ModInitializer {

    public static final String MOD_ID = "nicos_graves";

    @Override
    public void onInitialize() {
        ModEntities.register();
        ModGamerules.register();
        ModTags.register();
    }

    public static Identifier id(String name) {
        //? if >=1.19 {
        return Identifier.of(MOD_ID, name);
        //?} else {
        /*return new Identifier(MOD_ID, name);
        *///?}
    }
}

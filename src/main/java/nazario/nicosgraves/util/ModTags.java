package nazario.nicosgraves.util;

import nazario.nicosgraves.NicosGraves;
import net.minecraft.item.Item;
//? >=1.19.3 {
/*import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
*///?} else {
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
//?}

public class ModTags {
    public static class ItemTags {
        //? >=1.19.3 {
        /*public static TagKey<Item> SOULBOUND_ITEMS = TagKey.of(RegistryKeys.ITEM, NicosGraves.id("soulbound"));
        *///?} else {
        public static TagKey<Item> SOULBOUND_ITEMS = TagKey.of(Registry.ITEM_KEY, NicosGraves.id("soulbound"));
        //?}

        public static void register() {

        }
    }

    public static void register() {
        ItemTags.register();
    }
}

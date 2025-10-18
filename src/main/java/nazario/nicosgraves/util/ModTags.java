package nazario.nicosgraves.util;

import nazario.nicosgraves.NicosGraves;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModTags {
    public static class ItemTags {
        public static TagKey<Item> SOULBOUND_ITEMS = TagKey.of(RegistryKeys.ITEM, NicosGraves.id("soulbound"));

        public static void register() {

        }
    }

    public static void register() {
        ItemTags.register();
    }
}

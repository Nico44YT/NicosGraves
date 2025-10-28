package nazario.nicosgraves.util;

import nazario.nicosgraves.NicosGraves;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

public class ModTags {
    public static class ItemTags {
        public static TagKey<Item> SOULBOUND_ITEMS = TagKey.of(Registry.ITEM_KEY, NicosGraves.id("soulbound"));

        public static void register() {

        }
    }

    public static void register() {
        ItemTags.register();
    }
}

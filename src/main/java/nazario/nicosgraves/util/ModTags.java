package nazario.nicosgraves.util;

import nazario.nicosgraves.NicosGraves;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;

public class ModTags {
    public static class ItemTags {
        public static Tag<Item> SOULBOUND_ITEMS = TagFactory.ITEM.create(NicosGraves.id("soulbound"));

        public static void register() {

        }
    }

    public static void register() {
        ItemTags.register();
    }
}

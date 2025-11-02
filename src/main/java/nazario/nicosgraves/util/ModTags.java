package nazario.nicosgraves.util;

import nazario.nicosgraves.NicosGraves;
import net.minecraft.item.Item;

//? if >= 1.19.3 {
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
//?} else if >=1.18.2 {
//import net.minecraft.tag.TagKey;
//?} else if >=1.17.1 {
/*import net.minecraft.tag.Tag;
import net.fabricmc.fabric.api.tag.TagFactory;
*///?} else if >=1.17 {
//import net.minecraft.tag.Tag;
//?}

public class ModTags {
    public static class ItemTags {
        //? if >=1.20 {
        public static TagKey<Item> SOULBOUND_ITEMS = TagKey.of(RegistryKeys.ITEM, NicosGraves.id("soulbound"));
        //?} else if >=1.18.2 {
        //public static TagKey<Item> SOULBOUND_ITEMS = TagKey.of(Registry.ITEM_KEY, NicosGraves.id("soulbound"));
        //?} else if >=1.17.1 {
        /*public static Tag<Item> SOULBOUND_ITEMS = TagFactory.ITEM.create(NicosGraves.id("soulbound"));
        *///?} else if >=1.17 {
        //public static Tag<Item> SOULBOUND_ITEMS = TagRegistry.item(NicosGraves.id("soulbound"));
        //?}

        public static void register() {

        }
    }

    public static void register() {
        ItemTags.register();
    }
}

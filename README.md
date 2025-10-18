![Created for Remnants SMP](https://cdn.modrinth.com/data/cached_images/199461d5e18f8ecddc82a29008a2889edea3ffb7_0.webp)
[![Requires Liby](https://cdn.modrinth.com/data/UsIxG2yq/images/a2ddcc545dd795a1b14d5ceb4b5eaa9d7780264b.png)](https://www.modrinth.com/mod/liby)

![Showcase](https://github.com/Nico44YT/NicosGraves/blob/fabric-1.20.1/promo/NicosGraves-Showcase.gif?raw=true)

Is the chance of losing all your items in a lava pit too punishing for you?

#### Generel Info
- The graves can be toggled with the gamerule `nicos_graves:spawn_player_graves`.
- [Trinkets](https://modrinth.com/mod/trinkets) is automatically supported.

### Developer/Modpack Info
To make an item soulbound, essentially making the item be kept on death, the item can be added to the tag `data/nicos_graves/tags/items/soulbound.json`.

```json
{
  "replace": false,
  "values": [
    "mod_id:my_cool_item"
  ]
}
```

Alternatively the item class can implement the interface `SoulboundItem`, and override the `isRetained` method.

```java
package you.your_mod.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nazario.nicosgraves.api.SoulboundItem;

public class MyCoolItem extends Item implements SoulboundItem {

    public MyCoolItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isRetained(ItemStack stack, PlayerEntity player, World world) {
        return true;
    }
}
```




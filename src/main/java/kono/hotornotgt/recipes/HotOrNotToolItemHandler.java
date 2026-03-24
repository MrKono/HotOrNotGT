package kono.hotornotgt.recipes;

import net.minecraft.item.ItemStack;

import com.buuz135.hotornot.item.ModItems;

import gregtech.api.GregTechAPI;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.ConfigHolder;

import kono.hotornotgt.common.items.HotOrNotGTToolItems;

public class HotOrNotToolItemHandler {

    public static void init() {
        override();
        for (Material material : GregTechAPI.materialManager.getRegisteredMaterials()) {
            processTongsRecipe(material);
        }
    }

    private static void override() {
        ModHandler.removeRecipeByOutput(new ItemStack(ModItems.IRON_TONGS));

        if (ConfigHolder.recipes.hardToolArmorRecipes) {
            ModHandler.addShapedRecipe(true, "hot_or_not_tongs", new ItemStack(ModItems.IRON_TONGS),
                    "I I", "dIf", "W W",
                    'I', OreDictUnifier.get(OrePrefix.plate, Materials.Iron),
                    'W', OreDictUnifier.get(OrePrefix.stick, Materials.Wood));

        } else {
            ModHandler.addShapedRecipe(true, "hot_or_not_tongs", new ItemStack(ModItems.IRON_TONGS),
                    "I I", " I ", "W W",
                    'I', OreDictUnifier.get(OrePrefix.ingot, Materials.Iron),
                    'W', OreDictUnifier.get(OrePrefix.stick, Materials.Wood));
        }
    }

    private static void processTongsRecipe(Material material) {
        if (!material.hasProperty(PropertyKey.TOOL)) return;

        if (material == Materials.Flint) return;

        if (!material.hasFlag(MaterialFlags.GENERATE_PLATE)) return;

        if (ConfigHolder.recipes.hardToolArmorRecipes) {
            ModHandler.addShapedRecipe(true, String.format("tongs_%s", material.getName()),
                    HotOrNotGTToolItems.TONGS.get(material), "P P", "dSf", "W W",
                    'P', OreDictUnifier.get(OrePrefix.plate, material),
                    'S', OreDictUnifier.get(OrePrefix.screw, material),
                    'W', OreDictUnifier.get(OrePrefix.stick, Materials.Wood));
        } else {
            ModHandler.addShapedRecipe(true, String.format("tongs_%s", material.getName()),
                    HotOrNotGTToolItems.TONGS.get(material), "P P", " S ", "W W",
                    'P',
                    OreDictUnifier.get(material.hasProperty(PropertyKey.INGOT) ? OrePrefix.ingot : OrePrefix.gem,
                            material),
                    'S', OreDictUnifier.get(OrePrefix.screw, material),
                    'W', OreDictUnifier.get(OrePrefix.stick, Materials.Wood));
        }
    }
}

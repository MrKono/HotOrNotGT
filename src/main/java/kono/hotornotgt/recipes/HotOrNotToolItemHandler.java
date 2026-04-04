package kono.hotornotgt.recipes;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.api.items.toolitem.IGTTool;
import gregtech.common.items.ToolItems;
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
import net.minecraft.item.crafting.Ingredient;

import static gregtech.loaders.recipe.handlers.ToolRecipeHandler.powerUnitItems;

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
                    'W', OreDictUnifier.get(OrePrefix.stick, material));
        } else {
            ModHandler.addShapedRecipe(true, String.format("tongs_%s", material.getName()),
                    HotOrNotGTToolItems.TONGS.get(material), "P P", " S ", "W W",
                    'P',
                    OreDictUnifier.get(material.hasProperty(PropertyKey.INGOT) ? OrePrefix.ingot : OrePrefix.gem,
                            material),
                    'S', OreDictUnifier.get(OrePrefix.screw, material),
                    'W', OreDictUnifier.get(OrePrefix.stick, material));
        }
        addElectricTongsRecipe(material, new IGTTool[] {HotOrNotGTToolItems.TONGS_LV, HotOrNotGTToolItems.TONGS_HV, HotOrNotGTToolItems.TONGS_IV});
    }

    private static void addElectricTongsRecipe(Material material, IGTTool[] toolItems) {
        for (IGTTool toolItem : toolItems) {
            int tier = toolItem.getElectricTier();
            ItemStack powerUnitStack = powerUnitItems.get(tier).getStackForm();
            IElectricItem powerUnit = powerUnitStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
            ItemStack tool = toolItem.get(material, 0, powerUnit.getMaxCharge());

            if (ConfigHolder.recipes.hardToolArmorRecipes) {
                ModHandler.addShapedEnergyTransferRecipe(String.format("%s_%s", toolItem.getToolId(), material.getName()), tool,
                        Ingredient.fromStacks(powerUnitStack), true, true, "P P", "dSf", "WUW",
                        'P', OreDictUnifier.get(OrePrefix.plate, material),
                        'S', OreDictUnifier.get(OrePrefix.screw, material),
                        'U', powerUnitStack,
                        'W', OreDictUnifier.get(OrePrefix.stick, material));
            } else {
                ModHandler.addShapedEnergyTransferRecipe(String.format("%s_%s", toolItem.getToolId(), material.getName()), tool,
                        Ingredient.fromStacks(powerUnitStack), true, true, "P P", " S ", "WUW",
                        'P',
                        OreDictUnifier.get(material.hasProperty(PropertyKey.INGOT) ? OrePrefix.ingot : OrePrefix.gem,
                                material),
                        'S', OreDictUnifier.get(OrePrefix.screw, material),
                        'U', powerUnitStack,
                        'W', OreDictUnifier.get(OrePrefix.stick, material));
            }
        }
    }
}

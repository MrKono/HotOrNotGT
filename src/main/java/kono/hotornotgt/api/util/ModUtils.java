package kono.hotornotgt.api.util;

import static kono.hotornotgt.api.util.ModValues.energyN;
import static kono.hotornotgt.api.util.ModValues.energyQ;

import net.minecraft.item.ItemStack;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.common.items.MetaItems;

public class ModUtils {

    public static boolean isNanoHelm(ItemStack stack) {
        ItemStack armor = MetaItems.NANO_HELMET.getStackForm();
        return checkArmor(stack, armor, energyN);
    }

    public static boolean isNanoChest(ItemStack stack) {
        ItemStack armor = MetaItems.NANO_CHESTPLATE.getStackForm();
        return checkArmor(stack, armor, energyN);
    }

    public static boolean isNanoChestAdv(ItemStack stack) {
        ItemStack armor = MetaItems.NANO_CHESTPLATE_ADVANCED.getStackForm();
        return checkArmor(stack, armor, energyN);
    }

    public static boolean isNanoLegs(ItemStack stack) {
        ItemStack armor = MetaItems.NANO_LEGGINGS.getStackForm();
        return checkArmor(stack, armor, energyN);
    }

    public static boolean isNanoFeet(ItemStack stack) {
        ItemStack armor = MetaItems.NANO_BOOTS.getStackForm();
        return checkArmor(stack, armor, energyN);
    }

    public static boolean isQuarkHelm(ItemStack stack) {
        ItemStack armor = MetaItems.QUANTUM_HELMET.getStackForm();
        return checkArmor(stack, armor, energyQ);
    }

    public static boolean isQuarkChest(ItemStack stack) {
        ItemStack armor = MetaItems.QUANTUM_CHESTPLATE.getStackForm();;
        return checkArmor(stack, armor, energyQ);
    }

    public static boolean isQuarkChestAdv(ItemStack stack) {
        ItemStack armor = MetaItems.QUANTUM_CHESTPLATE_ADVANCED.getStackForm();
        return checkArmor(stack, armor, energyQ);
    }

    public static boolean isQuarkLegs(ItemStack stack) {
        ItemStack armor = MetaItems.QUANTUM_LEGGINGS.getStackForm();
        return checkArmor(stack, armor, energyQ);
    }

    public static boolean isQuarkFeet(ItemStack stack) {
        ItemStack armor = MetaItems.QUANTUM_BOOTS.getStackForm();
        return checkArmor(stack, armor, energyQ);
    }

    public static boolean checkArmor(ItemStack stack1, ItemStack stack2, long energy) {
        IElectricItem item = stack1.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
        return stack1.getItem() == stack2.getItem() && stack1.getItemDamage() == stack2.getItemDamage() &&
                item != null && (item.getCharge() >= energy);
    }
}

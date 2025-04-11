/*
 * This file is part of Hot or Not.
 *
 * Copyright 2018, Buuz135
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package kono.hotornotgt.mixin;

import static kono.hotornotgt.api.util.ModUtils.*;
import static kono.hotornotgt.api.util.ModValues.energyN;
import static kono.hotornotgt.api.util.ModValues.energyQ;

import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.buuz135.hotornot.server.ServerTick;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.common.ConfigHolder;

import kono.hotornotgt.HotOtNotGTConfig;

@Mixin(value = ServerTick.class, remap = false)
public class MixinServerTick {

    @Inject(
            method = "damageProtectionItem",
            at = @At(value = "RETURN"),
            cancellable = true)
    private static void damageProtectionItemMixin(EntityPlayer player, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ()) {
            ItemStack helm = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
            ItemStack chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
            ItemStack legs = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
            ItemStack feet = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
            List<ItemStack> armors = Arrays.asList(helm, chest, legs, feet);
            int nano = 0;
            int quark = 0;
            if (HotOtNotGTConfig.feature.protectNano) {
                if (HotOtNotGTConfig.nano.protectHelmet && isNanoHelm(helm)) {
                    nano++;
                }
                if (HotOtNotGTConfig.nano.protectChest) {
                    if (HotOtNotGTConfig.nano.requiredAdvancedNanoSuite) {
                        if (isNanoChestAdv(chest)) {
                            nano++;
                        }
                    } else {
                        if (isNanoChest(chest) || isNanoChestAdv(chest)) {
                            nano++;
                        }
                    }
                }
                if (HotOtNotGTConfig.nano.protectLegs && isNanoLegs(legs)) {
                    nano++;
                }
                if (HotOtNotGTConfig.nano.protectBoots && isNanoFeet(feet)) {
                    nano++;
                }
                if (nano >= HotOtNotGTConfig.nano.minimumEquippedNano) {
                    armors.forEach(stack -> {
                        IElectricItem item = stack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
                        if (item != null) {
                            if (isNanoHelm(stack) || isNanoChest(stack) || isNanoLegs(stack) ||
                                    isNanoFeet(stack)) {
                                item.discharge(energyN, ConfigHolder.tools.voltageTierNanoSuit, true, false, false);
                            } else if (isNanoChestAdv(stack)) {
                                item.discharge(energyN, ConfigHolder.tools.voltageTierAdvNanoSuit, true, false, false);
                            }
                        }
                    });
                    cir.setReturnValue(true);
                }
            }
            if (HotOtNotGTConfig.feature.protectQuark) {
                if (HotOtNotGTConfig.quark.protectHelmet && isQuarkHelm(helm)) {
                    quark++;
                }
                if (HotOtNotGTConfig.quark.protectChest) {
                    if (HotOtNotGTConfig.quark.requiredAdvancedQuarkSuite) {
                        if (isQuarkChestAdv(chest)) {
                            quark++;
                        }
                    } else {
                        if (isQuarkChest(chest) || isQuarkChestAdv(chest))
                            quark++;
                    }
                }
                if (HotOtNotGTConfig.quark.protectLegs && isQuarkLegs(legs)) {
                    quark++;
                }
                if (HotOtNotGTConfig.quark.protectBoots && isQuarkFeet(feet)) {
                    quark++;
                }
                if (quark >= HotOtNotGTConfig.quark.minimumEquippedQuark) {
                    armors.forEach(stack -> {
                        IElectricItem item = stack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
                        if (item != null) {
                            if (isQuarkHelm(stack) || isQuarkChest(stack) || isQuarkLegs(stack) ||
                                    isQuarkFeet(stack)) {
                                item.discharge(energyQ, ConfigHolder.tools.voltageTierQuarkTech, true, false, false);
                            } else if (isQuarkChestAdv(stack)) {
                                item.discharge(energyQ, ConfigHolder.tools.voltageTierAdvQuarkTech, true, false, false);
                            }
                        }
                    });
                    cir.setReturnValue(true);
                }
            }
        }
    }
}

package kono.hotornotgt.common;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import gregtech.api.unification.material.event.PostMaterialEvent;

import kono.hotornotgt.api.unification.HotOrNotGTMaterialFlags;
import kono.hotornotgt.api.util.ModValues;

@Mod.EventBusSubscriber(modid = ModValues.modId)
public class HotOrNotEventHandler {

    public HotOrNotEventHandler() {}

    @SubscribeEvent
    public static void registerMaterialsPost(PostMaterialEvent event) {
        HotOrNotGTMaterialFlags.addFlags();
    }
}

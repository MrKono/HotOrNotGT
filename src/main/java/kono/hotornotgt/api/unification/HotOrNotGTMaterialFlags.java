package kono.hotornotgt.api.unification;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.properties.PropertyKey;

public class HotOrNotGTMaterialFlags {

    public static void addFlags() {
        for (Material material : GregTechAPI.materialManager.getRegisteredMaterials()) {
            if (!material.hasProperty(PropertyKey.TOOL)) continue;

            if (!material.hasFlag(MaterialFlags.GENERATE_BOLT_SCREW)) {
                material.addFlags(MaterialFlags.GENERATE_BOLT_SCREW);
            }
        }
    }
}

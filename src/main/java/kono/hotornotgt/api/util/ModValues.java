package kono.hotornotgt.api.util;

import kono.hotornotgt.HotOtNotGTConfig;
import kono.hotornotgt.Tags;

public class ModValues {

    public static final String modName = Tags.MODNAME;
    public static final String modId = Tags.MODID;

    public static final long energyN = HotOtNotGTConfig.nano.energyConsumptionNano;
    public static final long energyQ = HotOtNotGTConfig.quark.energyConsumptionQuark;
}

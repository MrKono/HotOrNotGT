package kono.hotornotgt;

import java.util.List;

import com.google.common.collect.Lists;

import zone.rong.mixinbooter.ILateMixinLoader;

public class HotOrNotGTMixinLoader implements ILateMixinLoader {

    @Override
    public List<String> getMixinConfigs() {
        return Lists.newArrayList("mixins.hotornotgt.json");
    }
}

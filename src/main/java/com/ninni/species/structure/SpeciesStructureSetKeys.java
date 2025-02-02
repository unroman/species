package com.ninni.species.structure;

import com.ninni.species.Species;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.StructureSet;

public class SpeciesStructureSetKeys {
    public static final ResourceKey<StructureSet> WRAPTOR_COOPS = of("wraptor_coops");

    private static ResourceKey<StructureSet> of(String id) {
        return ResourceKey.create(Registry.STRUCTURE_SET_REGISTRY, new ResourceLocation(Species.MOD_ID, id));
    }
}

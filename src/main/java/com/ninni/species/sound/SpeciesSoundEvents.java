package com.ninni.species.sound;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import static com.ninni.species.Species.MOD_ID;

public class SpeciesSoundEvents {

    public static final SoundEvent ENTITY_WRAPTOR_DEATH = register("entity.wraptor.death");
    public static final SoundEvent ENTITY_WRAPTOR_HURT = register("entity.wraptor.hurt");
    public static final SoundEvent ENTITY_WRAPTOR_IDLE = register("entity.wraptor.idle");
    public static final SoundEvent ENTITY_WRAPTOR_AGGRO = register("entity.wraptor.aggro");
    public static final SoundEvent ENTITY_WRAPTOR_AGITATED = register("entity.wraptor.agitated");
    public static final SoundEvent ENTITY_WRAPTOR_ATTACK = register("entity.wraptor.attack");
    public static final SoundEvent ENTITY_WRAPTOR_SHEAR = register("entity.wraptor.shear");
    public static final SoundEvent ENTITY_WRAPTOR_STEP = register("entity.wraptor.step");
    public static final SoundEvent ENTITY_WRAPTOR_FEATHER_LOSS = register("entity.wraptor.feather_loss");
    public static final SoundEvent ENTITY_WRAPTOR_EGG = register("entity.wraptor.egg");

    public static final SoundEvent BLOCK_WRAPTOR_EGG_BREAK = register("block.wraptor_egg.break");
    public static final SoundEvent BLOCK_WRAPTOR_EGG_CRACK = register("block.wraptor_egg.crack");
    public static final SoundEvent BLOCK_WRAPTOR_EGG_HATCH = register("block.wraptor_egg.hatch");

    public static final SoundEvent ITEM_CRACKED_WRAPTOR_EGG_SLURP = register("item.cracked_wraptor_egg.slurp");

    public static final SoundEvent ENTITY_DEEPFISH_DEATH = register("entity.deepfish.death");
    public static final SoundEvent ENTITY_DEEPFISH_HURT = register("entity.deepfish.hurt");
    public static final SoundEvent ENTITY_DEEPFISH_IDLE = register("entity.deepfish.idle");
    public static final SoundEvent ENTITY_DEEPFISH_FLOP = register("entity.deepfish.flop");

    public static final SoundEvent ENTITY_ROOMBUG_DEATH = register("entity.roombug.death");
    public static final SoundEvent ENTITY_ROOMBUG_HURT = register("entity.roombug.hurt");
    public static final SoundEvent ENTITY_ROOMBUG_IDLE = register("entity.roombug.idle");
    public static final SoundEvent ENTITY_ROOMBUG_SNORING = register("entity.roombug.snoring");
    public static final SoundEvent ENTITY_ROOMBUG_EAT = register("entity.roombug.eat");
    public static final SoundEvent ENTITY_ROOMBUG_STEP = register("entity.roombug.step");
    public static final SoundEvent ENTITY_ROOMBUG_GOOFY_AAH_STEP = register("entity.roombug.goofy_aah_step");

    public static final SoundEvent ENTITY_BIRT_DEATH = register("entity.birt.death");
    public static final SoundEvent ENTITY_BIRT_HURT = register("entity.birt.hurt");
    public static final SoundEvent ENTITY_BIRT_IDLE = register("entity.birt.idle");
    public static final SoundEvent ENTITY_BIRT_FLY = register("entity.birt.fly");
    public static final SoundEvent ENTITY_BIRT_MESSAGE = register("entity.birt.message");

    public static final SoundEvent BLOCK_BIRT_DWELLING_COLLECT = register("block.birt_dwelling.collect");
    public static final SoundEvent BLOCK_BIRT_DWELLING_ENTER = register("block.birt_dwelling.enter");
    public static final SoundEvent BLOCK_BIRT_DWELLING_EXIT = register("block.birt_dwelling.exit");
    public static final SoundEvent BLOCK_BIRT_DWELLING_WORK = register("block.birt_dwelling.work");

    public static final SoundEvent ENTITY_BIRTD = register("entity.birtd");

    public static final SoundEvent ITEM_BIRT_EGG_THROW = register("item.birt_egg.throw");
    public static final SoundEvent ITEM_BIRT_EGG_HIT = register("item.birt_egg.hit");

    public static final SoundEvent MUSIC_DISC_DIAL = register("music.disc.dial");

    public static final SoundEvent ENTITY_LIMPET_DEATH = register("entity.limpet.death");
    public static final SoundEvent ENTITY_LIMPET_HURT = register("entity.limpet.hurt");
    public static final SoundEvent ENTITY_LIMPET_IDLE = register("entity.limpet.idle");
    public static final SoundEvent ENTITY_LIMPET_STEP = register("entity.limpet.step");
    public static final SoundEvent ENTITY_LIMPET_DEFLECT = register("entity.limpet.deflect");

    private static SoundEvent register(String name) {
        ResourceLocation id = new ResourceLocation(MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }
}

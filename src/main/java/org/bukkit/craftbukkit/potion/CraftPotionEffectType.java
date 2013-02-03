package org.bukkit.craftbukkit.potion;


import org.bukkit.potion.PotionEffectType;

public class CraftPotionEffectType extends PotionEffectType {
    private final net.minecraft.potion.Potion/*was:MobEffectList*/ handle;

    public CraftPotionEffectType(net.minecraft.potion.Potion/*was:MobEffectList*/ handle) {
        super(handle.field_76415_H/*was:id*/);
        this.handle = handle;
    }

    @Override
    public double getDurationModifier() {
        return handle.func_76388_g/*was:getDurationModifier*/();
    }

    public net.minecraft.potion.Potion/*was:MobEffectList*/ getHandle() {
        return handle;
    }

    @Override
    public String getName() {
        switch (handle.field_76415_H/*was:id*/) {
        case 1:
            return "SPEED";
        case 2:
            return "SLOW";
        case 3:
            return "FAST_DIGGING";
        case 4:
            return "SLOW_DIGGING";
        case 5:
            return "INCREASE_DAMAGE";
        case 6:
            return "HEAL";
        case 7:
            return "HARM";
        case 8:
            return "JUMP";
        case 9:
            return "CONFUSION";
        case 10:
            return "REGENERATION";
        case 11:
            return "DAMAGE_RESISTANCE";
        case 12:
            return "FIRE_RESISTANCE";
        case 13:
            return "WATER_BREATHING";
        case 14:
            return "INVISIBILITY";
        case 15:
            return "BLINDNESS";
        case 16:
            return "NIGHT_VISION";
        case 17:
            return "HUNGER";
        case 18:
            return "WEAKNESS";
        case 19:
            return "POISON";
        case 20:
            return "WITHER";
        default:
            return "UNKNOWN_EFFECT_TYPE_" + handle.field_76415_H/*was:id*/;
        }
    }

    @Override
    public boolean isInstant() {
        return handle.func_76403_b/*was:isInstant*/();
    }
}
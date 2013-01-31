package org.bukkit.craftbukkit;

import org.bukkit.Art;

// Safety class, will break if either side changes
public class CraftArt {

    public static Art NotchToBukkit(net.minecraft.util.EnumArt/*was:EnumArt*/ art) {
        switch (art) {
            case Kebab/*was:KEBAB*/: return Art.KEBAB;
            case Aztec/*was:AZTEC*/: return Art.AZTEC;
            case Alban/*was:ALBAN*/: return Art.ALBAN;
            case Aztec2/*was:AZTEC2*/: return Art.AZTEC2;
            case Bomb/*was:BOMB*/: return Art.BOMB;
            case Plant/*was:PLANT*/: return Art.PLANT;
            case Wasteland/*was:WASTELAND*/: return Art.WASTELAND;
            case Pool/*was:POOL*/: return Art.POOL;
            case Courbet/*was:COURBET*/: return Art.COURBET;
            case Sea/*was:SEA*/: return Art.SEA;
            case Sunset/*was:SUNSET*/: return Art.SUNSET;
            case Creebet/*was:CREEBET*/: return Art.CREEBET;
            case Wanderer/*was:WANDERER*/: return Art.WANDERER;
            case Graham/*was:GRAHAM*/: return Art.GRAHAM;
            case Match/*was:MATCH*/: return Art.MATCH;
            case Bust/*was:BUST*/: return Art.BUST;
            case Stage/*was:STAGE*/: return Art.STAGE;
            case Void/*was:VOID*/: return Art.VOID;
            case SkullAndRoses/*was:SKULL_AND_ROSES*/: return Art.SKULL_AND_ROSES;
            case Fighters/*was:FIGHTERS*/: return Art.FIGHTERS;
            case Pointer/*was:POINTER*/: return Art.POINTER;
            case Pigscene/*was:PIGSCENE*/: return Art.PIGSCENE;
            case BurningSkull/*was:BURNINGSKULL*/: return Art.BURNINGSKULL;
            case Skeleton/*was:SKELETON*/: return Art.SKELETON;
            case DonkeyKong/*was:DONKEYKONG*/: return Art.DONKEYKONG;
            case Wither/*was:WITHER*/: return Art.WITHER;
            default:
                throw new AssertionError(art);
        }
    }

    public static net.minecraft.util.EnumArt/*was:EnumArt*/ BukkitToNotch(Art art) {
        switch (art) {
            case KEBAB: return net.minecraft.util.EnumArt/*was:EnumArt*/.Kebab/*was:KEBAB*/;
            case AZTEC: return net.minecraft.util.EnumArt/*was:EnumArt*/.Aztec/*was:AZTEC*/;
            case ALBAN: return net.minecraft.util.EnumArt/*was:EnumArt*/.Alban/*was:ALBAN*/;
            case AZTEC2: return net.minecraft.util.EnumArt/*was:EnumArt*/.Aztec2/*was:AZTEC2*/;
            case BOMB: return net.minecraft.util.EnumArt/*was:EnumArt*/.Bomb/*was:BOMB*/;
            case PLANT: return net.minecraft.util.EnumArt/*was:EnumArt*/.Plant/*was:PLANT*/;
            case WASTELAND: return net.minecraft.util.EnumArt/*was:EnumArt*/.Wasteland/*was:WASTELAND*/;
            case POOL: return net.minecraft.util.EnumArt/*was:EnumArt*/.Pool/*was:POOL*/;
            case COURBET: return net.minecraft.util.EnumArt/*was:EnumArt*/.Courbet/*was:COURBET*/;
            case SEA: return net.minecraft.util.EnumArt/*was:EnumArt*/.Sea/*was:SEA*/;
            case SUNSET: return net.minecraft.util.EnumArt/*was:EnumArt*/.Sunset/*was:SUNSET*/;
            case CREEBET: return net.minecraft.util.EnumArt/*was:EnumArt*/.Creebet/*was:CREEBET*/;
            case WANDERER: return net.minecraft.util.EnumArt/*was:EnumArt*/.Wanderer/*was:WANDERER*/;
            case GRAHAM: return net.minecraft.util.EnumArt/*was:EnumArt*/.Graham/*was:GRAHAM*/;
            case MATCH: return net.minecraft.util.EnumArt/*was:EnumArt*/.Match/*was:MATCH*/;
            case BUST: return net.minecraft.util.EnumArt/*was:EnumArt*/.Bust/*was:BUST*/;
            case STAGE: return net.minecraft.util.EnumArt/*was:EnumArt*/.Stage/*was:STAGE*/;
            case VOID: return net.minecraft.util.EnumArt/*was:EnumArt*/.Void/*was:VOID*/;
            case SKULL_AND_ROSES: return net.minecraft.util.EnumArt/*was:EnumArt*/.SkullAndRoses/*was:SKULL_AND_ROSES*/;
            case FIGHTERS: return net.minecraft.util.EnumArt/*was:EnumArt*/.Fighters/*was:FIGHTERS*/;
            case POINTER: return net.minecraft.util.EnumArt/*was:EnumArt*/.Pointer/*was:POINTER*/;
            case PIGSCENE: return net.minecraft.util.EnumArt/*was:EnumArt*/.Pigscene/*was:PIGSCENE*/;
            case BURNINGSKULL: return net.minecraft.util.EnumArt/*was:EnumArt*/.BurningSkull/*was:BURNINGSKULL*/;
            case SKELETON: return net.minecraft.util.EnumArt/*was:EnumArt*/.Skeleton/*was:SKELETON*/;
            case DONKEYKONG: return net.minecraft.util.EnumArt/*was:EnumArt*/.DonkeyKong/*was:DONKEYKONG*/;
            case WITHER: return net.minecraft.util.EnumArt/*was:EnumArt*/.Wither/*was:WITHER*/;
            default:
                throw new AssertionError(art);
        }
    }
}

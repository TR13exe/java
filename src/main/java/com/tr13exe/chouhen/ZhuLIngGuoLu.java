package com.tr13exe.chouhen;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.raid.Raider;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.function.Predicate;

public class ZhuLIngGuoLu
{
    private static final List<Class<? extends Monster>> BIAODAN = List.of(Zombie.class, Skeleton.class, Creeper.class, CaveSpider.class, Spider.class, Warden.class, Witch.class, Pillager.class, Vindicator.class, Evoker.class, Ravager.class, Blaze.class, Stray.class,
            Drowned.class, Guardian.class, Husk.class, Piglin.class, PiglinBrute.class, ZombifiedPiglin.class, WitherSkeleton.class, ZombieVillager.class);
    private static final List<Class<? extends Raider>> XIJIZHE = List.of(Witch.class, Pillager.class, Vindicator.class, Evoker.class, Ravager.class);
    private static final List<Class<? extends AbstractSkeleton>> KULOU = List.of(Stray.class, Skeleton.class, WitherSkeleton.class);
    private static final List<Class<? extends AbstractPiglin>> ZHULING = List.of(Piglin.class, PiglinBrute.class);
    private static final List<Class<? extends Zombie>> JIANGSHI = List.of(Husk.class, Zombie.class, Drowned.class,ZombieVillager.class);
    public static boolean targetShouldAttacked(Mob attacker, LivingEntity target)
    {
        boolean isTargetInList = BIAODAN.contains(target.getClass());
        boolean isTargetOfType = target.getClass() != attacker.getClass();
        boolean isTargetOfType2 = XIJIZHE.contains(attacker.getClass()) && XIJIZHE.contains(target.getClass());
        boolean isTargetOfType4 = KULOU.contains(attacker.getClass()) && KULOU.contains(target.getClass());
        boolean isTargetOfType5 = JIANGSHI.contains(attacker.getClass()) && JIANGSHI.contains(target.getClass());
        boolean isTargetOfType3 = ZHULING.contains(attacker.getClass()) && ZHULING.contains(target.getClass());
        return isTargetInList && isTargetOfType && !isTargetOfType2 && !isTargetOfType4 && !isTargetOfType5 && !isTargetOfType3;
    }
}

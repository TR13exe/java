package com.tr13exe.chouhen;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.List;
import java.util.function.Predicate;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ChouHenMod.MODID)
public class ChouHenMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "chouhen";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public ChouHenMod(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(ChouHenMod.class);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");






    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event)
    {
        Player player = event.player;
        ItemStack itemstack = player.getItemBySlot(EquipmentSlot.HEAD);
        if(itemstack.is(Items.DIAMOND_HELMET))
        {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,2,1,false,false,true));
        }
    }
    private static final List<Class<? extends Monster>> BIAODAN = List.of(Zombie.class, Skeleton.class, Creeper.class, CaveSpider.class, Spider.class, Warden.class, Witch.class, Pillager.class, Vindicator.class, Evoker.class, Ravager.class);
    private static final List<Class<? extends Raider>> XIJIZHE = List.of(Witch.class, Pillager.class, Vindicator.class, Evoker.class, Ravager.class);
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinLevelEvent event)
    {
        if(event.getLevel().isClientSide()) {return;}
        Entity didui = event.getEntity();
        if(BIAODAN.contains(didui.getClass()))
        {
            Mob gongji = (Mob) didui;
            Predicate<LivingEntity> guolu = (target) ->
            {
                boolean isTargetInList = BIAODAN.contains(target.getClass());
                boolean isTargetOfType = target.getClass()!=didui.getClass(); //判断攻击者和攻击目标是否是一个类，如果不是就能返回true
                boolean isTargetOfType2 = XIJIZHE.contains(didui.getClass()) && XIJIZHE.contains(target.getClass());
                return isTargetInList && isTargetOfType && !isTargetOfType2;
            };
            gongji.targetSelector.addGoal(
                    3,new NearestAttackableTargetGoal<>(
                            gongji, Monster.class,10, true, false, guolu
                    )
            );
        }
    }
    /*
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinLevelEvent event)
    {
        if(event.getLevel().isClientSide()) {return;}
        if(event.getEntity() instanceof Zombie)
        {
            Zombie zombie = (Zombie)event.getEntity();
            zombie.targetSelector.addGoal(
                    3,
                    new NearestAttackableTargetGoal<>(
                            zombie, Skeleton.class,true
                    )
            );
            zombie.targetSelector.addGoal(
                    3,
                    new NearestAttackableTargetGoal<>(
                            zombie, Creeper.class,true
                    )
            );
            zombie.targetSelector.addGoal(
                    3,
                    new NearestAttackableTargetGoal<>(
                            zombie, CaveSpider.class,true
                    )
            );
            zombie.targetSelector.addGoal(
                    3,
                    new NearestAttackableTargetGoal<>(
                            zombie, Spider.class,true
                    )
            );
            zombie.targetSelector.addGoal(
                    3,
                    new NearestAttackableTargetGoal<>(
                            zombie, Witch.class,true
                    )
            );
            zombie.targetSelector.addGoal(
                    3,
                    new NearestAttackableTargetGoal<>(
                            zombie, Pillager.class,true
                    )
            );
            zombie.targetSelector.addGoal(
                    3,
                    new NearestAttackableTargetGoal<>(
                            zombie, Vindicator.class,true
                    )
            );
            zombie.targetSelector.addGoal(
                    3,
                    new NearestAttackableTargetGoal<>(
                            zombie, Warden.class,true
                    )
            );
            zombie.targetSelector.addGoal(
                    3,
                    new NearestAttackableTargetGoal<>(
                            zombie, Slime.class,true
                    )
            );
            LOGGER.info("锁定目标");
        }

    }
    @SubscribeEvent
    public void onEntityJoinWorld2(EntityJoinLevelEvent event)
    {
        if(event.getLevel().isClientSide()) {return;}
        if(event.getEntity() instanceof Skeleton)
        {
            Skeleton skeleton = (Skeleton)event.getEntity();
            skeleton.targetSelector.addGoal(
                    3,
                    new NearestAttackableTargetGoal<>(
                            skeleton, Zombie.class,true
                    )
            );
            skeleton.targetSelector.addGoal(
                    3,
                    new NearestAttackableTargetGoal<>(
                            skeleton, Creeper.class,true
                    )
            );
            skeleton.targetSelector.addGoal(
                    3,
                    new NearestAttackableTargetGoal<>(
                            skeleton, CaveSpider.class,true
                    )
            );
            skeleton.targetSelector.addGoal(
                    3,
                    new NearestAttackableTargetGoal<>(
                            skeleton, Spider.class,true
                    )
            );
            skeleton.targetSelector.addGoal(
                    3,
                    new NearestAttackableTargetGoal<>(
                            skeleton, Witch.class,true
                    )
            );
            skeleton.targetSelector.addGoal(
                    3,
                    new NearestAttackableTargetGoal<>(
                            skeleton, Pillager.class,true
                    )
            );
            skeleton.targetSelector.addGoal(
                    3,
                    new NearestAttackableTargetGoal<>(
                            skeleton, Vindicator.class,true
                    )
            );
            skeleton.targetSelector.addGoal(
                    3,
                    new NearestAttackableTargetGoal<>(
                            skeleton, Warden.class,true
                    )
            );
            skeleton.targetSelector.addGoal(
                    3,
                    new NearestAttackableTargetGoal<>(
                            skeleton, Slime.class,true
                    )
            );
        }
    }*/
}

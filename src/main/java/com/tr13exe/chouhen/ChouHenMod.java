package com.tr13exe.chouhen;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.CaveSpiderRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

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

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        MinecraftForge.EVENT_BUS.register(ChouHenMod.class);
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
    public void onEntityJoinWorld(EntityJoinLevelEvent event)
    {
        if(event.getLevel().isClientSide()) {return;}
        if(event.getEntity() instanceof Zombie)
        {
            Zombie zombie=(Zombie)event.getEntity();
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
            LOGGER.info("锁定目标");
        }

    }
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event)
    {
        Player player = event.player;
        ItemStack itemstack = player.getItemBySlot(EquipmentSlot.HEAD);
        if(itemstack.is(Items.DIAMOND_HELMET))
        {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200,1,false,false,true));
            LOGGER.info("添加效果");
        }
    }
}

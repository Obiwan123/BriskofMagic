package com.igor.briskofmagic;

import com.igor.briskofmagic.block.entity.ModBlockEntities;
import com.igor.briskofmagic.block.entity.renderer.FuserBlockEntityRenderer;
import com.igor.briskofmagic.item.ModItemProperties;
import com.igor.briskofmagic.particle.ModParticles;
import com.igor.briskofmagic.particle.PheonParticles;
import com.igor.briskofmagic.screen.ModMenuTypes;
import com.igor.briskofmagic.screen.custom.*;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = BriskofMagic.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = BriskofMagic.MODID, value = Dist.CLIENT)
public class BriskofMagicClient {
    public BriskofMagicClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        BriskofMagic.LOGGER.info("HELLO FROM CLIENT SETUP");
        BriskofMagic.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        event.enqueueWork(ModItemProperties::register);
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.FUSER_BE.get(), FuserBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.EXTRACTOR_MENU.get(), ExtractorScreen::new);
        event.register(ModMenuTypes.FUSER_MENU.get(), FuserScreen::new);
        event.register(ModMenuTypes.DISENCHANT_MENU.get(), DisenchantScreen::new);
        event.register(ModMenuTypes.EMBEDDING_STATION_MENU.get(), EmbeddingStationScreen::new);
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event){
        event.registerSpriteSet(ModParticles.PHEON_PARTICLE.get(), PheonParticles.Provider::new);
    }

}

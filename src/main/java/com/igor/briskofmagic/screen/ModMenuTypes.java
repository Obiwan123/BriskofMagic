package com.igor.briskofmagic.screen;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.screen.custom.DisenchantMenu;
import com.igor.briskofmagic.screen.custom.EmbeddingStationMenu;
import com.igor.briskofmagic.screen.custom.ExtractorMenu;
import com.igor.briskofmagic.screen.custom.FuserMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, BriskofMagic.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<ExtractorMenu>> EXTRACTOR_MENU =
            registerMenuType("extractor_menu", ExtractorMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<FuserMenu>> FUSER_MENU =
            registerMenuType("fuser_menu", FuserMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<DisenchantMenu>> DISENCHANT_MENU =
            registerMenuType("disenchant_menu", DisenchantMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<EmbeddingStationMenu>> EMBEDDING_STATION_MENU =
            registerMenuType("embedding_station_menu", EmbeddingStationMenu::new);

    private static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {

        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}

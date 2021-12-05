package com.example.examplemod;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("examplemod")
public class ExampleMod {
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public ExampleMod() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private boolean isInY10;

    /*
     *
     */
    @SubscribeEvent
    public void onTickEvent(TickEvent.ClientTickEvent event) {
        // Minecraft インスタンスを取得
        var mc = Minecraft.getInstance();
        // カメラを取得
        var camera = mc.getCameraEntity();

        // カメラがない場合 (ワールドに入ってない場合など) は、処理を停止する
        if (camera == null) {
            // 呼ばれたメソッド (今回は onTickEvent などは、return; で処理を停止できる)
            return;
        }

        // プレイヤーの XYZ 座標を取得する
        var location = camera.blockPosition();

        // プレイヤーの y 座標を取得する
        var y = location.getY();

        // プレイヤーが Y <= 10 にいるか
        var value = y <= 10;

        // 前回の値と、今回の値が違うなら
        if (value != this.isInY10) {
            // プレイヤーが Y <= 10 にいれば
            if (value) {
                mc.gui.getChat().addMessage(new TextComponent("Y <= 10"));
            }

        }

        // 最後の値を更新
        this.isInY10 = value;
    }

}

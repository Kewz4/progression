package com.example.compat;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

@Mod("advanced_endgame_compat")
public class ArmorCompatMod {
    private static final TagKey<net.minecraft.world.item.Item> NETHERITE_DIAMOND = TagKey.create(Registries.ITEM, new ResourceLocation("advancednetherite", "tiers/armor/netherite_diamond"));

    public ArmorCompatMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingChangeTarget(LivingChangeTargetEvent event) {
        if (event.getEntity() instanceof Phantom && event.getNewTarget() instanceof Player player) {
            // Check for Full Set of the tag
            boolean fullSet = true;
            for (ItemStack stack : player.getArmorSlots()) {
                if (stack.isEmpty() || !stack.is(NETHERITE_DIAMOND)) {
                    fullSet = false;
                    break;
                }
            }

            if (fullSet) {
                event.setCanceled(true); // Prevent targeting
            }
        }
    }

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().is(NETHERITE_DIAMOND)) {
            event.getToolTip().add(Component.empty());
            event.getToolTip().add(Component.translatable("tooltip.advanced_endgame_compat.set_bonus").withStyle(ChatFormatting.AQUA));
            event.getToolTip().add(Component.literal(" - ").append(Component.translatable("tooltip.advanced_endgame_compat.passive.phantom")).withStyle(ChatFormatting.BLUE));
            event.getToolTip().add(Component.literal(" - ").append(Component.translatable("tooltip.advanced_endgame_compat.passive.piglin")).withStyle(ChatFormatting.GOLD));
            event.getToolTip().add(Component.literal(" - ").append(Component.translatable("tooltip.advanced_endgame_compat.passive.enderman")).withStyle(ChatFormatting.DARK_PURPLE));
        }
    }
}

package com.example.compat;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import java.util.UUID;

@Mod("advanced_endgame_compat")
public class ArmorCompatMod {
    private static final TagKey<net.minecraft.world.item.Item> NETHERITE_DIAMOND = TagKey.create(Registries.ITEM, new ResourceLocation("advancednetherite", "tiers/armor/netherite_diamond"));
    private static final TagKey<net.minecraft.world.item.Item> JELLYFISH_ARMOR = TagKey.create(Registries.ITEM, new ResourceLocation("advanced_endgame_compat", "jellyfish_armor"));
    private static final TagKey<net.minecraft.world.item.Item> ENDGAME_TOOLS = TagKey.create(Registries.ITEM, new ResourceLocation("advanced_endgame_compat", "endgame_tools"));

    // UUIDs for attributes
    private static final UUID SPEED_MODIFIER = UUID.fromString("6f0c4332-e30b-4d4b-a2cc-29a320305844");
    private static final UUID DAMAGE_MODIFIER = UUID.fromString("cb3f55d3-645c-4f38-a497-9c13a33db5cf");

    public ArmorCompatMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingChangeTarget(LivingChangeTargetEvent event) {
        if (event.getEntity() instanceof Phantom && event.getNewTarget() instanceof Player player) {
            if (isFullSet(player, NETHERITE_DIAMOND)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START && isFullSet(event.player, JELLYFISH_ARMOR)) {
            event.player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20, 0, false, false, true));
            event.player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20, 0, false, false, true));
        }
    }

    @SubscribeEvent
    public void onEffectApplicable(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player && isFullSet(player, JELLYFISH_ARMOR)) {
            if (event.getEffectInstance().getEffect() == MobEffects.BLINDNESS || event.getEffectInstance().getEffect() == MobEffects.DARKNESS) {
                event.setResult(net.minecraftforge.eventbus.api.Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public void onItemAttribute(ItemAttributeModifierEvent event) {
        // Jellyfish Leggings Speed
        if (event.getSlotType() == EquipmentSlot.LEGS && event.getItemStack().is(JELLYFISH_ARMOR)) {
            event.addModifier(Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED_MODIFIER, "Jellyfish Leggings Speed", 0.20, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }

        // Tool Damage Bonus
        if (event.getSlotType() == EquipmentSlot.MAINHAND) {
            ResourceLocation id = net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(event.getItemStack().getItem());
            if (id != null && id.toString().equals("bossesunleashed:jellyfish_umbrella")) {
                 // +2 over Warden Sword (+2 base), assuming Umbrella base ~ Sword base.
                 // Ideally +4 total bonus.
                 event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_MODIFIER, "Umbrella Tool Bonus", 4.0, AttributeModifier.Operation.ADDITION));
            } else if (event.getItemStack().is(ENDGAME_TOOLS)) {
                 event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_MODIFIER, "Endgame Tool Bonus", 2.0, AttributeModifier.Operation.ADDITION));
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

        if (event.getItemStack().is(JELLYFISH_ARMOR)) {
            event.getToolTip().add(Component.empty());
            event.getToolTip().add(Component.translatable("tooltip.advanced_endgame_compat.jellyfish_bonus").withStyle(ChatFormatting.LIGHT_PURPLE));
            event.getToolTip().add(Component.literal(" - ").append(Component.translatable("tooltip.advanced_endgame_compat.passive.fire_res")).withStyle(ChatFormatting.RED));

            if (event.getItemStack().getEquipmentSlot() == EquipmentSlot.LEGS) {
                event.getToolTip().add(Component.literal(" - ").append(Component.translatable("tooltip.advanced_endgame_compat.passive.speed")).withStyle(ChatFormatting.WHITE));
            }
            if (event.getItemStack().getEquipmentSlot() == EquipmentSlot.FEET) {
                event.getToolTip().add(Component.literal(" - ").append(Component.translatable("tooltip.advanced_endgame_compat.passive.dampening")).withStyle(ChatFormatting.GRAY));
            }

            event.getToolTip().add(Component.literal(" - ").append(Component.translatable("tooltip.advanced_endgame_compat.passive.full_set")).withStyle(ChatFormatting.GREEN));
        }
    }

    private boolean isFullSet(Player player, TagKey<net.minecraft.world.item.Item> tag) {
        for (ItemStack stack : player.getArmorSlots()) {
            if (stack.isEmpty() || !stack.is(tag)) {
                return false;
            }
        }
        return true;
    }
}

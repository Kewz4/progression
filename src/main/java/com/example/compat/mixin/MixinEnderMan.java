package com.example.compat.mixin;

import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderMan.class)
public class MixinEnderMan {
    private static final TagKey<net.minecraft.world.item.Item> NETHERITE_DIAMOND = TagKey.create(Registries.ITEM, new ResourceLocation("advancednetherite", "tiers/armor/netherite_diamond"));

    @Inject(method = "isLookingAtMe", at = @At("HEAD"), cancellable = true)
    private void isLookingAtMe(Player player, CallbackInfoReturnable<Boolean> cir) {
        // Vanilla Enderman checks for Pumpkin in HEAD slot.
        // We extend this to check for our tagged helmet.
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if (helmet.is(NETHERITE_DIAMOND)) {
            cir.setReturnValue(false);
        }
    }
}

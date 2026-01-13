package com.example.compat.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinAi.class)
public class MixinPiglinAi {
    private static final TagKey<net.minecraft.world.item.Item> NETHERITE_DIAMOND = TagKey.create(Registries.ITEM, new ResourceLocation("advancednetherite", "tiers/armor/netherite_diamond"));

    @Inject(method = "isWearingGold", at = @At("HEAD"), cancellable = true)
    private static void isWearingNetheriteDiamond(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        Iterable<ItemStack> armor = entity.getArmorSlots();
        boolean fullSet = true;
        int count = 0;

        for (ItemStack stack : armor) {
            count++;
            if (stack.isEmpty() || !stack.is(NETHERITE_DIAMOND)) {
                fullSet = false;
                break;
            }
        }

        // Ensure we actually checked slots (LivingEntity usually has 4 armor slots)
        if (fullSet && count > 0) {
            cir.setReturnValue(true);
        }
    }
}

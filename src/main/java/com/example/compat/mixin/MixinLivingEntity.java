package com.example.compat.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinLivingEntity {
    private static final TagKey<Item> JELLYFISH_ARMOR = TagKey.create(Registries.ITEM, new ResourceLocation("advanced_endgame_compat", "jellyfish_armor"));

    @Inject(method = "isSteppingCarefully", at = @At("HEAD"), cancellable = true)
    private void isSteppingCarefully(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof LivingEntity entity) {
            // Check Boots (Dampens vibrations)
            ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
            if (boots.is(JELLYFISH_ARMOR)) {
                 cir.setReturnValue(true);
                 return;
            }

            // Check Full Set (Silent footsteps)
            boolean fullSet = true;
            for (ItemStack stack : entity.getArmorSlots()) {
                if (stack.isEmpty() || !stack.is(JELLYFISH_ARMOR)) {
                    fullSet = false;
                    break;
                }
            }
            if (fullSet) {
                cir.setReturnValue(true);
            }
        }
    }
}

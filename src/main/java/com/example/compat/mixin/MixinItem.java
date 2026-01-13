package com.example.compat.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class MixinItem {
    private static final TagKey<Item> JELLYFISH_ARMOR = TagKey.create(Registries.ITEM, new ResourceLocation("advanced_endgame_compat", "jellyfish_armor"));

    @Inject(method = "isFireResistant", at = @At("HEAD"), cancellable = true)
    private void isFireResistant(CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = new ItemStack((Item) (Object) this);
        if (stack.is(JELLYFISH_ARMOR)) {
            cir.setReturnValue(true);
        }
    }
}

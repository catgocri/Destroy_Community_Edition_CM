package com.petrolpark.destroy.mixin;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.petrolpark.destroy.util.FireproofingHelper;
import com.petrolpark.destroy.util.UnfireproofingHelper;
import com.simibubi.create.content.fluids.transfer.GenericItemFilling;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;

@Mixin(GenericItemFilling.class)
public class GenericItemFillingMixin {

    @Shadow
    private static boolean canFillGlassBottleInternally(FluidStack availableFluid) {
        return false; // Dummy contents
    };
    
    @Inject(
        method = "Lcom/simibubi/create/content/fluids/transfer/GenericItemFilling;canItemBeFilled(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;)Z",
        at = @At("RETURN"),
        remap = false,
        cancellable = true
    )
    private static void inCanItemBeFilled(Level world, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (FireproofingHelper.canApply(world, stack)) cir.setReturnValue(true);
        if (UnfireproofingHelper.canApply(world, stack)) cir.setReturnValue(true);
    };

    @Inject(
        method = "Lcom/simibubi/create/content/fluids/transfer/GenericItemFilling;getRequiredAmountForItem(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraftforge/fluids/FluidStack;)I",
        at = @At("RETURN"),
        remap = false,
        cancellable = true
    )
    private static void inGetRequiredAmountForItem(Level world, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<Integer> cir) {
        if (cir.getReturnValueI() == -1) {
            if (!FireproofingHelper.isFireproof(stack)) {
                int required = FireproofingHelper.getRequiredAmountForItem(world, stack, availableFluid);
                if (required > 0) cir.setReturnValue(required);
            }
            else {
                int required = UnfireproofingHelper.getRequiredAmountForItem(world, stack, availableFluid);
                if (required > 0) cir.setReturnValue(required);
            }
        };
    };

    @Inject(
        method = "Lcom/simibubi/create/content/fluids/transfer/GenericItemFilling;fillItem(Lnet/minecraft/world/level/Level;ILnet/minecraft/world/item/ItemStack;Lnet/minecraftforge/fluids/FluidStack;)Lnet/minecraft/world/item/ItemStack;",
        at = @At("HEAD"),
        remap = false,
        cancellable = true
    )
    private static void inFillItem(Level world, int requiredAmount, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<ItemStack> cir) {
        FluidStack toFill = availableFluid.copy();
        toFill.setAmount(requiredAmount);
        ItemStack single = stack.copy();
        single.setCount(1);
        if (!(stack.getItem() == Items.GLASS_BOTTLE && canFillGlassBottleInternally(toFill)) && !single.getCapability(ForgeCapabilities.FLUID_HANDLER).isPresent() && !FireproofingHelper.isFireproof(stack)) {
            ItemStack result = FireproofingHelper.fillItem(world, requiredAmount, stack, availableFluid);
            if (!result.isEmpty()) cir.setReturnValue(result);
        }
        if (!(stack.getItem() == Items.GLASS_BOTTLE && canFillGlassBottleInternally(toFill)) && !single.getCapability(ForgeCapabilities.FLUID_HANDLER).isPresent() && FireproofingHelper.isFireproof(stack)) {
            ItemStack result = UnfireproofingHelper.fillItem(world, requiredAmount, stack, availableFluid);
            if (!result.isEmpty()) cir.setReturnValue(result);
        };
    };
};

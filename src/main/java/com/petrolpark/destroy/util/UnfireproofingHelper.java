package com.petrolpark.destroy.util;

import com.petrolpark.destroy.recipe.DestroyRecipeTypes;
import com.petrolpark.destroy.recipe.SingleFluidRecipe;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class UnfireproofingHelper {

    public static final String IS_APPLIED_TAG = "FlameRetardantApplied";

    private static final RecipeWrapper WRAPPER = new RecipeWrapper(new ItemStackHandler());

    public static boolean canApply(Level world, ItemStack stack) {
        return couldApply(world, stack) || DestroyRecipeTypes.FLAME_RETARDANT_REMOVAL.find(WRAPPER, world).isEmpty();
    };

    public static boolean couldApply(Level world, ItemStack stack) {
        if (isFireproof(stack)) return true;
        if (stack.getItem() instanceof BlockItem blockItem && !(blockItem.getBlock() instanceof ShulkerBoxBlock)) return false;
        return false;
    };

    public static int getRequiredAmountForItem(Level world, ItemStack stack, FluidStack availableFluid) {
        if (!canApply(world, stack)) return -1;
        return world.getRecipeManager().getRecipeFor(DestroyRecipeTypes.FLAME_RETARDANT_REMOVAL.getType(), WRAPPER, world).stream()
            .map(r -> (SingleFluidRecipe)r)
            .map(SingleFluidRecipe::getRequiredFluid)
            .filter(i -> i.test(availableFluid))
            .findFirst()
            .map(FluidIngredient::getRequiredAmount)
            .orElse(-1);
    };

    public static ItemStack fillItem(Level world, int requiredAmount, ItemStack stack, FluidStack availableFluid) {
        if (!canApply(world, stack)) return ItemStack.EMPTY;
        return world.getRecipeManager().getRecipeFor(DestroyRecipeTypes.FLAME_RETARDANT_REMOVAL.getType(), WRAPPER, world)
            .map(r -> (SingleFluidRecipe)r)
            .filter(r  -> r.getRequiredFluid().test(availableFluid))
            .map(r -> {
                availableFluid.shrink(10);
                ItemStack result = stack.copy();
                stack.shrink(1);
                apply(world, result);
                return result;
            })
            .orElse(ItemStack.EMPTY);
    };

    public static void apply(Level world, ItemStack stack) {
        if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof ShulkerBoxBlock) {
            stack.getOrCreateTagElement("BlockEntityTag").remove(IS_APPLIED_TAG);
        } else {
            stack.removeTagKey(IS_APPLIED_TAG);
        };
    };

    public static boolean isFireproof(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof ShulkerBoxBlock) {
            return stack.getOrCreateTagElement("BlockEntityTag").contains(IS_APPLIED_TAG, Tag.TAG_BYTE);
        } else {
            return stack.hasTag() && stack.getOrCreateTag().contains(IS_APPLIED_TAG, Tag.TAG_BYTE);
        }
    };
};

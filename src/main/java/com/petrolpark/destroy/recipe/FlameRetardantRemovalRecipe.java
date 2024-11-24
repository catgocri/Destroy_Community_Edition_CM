package com.petrolpark.destroy.recipe;

import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;

import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class FlameRetardantRemovalRecipe extends SingleFluidRecipe {

    public FlameRetardantRemovalRecipe(ProcessingRecipeParams params) {
        super(DestroyRecipeTypes.FLAME_RETARDANT_REMOVAL, params);
    };

    @Override
    public boolean matches(RecipeWrapper container, Level level) {
        return true;
    };

    @Override
    protected int getMaxInputCount() {
        return 0;
    };

    @Override
    protected int getMaxOutputCount() {
        return 0;
    };

    @Override
    protected int getMaxFluidOutputCount() {
        return 1;
    }

    @Override
    public String getRecipeTypeName() {
        return "flame retardant removal";
    };



};

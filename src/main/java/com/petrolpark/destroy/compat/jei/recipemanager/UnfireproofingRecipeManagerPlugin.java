package com.petrolpark.destroy.compat.jei.recipemanager;

import com.petrolpark.destroy.compat.jei.DestroyJEI;
import com.petrolpark.destroy.recipe.DestroyRecipeTypes;
import com.petrolpark.destroy.util.UnfireproofingHelper;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.advanced.IRecipeManagerPlugin;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;

import java.util.Collections;
import java.util.List;

public class UnfireproofingRecipeManagerPlugin implements IRecipeManagerPlugin {

    private final Minecraft mc = Minecraft.getInstance();

    @Override
    public <V> List<RecipeType<?>> getRecipeTypes(IFocus<V> focus) {
        if (focus.getRole() == RecipeIngredientRole.INPUT && focus.checkedCast(VanillaTypes.ITEM_STACK).filter(f -> UnfireproofingHelper.couldApply(mc.level, f.getTypedValue().getIngredient())).isPresent()) return Collections.singletonList(DestroyJEI.unfireproofing.getRecipeType());
        return Collections.emptyList();
    };

    @Override
    @SuppressWarnings("unchecked")
    public <T, V> List<T> getRecipes(IRecipeCategory<T> recipeCategory, IFocus<V> focus) {
        if (recipeCategory.getRecipeType() == DestroyJEI.unfireproofing.getRecipeType()) return (List<T>)mc.getConnection().getRecipeManager().getAllRecipesFor(DestroyRecipeTypes.FLAME_RETARDANT_REMOVAL.getType());
        return Collections.emptyList();
    };

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> getRecipes(IRecipeCategory<T> recipeCategory) {
        if (recipeCategory.getRecipeType() == DestroyJEI.unfireproofing.getRecipeType()) return (List<T>)mc.getConnection().getRecipeManager().getAllRecipesFor(DestroyRecipeTypes.FLAME_RETARDANT_REMOVAL.getType());
        return Collections.emptyList();
    };

};

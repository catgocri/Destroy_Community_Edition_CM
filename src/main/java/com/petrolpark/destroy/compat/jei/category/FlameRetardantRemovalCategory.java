package com.petrolpark.destroy.compat.jei.category;

import java.util.Collections;
import java.util.List;

import com.petrolpark.compat.jei.category.PetrolparkRecipeCategory;
import com.petrolpark.destroy.recipe.FlameRetardantRemovalRecipe;
import com.petrolpark.destroy.util.DestroyLang;
import com.petrolpark.destroy.util.FireproofingHelper;
import com.petrolpark.destroy.util.UnfireproofingHelper;
import com.simibubi.create.compat.jei.category.animations.AnimatedSpout;
import com.simibubi.create.foundation.gui.AllGuiTextures;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class FlameRetardantRemovalCategory extends PetrolparkRecipeCategory<FlameRetardantRemovalRecipe> {

    private final AnimatedSpout spout = new AnimatedSpout();
    private final List<ItemStack> exampleItems;
    private final Minecraft mc = Minecraft.getInstance();

    public FlameRetardantRemovalCategory(Info<FlameRetardantRemovalRecipe> info, IJeiHelpers helpers) {
        super(info, helpers);
        exampleItems = List.of(new ItemStack(Items.STICK), new ItemStack(Items.SHULKER_BOX), new ItemStack(Items.IRON_PICKAXE));
    };

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FlameRetardantRemovalRecipe recipe, IFocusGroup focuses) {
        boolean example = focuses.getFocuses(VanillaTypes.ITEM_STACK, RecipeIngredientRole.INPUT).findAny().isEmpty();
        List<ItemStack> items = example ? exampleItems : focuses.getItemStackFocuses(RecipeIngredientRole.INPUT).map(IFocus::getTypedValue).map(ITypedIngredient::getIngredient).toList();


        builder.addSlot(RecipeIngredientRole.INPUT, 27, 51)
                .setBackground(getRenderedSlot(), -1, -1)
                .addItemStacks(items.stream().map(i -> {
                    ItemStack result = i.copy();
                    FireproofingHelper.apply(mc.level, result);
                    return result;
                }).toList())
                .addRichTooltipCallback((v, t) -> { if (example) t.add(DestroyLang.translate("recipe.unfireproofing.info").style(ChatFormatting.GOLD).component()); });
        builder.addSlot(RecipeIngredientRole.INPUT, 27, 32)
                .setBackground(getRenderedSlot(), -1, -1)
                .addIngredients(ForgeTypes.FLUID_STACK, withImprovedVisibility(recipe.getRequiredFluid().getMatchingFluidStacks()))
                .addTooltipCallback(addFluidTooltip(recipe.getRequiredFluid().getRequiredAmount()));
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 132, 51)
                .setBackground(getRenderedSlot(), -1, -1)
                .addItemStacks(items.stream().map(i -> {
                    ItemStack result = i.copy();
                    UnfireproofingHelper.apply(mc.level, result);
                    return result;
                }).toList());
    };

    @Override
    public void draw(FlameRetardantRemovalRecipe recipe, IRecipeSlotsView iRecipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        AllGuiTextures.JEI_SHADOW.render(graphics, 62, 57);
        AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 126, 29);
        spout.withFluids(recipe.getRequiredFluid().getMatchingFluidStacks()).draw(graphics, getBackground().getWidth() / 2 - 13, 22);
    };

};

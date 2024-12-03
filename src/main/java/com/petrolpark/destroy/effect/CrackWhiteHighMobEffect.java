package com.petrolpark.destroy.effect;

import com.petrolpark.destroy.advancement.DestroyAdvancementTrigger;
import com.petrolpark.destroy.capability.player.crackwhite.PlayerCrackWhiteAddictionProvider;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class CrackWhiteHighMobEffect extends UncurableMobEffect {

    public CrackWhiteHighMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x8BDCEB);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "31875c8a-f500-477c-ac52-70355c6adc12", (double)0.5F, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, "0a7d851c-b38b-47c8-9131-348a492e3af8", (double)0.75F, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, "93bfb982-7e97-472f-b2f6-a0c51b4d916f", (double)2.5F, AttributeModifier.Operation.ADDITION);
    }

    @Override
    @SuppressWarnings("null") // We know the effect isn't null if its ticking
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!livingEntity.level().isClientSide()) {
            int duration = livingEntity.getEffect(DestroyMobEffects.CRACK_WHITE_HIGH.get()).getDuration(); // This is the bit it says is null
            if (duration == 1) {
                // Apply the Baby Blue Withdrawal Effect as the BabyBlue High Effect runs out.
                if (livingEntity instanceof Player player) {
                    player.getCapability(PlayerCrackWhiteAddictionProvider.PLAYER_CRACK_WHITE_ADDICTION).ifPresent(crackWhiteAddiction -> {
                        player.addEffect(new MobEffectInstance(DestroyMobEffects.CRACK_WHITE_WITHDRAWAL.get(), (10 + crackWhiteAddiction.getCrackWhiteAddiction()) * 20)); // Change the length of the effect depending on the Addiction level
                    });
                };
            } else {
                livingEntity.removeEffect(DestroyMobEffects.CRACK_WHITE_WITHDRAWAL.get());
            };

            if (livingEntity instanceof Animal animal && !animal.isBaby()) {
                if (animal.getAge() > 0) animal.resetLove();
                animal.setAge(0);
            };
        };

        super.applyEffectTick(livingEntity, amplifier);
    };

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // Apply effects every tick
    };
};

package com.petrolpark.destroy.item;

import com.petrolpark.destroy.config.DestroySubstancesConfigs;
import com.petrolpark.destroy.effect.DestroyMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CrackWhiteSyringeItem extends SyringeItem {

    private int duration;
    private int amplifier;

    public CrackWhiteSyringeItem(Properties properties, int crackWhiteEffectDuration, int crackWhiteEffectAmplifier) {
        super(properties);
        this.duration = crackWhiteEffectDuration;
        this.amplifier = crackWhiteEffectAmplifier;
    };

    @Override
    public void onInject(ItemStack itemStack, Level level, LivingEntity target) {
        target.addEffect(new MobEffectInstance(DestroyMobEffects.CRACK_WHITE_HIGH.get(), duration, amplifier));
    };
    
}

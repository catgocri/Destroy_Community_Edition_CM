package com.petrolpark.destroy.capability.player.crackwhite;

import com.petrolpark.destroy.config.DestroyAllConfigs;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;

public class PlayerCrackWhiteAddiction {
    private int crackWhiteAddiction;

    public void copyFrom(PlayerCrackWhiteAddiction source) {
        this.crackWhiteAddiction = source.crackWhiteAddiction;
    };

    public int getCrackWhiteAddiction() {
        return this.crackWhiteAddiction;
    };

    public void setCrackWhiteAddiction(int crackWhiteAddiction) {
        this.crackWhiteAddiction = Mth.clamp(crackWhiteAddiction, 0, getMaxCrackWhiteAddiction());
    };

    public static final int getMaxCrackWhiteAddiction() {
        return DestroyAllConfigs.SERVER.substances.babyBlueMaxAddictionLevel.get();
    };

    public void addCrackWhiteAddiction(int change) {
        this.crackWhiteAddiction = Mth.clamp(this.crackWhiteAddiction + change, 0, getMaxCrackWhiteAddiction());
    };

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("CrackWhiteAddiction", this.crackWhiteAddiction);
    };

    public void loadNBTData(CompoundTag nbt) {
        this.crackWhiteAddiction = nbt.getInt("CrackWhiteAddiction");
    };
}

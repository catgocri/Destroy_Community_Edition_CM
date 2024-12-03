package com.petrolpark.destroy.capability.player.crackwhite;

import com.petrolpark.destroy.capability.player.crackwhite.PlayerCrackWhiteAddiction;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerCrackWhiteAddictionProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerCrackWhiteAddiction> PLAYER_CRACK_WHITE_ADDICTION = CapabilityManager.get(new CapabilityToken<PlayerCrackWhiteAddiction>() { });

    private PlayerCrackWhiteAddiction crackWhiteAddiction = null;
    private final LazyOptional<PlayerCrackWhiteAddiction> optional = LazyOptional.of(this::createPlayerCrackWhiteAddiction);

    private PlayerCrackWhiteAddiction createPlayerCrackWhiteAddiction() {
        if (this.crackWhiteAddiction == null) {
            this.crackWhiteAddiction = new PlayerCrackWhiteAddiction();
        }
        return this.crackWhiteAddiction;
    };

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_CRACK_WHITE_ADDICTION) {
            return optional.cast();
        };

        return LazyOptional.empty();
    };
    
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerCrackWhiteAddiction().saveNBTData(nbt);
        return nbt;
    };

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerCrackWhiteAddiction().loadNBTData(nbt);
    };
}

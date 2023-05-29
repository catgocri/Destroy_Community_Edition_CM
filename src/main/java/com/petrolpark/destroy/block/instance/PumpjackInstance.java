package com.petrolpark.destroy.block.instance;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import com.jozufozu.flywheel.util.AnimationTickHolder;
import com.petrolpark.destroy.block.PumpjackBlock;
import com.petrolpark.destroy.block.entity.PumpjackBlockEntity;
import com.petrolpark.destroy.block.partial.DestroyPartials;
import com.simibubi.create.foundation.utility.AngleHelper;

import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;

public class PumpjackInstance extends BlockEntityInstance<PumpjackBlockEntity> implements DynamicInstance {

    protected final ModelData cam;
	protected final ModelData linkage;
	protected final ModelData beam;
    protected final ModelData pump;

    public PumpjackInstance(MaterialManager materialManager, PumpjackBlockEntity blockEntity) {
        super(materialManager, blockEntity);

        cam = materialManager.defaultSolid()
            .material(Materials.TRANSFORMED)
            .getModel(DestroyPartials.PUMPJACK_CAM, blockState)
            .createInstance();

        linkage = materialManager.defaultSolid()
            .material(Materials.TRANSFORMED)
            .getModel(DestroyPartials.PUMPJACK_LINKAGE, blockState)
            .createInstance();

        beam = materialManager.defaultSolid()
            .material(Materials.TRANSFORMED)
            .getModel(DestroyPartials.PUMPJACK_BEAM, blockState)
            .createInstance();

        pump = materialManager.defaultSolid()
            .material(Materials.TRANSFORMED)
            .getModel(DestroyPartials.PUMPJACK_PUMP, blockState)
            .createInstance();
    };

    @Override
    public void beginFrame() {
        Float angle = 2 * AnimationTickHolder.getRenderTime();
        float angleInRads = angle * Mth.PI / 180;
        //Float angle = blockEntity.getTargetAngle();
		// if (angle == null) {
		// 	cam.setEmptyTransform();
		// 	linkage.setEmptyTransform();
		// 	beam.setEmptyTransform();
        //     pump.setEmptyTransform();
		// 	return;
		// };

        Direction facing = PumpjackBlock.getFacing(blockState);

        transformed(cam, facing)
            .translate(0d, 0d, 1d)
            .centre()
            .rotateX(angle)
            .centre()
            .translate(0d, 0d, -1d)
            .unCentre()
            .unCentre();

        transformed(linkage, facing)
            .translate(0d, -5 / 16d, 1d)
            .translate(0d, Mth.sin(angleInRads) * 5 / 16d, -Mth.cos(angleInRads) * 5 / 16d)
            .centre()
            .rotateX(Mth.cos(angleInRads) * 10d)
            .centre()
            .translate(0d, 0d, -1d)
            .unCentre()
            .unCentre();

        transformed(beam, facing)
            .translate(0d, 1d, 0d)
            .centre()
            .rotateX((Mth.sin(angleInRads) - 1) * -20d)
            .centre()
            .translate(0d, -1d, 0d)
            .unCentre()
            .unCentre();

        transformed(pump, facing)
            .translate(0d, (3 / 16) - (Mth.sin(angleInRads) * 3 / 16d), 0d);
    };

    protected ModelData transformed(ModelData modelData, Direction facing) {
		return modelData.loadIdentity()
			.translate(getInstancePosition())
			.centre()
			.rotateY(AngleHelper.horizontalAngle(facing))
			.unCentre();
	};

    @Override
	public void updateLight() {
		relight(pos, cam, linkage, beam, pump);
	};

    @Override
    protected void remove() {
        cam.delete();
        linkage.delete();
        beam.delete();
        pump.delete();
    };
    
};

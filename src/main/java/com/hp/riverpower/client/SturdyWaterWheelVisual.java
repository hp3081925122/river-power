package com.hp.riverpower.client;

import com.hp.riverpower.content.SturdyLargeWaterWheelBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.model.Model;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.baked.BakedModelBuilder;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.util.RendererReloadCache;

import java.util.function.Consumer;

public final class SturdyWaterWheelVisual extends KineticBlockEntityVisual<SturdyLargeWaterWheelBlockEntity> {
    private static final RendererReloadCache<PartialModel, Model> MODELS =
            new RendererReloadCache<>(partial -> BakedModelBuilder.create(partial.get()).build());

    private final RotatingInstance wheel;

    public SturdyWaterWheelVisual(VisualizationContext context, SturdyLargeWaterWheelBlockEntity entity, float partialTick) {
        super(context, entity, partialTick);
        wheel = instancerProvider().instancer(AllInstanceTypes.ROTATING, MODELS.get(RiverPowerModels.sturdyWheel(blockState)))
                .createInstance();
        wheel.setup(entity).setPosition(getVisualPosition()).rotateToFace(rotationAxis()).setChanged();
    }

    @Override
    public void update(float partialTick) {
        wheel.setup(blockEntity).setChanged();
    }

    @Override
    public void updateLight(float partialTick) {
        relight(wheel);
    }

    @Override
    protected void _delete() {
        wheel.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        consumer.accept(wheel);
    }
}

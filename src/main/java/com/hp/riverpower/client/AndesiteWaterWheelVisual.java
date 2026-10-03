package com.hp.riverpower.client;

import com.hp.riverpower.content.AndesiteLargeWaterWheelBlockEntity;
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

public final class AndesiteWaterWheelVisual extends KineticBlockEntityVisual<AndesiteLargeWaterWheelBlockEntity> {
    private static final RendererReloadCache<PartialModel, Model> MODELS =
            new RendererReloadCache<>(partial -> new BakedModelBuilder(partial.get()).build());

    private final RotatingInstance wheel;

    public AndesiteWaterWheelVisual(VisualizationContext context, AndesiteLargeWaterWheelBlockEntity entity, float partialTick) {
        super(context, entity, partialTick);
        wheel = instancerProvider().instancer(AllInstanceTypes.ROTATING, MODELS.get(RiverPowerModels.wheel(blockState)))
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

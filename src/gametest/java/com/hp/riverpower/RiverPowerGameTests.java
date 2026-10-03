package com.hp.riverpower;

import com.hp.riverpower.content.AndesiteLargeWaterWheelBlockEntity;
import com.hp.riverpower.content.BrassLargeWaterWheelBlockEntity;
import com.hp.riverpower.content.SturdyLargeWaterWheelBlockEntity;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.waterwheel.LargeWaterWheelBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(RiverPower.MOD_ID)
@PrefixGameTestTemplate(false)
public final class RiverPowerGameTests {
    @GameTest(template = "empty")
    public static void brassWheelRegistration(GameTestHelper helper) {
        BlockPos center = new BlockPos(4, 3, 4);
        helper.setBlock(center, RiverPowerContent.BRASS_LARGE_WATER_WHEEL.getDefaultState()
                .setValue(LargeWaterWheelBlock.AXIS, Direction.Axis.Z));
        helper.runAfterDelay(4, () -> {
            helper.assertTrue(helper.getBlockEntity(center) instanceof BrassLargeWaterWheelBlockEntity,
                    "Brass wheel must create its own block entity");
            float vanilla = (float) BlockStressValues.getCapacity(AllBlocks.LARGE_WATER_WHEEL.get());
            var wheel = (BrassLargeWaterWheelBlockEntity) helper.getBlockEntity(center);
            helper.assertTrue(wheel.calculateAddedStressCapacity() == vanilla * 3,
                    "Brass wheel must provide three times the configured vanilla capacity");
            helper.assertTrue(helper.getBlockState(center.above()).is(AllBlocks.WATER_WHEEL_STRUCTURAL.get()),
                    "Brass wheel must reuse Create structural blocks");
            helper.succeed();
        });
    }

    @GameTest(template = "empty")
    public static void sturdyWheelRegistration(GameTestHelper helper) {
        BlockPos center = new BlockPos(4, 3, 4);
        helper.setBlock(center, RiverPowerContent.STURDY_LARGE_WATER_WHEEL.getDefaultState()
                .setValue(LargeWaterWheelBlock.AXIS, Direction.Axis.Z));
        helper.runAfterDelay(4, () -> {
            helper.assertTrue(helper.getBlockEntity(center) instanceof SturdyLargeWaterWheelBlockEntity,
                    "Sturdy wheel must create its own block entity");
            float vanilla = (float) BlockStressValues.getCapacity(AllBlocks.LARGE_WATER_WHEEL.get());
            var wheel = (SturdyLargeWaterWheelBlockEntity) helper.getBlockEntity(center);
            helper.assertTrue(wheel.calculateAddedStressCapacity() == vanilla * 4,
                    "Sturdy wheel must provide four times the configured vanilla capacity");
            BlockPos outer = center.above();
            helper.assertBlockPresent(AllBlocks.WATER_WHEEL_STRUCTURAL.get(), outer);
            var state = helper.getBlockState(outer);
            ItemStack picked = state.getBlock().getCloneItemStack(helper.getLevel(), helper.absolutePos(outer), state);
            helper.assertTrue(picked.is(RiverPowerContent.STURDY_LARGE_WATER_WHEEL.asItem()),
                    "Picking the sturdy wheel rim must return the sturdy wheel");
            helper.succeed();
        });
    }

    @GameTest(template = "empty")
    public static void structurePickingAndDrops(GameTestHelper helper) {
        BlockPos center = new BlockPos(4, 3, 4);
        BlockPos vanilla = new BlockPos(12, 3, 4);
        helper.setBlock(center, RiverPowerContent.ANDESITE_LARGE_WATER_WHEEL.getDefaultState()
                .setValue(LargeWaterWheelBlock.AXIS, Direction.Axis.Z));
        helper.setBlock(vanilla, AllBlocks.LARGE_WATER_WHEEL.getDefaultState()
                .setValue(LargeWaterWheelBlock.AXIS, Direction.Axis.Z));
        helper.runAfterDelay(4, () -> {
            helper.assertTrue(helper.getBlockEntity(center) instanceof AndesiteLargeWaterWheelBlockEntity,
                    "Registered wheel must create its own block entity");
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (x == 0 && y == 0) continue;
                    BlockPos outer = center.offset(x, y, 0);
                    helper.assertBlockPresent(AllBlocks.WATER_WHEEL_STRUCTURAL.get(), outer);
                    var state = helper.getBlockState(outer);
                    ItemStack picked = state.getBlock().getCloneItemStack(helper.getLevel(), helper.absolutePos(outer), state);
                    helper.assertTrue(picked.is(RiverPowerContent.ANDESITE_LARGE_WATER_WHEEL.asItem()),
                            "Picking the wheel rim or corner must return the andesite wheel");
                }
            }
            BlockPos vanillaOuter = vanilla.above();
            var vanillaState = helper.getBlockState(vanillaOuter);
            helper.assertTrue(vanillaState.getBlock().getCloneItemStack(helper.getLevel(),
                    helper.absolutePos(vanillaOuter), vanillaState).is(AllBlocks.LARGE_WATER_WHEEL.asItem()),
                    "Vanilla wheel picking must remain functional");
            var drops = Block.getDrops(helper.getBlockState(center), helper.getLevel(),
                    helper.absolutePos(center), helper.getBlockEntity(center));
            helper.assertTrue(drops.size() == 1 && drops.get(0).getCount() == 1
                    && drops.get(0).is(RiverPowerContent.ANDESITE_LARGE_WATER_WHEEL.asItem()),
                    "Wheel must drop exactly one andesite wheel");
            helper.destroyBlock(center.above());
            helper.runAfterDelay(4, () -> {
                helper.assertTrue(helper.getBlockState(center).isAir(), "Breaking the rim must remove the center");
                helper.assertTrue(helper.getBlockState(center.offset(1, -1, 0)).isAir(),
                        "Breaking the wheel must clean up the remaining structure");
                helper.succeed();
            });
        });
    }

    @GameTest(template = "empty", timeoutTicks = 120)
    public static void flowNetworkAndStopX(GameTestHelper helper) {
        testVerticalWheel(helper, Direction.Axis.X);
    }

    @GameTest(template = "empty", timeoutTicks = 120)
    public static void flowNetworkAndStopZ(GameTestHelper helper) {
        testVerticalWheel(helper, Direction.Axis.Z);
    }

    private static void testVerticalWheel(GameTestHelper helper, Direction.Axis axis) {
        BlockPos center = new BlockPos(4, 3, 4);
        BlockPos flow = axis == Direction.Axis.X ? center.south(2) : center.east(2);
        helper.setBlock(center, RiverPowerContent.ANDESITE_LARGE_WATER_WHEEL.getDefaultState()
                .setValue(LargeWaterWheelBlock.AXIS, axis));
        helper.setBlock(center.relative(Direction.get(Direction.AxisDirection.POSITIVE, axis)),
                AllBlocks.SHAFT.getDefaultState().setValue(LargeWaterWheelBlock.AXIS, axis));
        helper.setBlock(flow.below(), Blocks.SOUL_SAND);
        helper.setBlock(flow, Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(BubbleColumnBlock.DRAG_DOWN, false));
        helper.runAfterDelay(8, () -> {
            verifyPoweredWheel(helper, center, axis);
            var wheel = (AndesiteLargeWaterWheelBlockEntity) helper.getBlockEntity(center);
            float initialSpeed = wheel.getGeneratedSpeed();
            var restored = new AndesiteLargeWaterWheelBlockEntity(RiverPowerContent.ANDESITE_LARGE_WATER_WHEEL_ENTITY.get(),
                    helper.absolutePos(center), helper.getBlockState(center));
            restored.setLevel(helper.getLevel());
            restored.loadWithComponents(wheel.saveWithoutMetadata(helper.getLevel().registryAccess()), helper.getLevel().registryAccess());
            helper.assertTrue(restored.flowScore == wheel.flowScore && restored.getGeneratedSpeed() == initialSpeed,
                    "Water flow state must survive block entity serialization");
            helper.setBlock(flow.below(), Blocks.MAGMA_BLOCK);
            helper.setBlock(flow, Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(BubbleColumnBlock.DRAG_DOWN, true));
            helper.runAfterDelay(8, () -> {
                helper.assertTrue(wheel.getGeneratedSpeed() == -initialSpeed,
                        "Reversed water flow must reverse the wheel promptly");
                helper.setBlock(flow, Blocks.AIR);
                helper.runAfterDelay(8, () -> {
                    helper.assertTrue(wheel.getGeneratedSpeed() == 0 && wheel.getSpeed() == 0,
                            "Removing water flow must stop generation promptly");
                    helper.succeed();
                });
            });
        });
    }

    private static void verifyPoweredWheel(GameTestHelper helper, BlockPos center, Direction.Axis axis) {
        var wheel = (AndesiteLargeWaterWheelBlockEntity) helper.getBlockEntity(center);
        helper.assertTrue(Math.abs(wheel.getGeneratedSpeed()) == 4, "Wheel must generate exactly 4 RPM");
        float expected = (float) BlockStressValues.getCapacity(AllBlocks.LARGE_WATER_WHEEL.get()) * 2;
        helper.assertTrue(expected > 0 && wheel.calculateAddedStressCapacity() == expected,
                "Wheel must follow vanilla configured capacity with a 2x multiplier");
        var shaft = (KineticBlockEntity) helper.getBlockEntity(
                center.relative(Direction.get(Direction.AxisDirection.POSITIVE, axis)));
        helper.assertTrue(shaft.getSpeed() == wheel.getSpeed() && Math.abs(shaft.getSpeed()) == 4,
                "Attached shaft must receive the generated rotation");
        helper.assertTrue(wheel.getOrCreateNetwork().calculateCapacity() == expected * 4,
                "Kinetic network must receive the actual doubled stress capacity");
    }

    @GameTest(template = "empty", timeoutTicks = 140)
    public static void horizontalWheelFlowY(GameTestHelper helper) {
        BlockPos center = new BlockPos(4, 3, 4);
        helper.setBlock(center, RiverPowerContent.ANDESITE_LARGE_WATER_WHEEL.getDefaultState());
        helper.setBlock(center.above(), AllBlocks.SHAFT.getDefaultState());
        for (int x = 1; x <= 7; x++) {
            helper.setBlock(x, 2, 6, Blocks.STONE);
            helper.setBlock(x, 3, 7, Blocks.STONE);
            if (x < 3 || x > 5) helper.setBlock(x, 3, 5, Blocks.STONE);
        }
        helper.setBlock(1, 3, 6, Blocks.STONE);
        helper.setBlock(7, 3, 6, Blocks.STONE);
        helper.setBlock(2, 3, 6, Blocks.WATER);
        for (int x = 3; x <= 6; x++) {
            helper.setBlock(x, 3, 6, Blocks.WATER.defaultBlockState().setValue(LiquidBlock.LEVEL, x - 2));
        }
        helper.runAfterDelay(40, () -> {
            verifyPoweredWheel(helper, center, Direction.Axis.Y);
            helper.succeed();
        });
    }

    @GameTest(template = "empty")
    public static void itemPlacementAndExtension(GameTestHelper helper) {
        BlockPos center = new BlockPos(4, 3, 4);
        helper.setBlock(center, RiverPowerContent.ANDESITE_LARGE_WATER_WHEEL.getDefaultState());
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var item = RiverPowerContent.ANDESITE_LARGE_WATER_WHEEL.asStack();
        BlockPos absolute = helper.absolutePos(center);
        var hit = new BlockHitResult(Vec3.atCenterOf(absolute).add(0, 0.5, 0), Direction.UP, absolute, false);
        var context = new BlockPlaceContext(helper.getLevel(), player, InteractionHand.MAIN_HAND, item, hit);
        helper.assertTrue(item.getItem() instanceof com.simibubi.create.content.kinetics.waterwheel.LargeWaterWheelBlockItem,
                "Item must reuse Create large wheel placement");
        helper.assertTrue(((net.minecraft.world.item.BlockItem) item.getItem()).place(context).consumesAction(),
                "Wheel item must place successfully adjacent to an existing wheel");
        helper.runAfterDelay(4, () -> {
            helper.assertBlockProperty(center.above(), LargeWaterWheelBlock.EXTENSION, true);
            helper.destroyBlock(center);
            helper.runAfterDelay(4, () -> {
                helper.assertBlockProperty(center.above(), LargeWaterWheelBlock.EXTENSION, false);
                helper.succeed();
            });
        });
    }

    @GameTest(template = "empty")
    public static void wrenchRecoveryFromCorner(GameTestHelper helper) {
        BlockPos center = new BlockPos(4, 3, 4);
        BlockPos corner = center.offset(1, 1, 0);
        helper.setBlock(center, RiverPowerContent.ANDESITE_LARGE_WATER_WHEEL.getDefaultState()
                .setValue(LargeWaterWheelBlock.AXIS, Direction.Axis.Z));
        helper.runAfterDelay(4, () -> {
            var player = helper.makeMockPlayer(GameType.SURVIVAL);
            var absolute = helper.absolutePos(corner);
            var context = new UseOnContext(helper.getLevel(), player, InteractionHand.MAIN_HAND,
                    AllItems.WRENCH.asStack(), new BlockHitResult(Vec3.atCenterOf(absolute), Direction.SOUTH, absolute, false));
            var state = helper.getBlockState(corner);
            AllBlocks.WATER_WHEEL_STRUCTURAL.get().onSneakWrenched(state, context);
            helper.assertTrue(player.getInventory().countItem(RiverPowerContent.ANDESITE_LARGE_WATER_WHEEL.asItem()) == 1,
                    "Wrench recovery from a corner must return one andesite wheel");
            helper.runAfterDelay(4, () -> {
                helper.assertTrue(helper.getBlockState(center).isAir() && helper.getBlockState(corner).isAir(),
                        "Wrench recovery must remove the entire wheel");
                helper.succeed();
            });
        });
    }
}

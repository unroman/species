package com.ninni.species.block;

import com.ninni.species.block.entity.BirtDwellingBlockEntity;
import com.ninni.species.block.entity.SpeciesBlockEntities;
import com.ninni.species.block.property.SpeciesProperties;
import com.ninni.species.entity.BirtEntity;
import com.ninni.species.item.SpeciesItems;
import com.ninni.species.sound.SpeciesSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class BirtDwellingBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty BIRTS = SpeciesProperties.BIRTS;
    public static final IntegerProperty EGGS = SpeciesProperties.EGGS;

    public BirtDwellingBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BIRTS, 0).setValue(EGGS, 0));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        if (state.getValue(EGGS) > 0 && player.getItemInHand(hand).isEmpty()) {
            if (!world.isClientSide() && world.getBlockEntity(pos) instanceof BirtDwellingBlockEntity birtDwellingBlockEntity) {
                birtDwellingBlockEntity.angerBirts(player, state, BirtDwellingBlockEntity.BirtState.EMERGENCY);
            }
            world.setBlockAndUpdate(pos, state.setValue(EGGS, state.getValue(EGGS) - 1));
            BlockPos itemPos = pos.relative(state.getValue(FACING));
            world.playSound(null, itemPos, SpeciesSoundEvents.BLOCK_BIRT_DWELLING_COLLECT, SoundSource.NEUTRAL, 1 ,1);
            popResource(world, itemPos, new ItemStack(SpeciesItems.BIRT_EGG));
            return InteractionResult.SUCCESS;
        }
        return super.use(state, world, pos, player, hand, blockHitResult);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, ItemStack itemStack) {
        super.playerDestroy(level, player, blockPos, blockState, blockEntity, itemStack);
        if (!level.isClientSide && blockEntity instanceof BirtDwellingBlockEntity birtDwellingBlockEntity) {
            birtDwellingBlockEntity.angerBirts(player, blockState, BirtDwellingBlockEntity.BirtState.EMERGENCY);
            level.updateNeighbourForOutputSignal(blockPos, this);
            this.angerNearbyBirts(level, blockPos);
        }
    }

    private void angerNearbyBirts(Level world, BlockPos pos) {
        List<BirtEntity> birtList = world.getEntitiesOfClass(BirtEntity.class, new AABB(pos).inflate(8.0, 6.0, 8.0));
        if (!birtList.isEmpty()) {
            List<Player> playerList = world.getEntitiesOfClass(Player.class, new AABB(pos).inflate(8.0, 6.0, 8.0));
            for (BirtEntity birt : birtList) {
                if (birt.getTarget() != null) continue;
                birt.setTarget(playerList.get(world.random.nextInt(playerList.size())));
            }
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite()).setValue(EGGS, this.defaultBlockState().getValue(EGGS)).setValue(BIRTS, this.defaultBlockState().getValue(BIRTS));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, BIRTS, EGGS);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BirtDwellingBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : BirtDwellingBlock.createTickerHelper(blockEntityType, SpeciesBlockEntities.BIRT_DWELLING, BirtDwellingBlockEntity::serverTick);
    }

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState blockState, Player player) {
        BlockEntity blockEntity;
        if (!world.isClientSide() && player.isCreative() && world.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && (blockEntity = world.getBlockEntity(pos)) instanceof BirtDwellingBlockEntity) {
            BirtDwellingBlockEntity blockEntity1 = (BirtDwellingBlockEntity)blockEntity;
            ItemStack itemStack = new ItemStack(this);
            boolean bl = !blockEntity1.hasNoBirts();
            if (bl) {
                CompoundTag nbtCompound = new CompoundTag();
                nbtCompound.put("Birts", blockEntity1.getBirts());
                BlockItem.setBlockEntityData(itemStack, SpeciesBlockEntities.BIRT_DWELLING, nbtCompound);
                nbtCompound = new CompoundTag();
                itemStack.addTagElement("BlockStateTag", nbtCompound);
                ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                itemEntity.setDefaultPickUpDelay();
                world.addFreshEntity(itemEntity);
            }
        }
        super.playerWillDestroy(world, pos, blockState, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        BlockEntity blockEntity;
        Entity entity = builder.getOptionalParameter(LootContextParams.THIS_ENTITY);
        if ((entity instanceof PrimedTnt || entity instanceof Creeper || entity instanceof WitherSkull || entity instanceof WitherBoss || entity instanceof MinecartTNT) && (blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY)) instanceof BirtDwellingBlockEntity) {
            BirtDwellingBlockEntity blockEntity1 = (BirtDwellingBlockEntity)blockEntity;
            blockEntity1.angerBirts(null, state, com.ninni.species.block.entity.BirtDwellingBlockEntity.BirtState.EMERGENCY);
        }
        return super.getDrops(state, builder);
    }

}


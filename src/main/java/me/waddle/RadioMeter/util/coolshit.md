Based on the code you've provided, blocks in this game use a **fundamentally different storage system** than entities like the player. Here's how it works:

## Block Storage Architecture

### 1. **Voxel Grid Storage (Not Transform Components)**
Blocks are stored in a **chunk-based voxel grid** rather than using ECS Transform Components:

- **`BlockChunk`**: Stores the actual block data (block IDs, rotation indices, fillers) in a compact array format
- **`WorldChunk`**: Higher-level chunk management
- **Location**: Blocks are referenced by **integer coordinates** (x, y, z) within chunks, not Transform components

```java
// From BlockHarvestUtils.java - blocks accessed by coordinates:
BlockSection blockSection = blockChunkComponent.getSectionAtBlockY(blockPosition.getY());
int blockTypeIndex = blockSection.get(x, y, z);
int rotationIndex = blockSection.getRotationIndex(x, y, z);
```

### 2. **Block "Entities" for State**
When blocks need **additional data** (like chests with inventory), they use a hybrid approach:

- **`BlockComponentChunk`**: Stores references to block "entities" that have ECS components
- **`BlockModule.BlockStateInfo`**: Component that links a block entity back to its world position
- These aren't full entities with transforms - they're data containers indexed by block position

```java
// From FarmingUtil.java:
int blockIndexColumn = ChunkUtil.indexBlockInColumn(blockPosition.x, blockPosition.y, blockPosition.z);
Ref<ChunkStore> blockRef = blockComponentChunk.getEntityReference(blockIndexColumn);
```

### 3. **How Chests Work**
Chests and other complex blocks:

1. Basic block data stored in `BlockChunk` (block ID, rotation)
2. Additional state stored in a **`ChunkStore` entity** referenced by `BlockComponentChunk`
3. Position stored in `BlockModule.BlockStateInfo` component (not Transform)
4. Custom state (like `TreasureChestState`) extends this system

```java
// From TeleporterInteraction.java - accessing chest data:
int blockIndex = ChunkUtil.indexBlockInColumn(targetBlock.x, targetBlock.y, targetBlock.z);
Ref<ChunkStore> blockRef = blockComponentChunk.getEntityReference(blockIndex);
Teleporter teleporter = chunkStore.getStore().getComponent(blockRef, Teleporter.getComponentType());
```

## Key Differences from Entity System

| Aspect | Entities (Player) | Blocks (Chest) |
|--------|------------------|----------------|
| Position | `TransformComponent` (float precision) | Integer coordinates in chunk grid |
| Storage | `EntityStore` | `ChunkStore` + `BlockChunk` |
| Movement | Yes (velocity, collision) | No (fixed in grid) |
| State | ECS components | Hybrid: grid + optional entity reference |

The game optimizes memory by not giving every block a full Transform component - only blocks that need extra state get an associated entity in the `ChunkStore`.
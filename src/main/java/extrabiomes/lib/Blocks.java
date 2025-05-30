package extrabiomes.lib;

import net.minecraft.block.Block;

public enum Blocks {

    // @formatter:off
	BLOCK_LOG_SAKURA_GROVE;
    // @formatter:on

    private Block block = null;

    public Block get() {
        return block;
    }

    public boolean isPresent() {
        return block != null;
    }

    public void set(Block block) {
        this.block = block;
    }

}

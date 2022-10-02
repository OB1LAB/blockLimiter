package ob1lab.geno.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.chunk.Chunk;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static ob1lab.geno.Geno.*;

public class CheckChunkCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
        Commands.literal("bl")
        .then(Commands.literal("check")
        .requires((commandSource -> commandSource.hasPermissionLevel(4)))
        .executes((command) -> checkChunkLimit(
                command.getSource()
        ))));
    }
    private static int checkChunkLimit(CommandSource source) throws CommandSyntaxException {
        Chunk chunk = source.asPlayer().getServerWorld().getChunk(source.asPlayer().chunkCoordX, source.asPlayer().chunkCoordZ);
        Map<String, Integer> amount_blocks = new HashMap<>();
        for (String blockName : limitedList.keySet()) {
            amount_blocks.put(blockName, 0);
        }
        for (TileEntity tile : chunk.getTileEntityMap().values()) {
            String checkBlockName = Objects.requireNonNull(tile.getBlockState().getBlock().getRegistryName()).toString();
            if (amount_blocks.containsKey(checkBlockName)) {
                amount_blocks.replace(checkBlockName, amount_blocks.get(checkBlockName) + 1);
            }
        }
        for (String blockName : limitedList.keySet()) {
            if (amount_blocks.get(blockName) > (int) limitedList.get(blockName).get(1)) {
                String viewName = (String) limitedList.get(blockName).get(0);
                String limitBlock = String.valueOf(limitedList.get(blockName).get(1));
                String placed = String.valueOf(amount_blocks.get(blockName));
                Message.inChunkOnLimit.replace("{item}", viewName).replace("{placed}", placed).replace("{limit}", limitBlock).send(source);
            }
        }
        source.sendFeedback(new StringTextComponent("§2Чанк просканирован"), false);
        return 1;
    }
}

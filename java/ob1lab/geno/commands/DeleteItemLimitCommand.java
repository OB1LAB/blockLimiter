package ob1lab.geno.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import ob1lab.geno.Message;

import java.util.Objects;

import static ob1lab.geno.Geno.limitedList;

public class DeleteItemLimitCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
        Commands.literal("bl")
        .then(Commands.literal("del")
        .requires((commandSource -> commandSource.hasPermissionLevel(4)))
        .executes((command) -> deleteItemLimit(
                command.getSource())
        )));
    }
    private static int deleteItemLimit(CommandSource source) throws CommandSyntaxException {
        ServerPlayerEntity player = source.asPlayer();
        String blockName = Objects.requireNonNull(player.getHeldItemMainhand().getItem().getRegistryName()).toString();
        boolean resultBlock = Block.getBlockFromItem(player.getHeldItemMainhand().getItem()).getDefaultState().hasTileEntity();
        if (resultBlock) {
            if (limitedList.containsKey(blockName)) {
                Message.onDelete.replace("{item}", limitedList.get(blockName).get(0).toString()).send(source);
                limitedList.remove(blockName);
            } else {
                Message.itemNotInList.replace("{item}", blockName).send(source);
            }
            return 1;
        } else {
            Message.itemNotTileEntity.replace("{item}", blockName).send(source);
            return -1;
        }
    }
}

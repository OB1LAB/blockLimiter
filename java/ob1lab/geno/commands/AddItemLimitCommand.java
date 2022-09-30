package ob1lab.geno.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import ob1lab.geno.Message;

import java.util.List;
import java.util.Objects;

import static ob1lab.geno.Geno.limitedList;

public class AddItemLimitCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                Commands.literal("bl")
                .then(Commands.literal("add")
                .requires((commandSource -> commandSource.hasPermissionLevel(4)))
                .then(Commands.argument("limit", IntegerArgumentType.integer())
                .then(Commands.argument("view name", StringArgumentType.greedyString())
                .executes((command) -> addItemLimit(
                        command.getSource(),
                        IntegerArgumentType.getInteger(command, "limit"),
                        StringArgumentType.getString(command, "view name")))
        ))));
    }
    private static int addItemLimit(CommandSource source, int limit, String viewName) throws CommandSyntaxException {
        ServerPlayerEntity player = source.asPlayer();
        String blockName = Objects.requireNonNull(player.getHeldItemMainhand().getItem().getRegistryName()).toString();
        boolean resultBlock = Block.getBlockFromItem(player.getHeldItemMainhand().getItem()).getDefaultState().hasTileEntity();
        if (resultBlock) {
            List<Object> data = Lists.newArrayList();
            data.add(viewName);
            data.add(limit);
            if (limitedList.containsKey(blockName)) {
                limitedList.replace(blockName, data);
                Message.onUpdateItemToLimitList.replace("{item}", viewName).replace("{amount}", String.valueOf(limit)).send(source);
            } else {
                limitedList.put(blockName, data);
                Message.addItemToLimitList.replace("{item}", viewName).replace("{amount}", String.valueOf(limit)).send(source);
            }
            return 1;
        } else {
            Message.itemNotTileEntity.replace("{item}", blockName).send(source);
            return -1;
        }
    }
}

package ob1lab.geno.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import ob1lab.geno.Message;

import static ob1lab.geno.Geno.limitedList;

public class DeleteItemLimitPageCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
        Commands.literal("bl")
        .then(Commands.literal("del")
        .requires((commandSource -> commandSource.hasPermissionLevel(4)))
        .then(Commands.argument("blockName", StringArgumentType.greedyString())
        .executes((command) -> deleteItemLimit(
                command.getSource(),
                StringArgumentType.getString(command, "blockName"))
        ))));
    }
    private static int deleteItemLimit(CommandSource source, String blockName){
        if (limitedList.containsKey(blockName)) {
            Message.onDelete.replace("{item}", limitedList.get(blockName).get(0).toString()).send(source);
            limitedList.remove(blockName);
        } else {
            Message.itemNotInList.replace("{item}", blockName).send(source);
        }
        return 1;
    }
}

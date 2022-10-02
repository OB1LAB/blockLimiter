package ob1lab.geno.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class InfoCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                Commands.literal("bl")
                .then(Commands.literal("info")
                .requires((commandSource -> commandSource.hasPermissionLevel(4)))
                .executes((command) -> info(
                    command.getSource()
                ))));
    }
    private static int info(CommandSource source) {
        Message.documentation.send(source);
        return 1;
    }
}

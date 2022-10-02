package ob1lab.geno.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import ob1lab.geno.Geno;

public class UpdateConfigCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
        Commands.literal("bl")
        .then(Commands.literal("reload")
        .requires((commandSource -> commandSource.hasPermissionLevel(4)))
        .executes((command) -> updateConfig(
                command.getSource()
        ))));
    }
    private static int updateConfig(CommandSource source) {
        Geno.loadConfig();
        Message.updateConfigText.send(source);
        return 1;
    }
}

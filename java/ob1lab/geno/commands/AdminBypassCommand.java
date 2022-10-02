package ob1lab.geno.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import ob1lab.geno.Geno;
import ob1lab.geno.config.GenoConfig;

public class AdminBypassCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
        Commands.literal("bl")
        .then(Commands.literal("bypass")
        .requires((commandSource -> commandSource.hasPermissionLevel(4)))
        .executes((command) -> changeBypassState(
                command.getSource()
        ))));
    }
    private static int changeBypassState(CommandSource source) {
        if (Geno.adminBypass) {
            Geno.adminBypass = false;
            GenoConfig.adminBypass.set(false);
            Message.adminBypassText.replace("{state}", "&4выключен").send(source);
        } else {
            Geno.adminBypass = true;
            GenoConfig.adminBypass.set(true);
            Message.adminBypassText.replace("{state}", "&2включен").send(source);
        }
        return 1;
    }
}

package ob1lab.geno.init;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ob1lab.geno.commands.*;

public class ModCommands {
    @SubscribeEvent
    public static void onRegisterCommandEvent(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> commandDispatcher = event.getDispatcher();
        AddItemLimitCommand.register(commandDispatcher);
        DeleteItemLimitCommand.register(commandDispatcher);
        DeleteItemLimitPageCommand.register(commandDispatcher);
        ListCommand.register(commandDispatcher);
        ListPageCommand.register(commandDispatcher);
        InfoCommand.register(commandDispatcher);
        AdminBypassCommand.register(commandDispatcher);
    }
}

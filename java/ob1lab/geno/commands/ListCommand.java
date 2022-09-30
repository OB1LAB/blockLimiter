package ob1lab.geno.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import ob1lab.geno.Message;
import ob1lab.geno.config.GenoConfig;

import static ob1lab.geno.Geno.limitedList;

public class ListCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
        Commands.literal("bl")
            .then(Commands.literal("list")
            .requires((commandSource -> commandSource.hasPermissionLevel(4)))
            .executes((command) -> listItemLimit(
                    command.getSource()
            ))));
    }
    private static int listItemLimit(CommandSource source) {
        int itemOnPage = GenoConfig.viewItemsInList.get();
        int maxPage = (limitedList.size()/itemOnPage)+1;
        Message.pageUp.replace("{page}", "1").replace("{max_page}", String.valueOf(maxPage)).send(source);
        for (int i = 0; i < limitedList.size(); i++) {
            if (i < itemOnPage) {
                Object key = limitedList.keySet().toArray()[i];
                Message.delete
                        .replace("{item}", (String) key)
                        .replace("{view_name}", limitedList.get(key).get(0).toString())
                        .replace("{limit}", limitedList.get(key).get(1).toString())
                        .send(source);
            }
        }
        if (maxPage == 1) {
            Message.pageDownZero.send(source);
        } else {
            Message.pageDownFirst.replace("{next_page}", "2").send(source);
        }
        return 1;
    }
}

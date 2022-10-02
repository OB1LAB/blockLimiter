package ob1lab.geno.commands;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public enum Message {
    onLimit, onDelete, itemNotInList, itemNotTileEntity, addItemToLimitList, onUpdateItemToLimitList, incorrectPage, pageUp, pageDown, pageDownZero, pageDownFirst, pageDownLast, delete, documentation, adminBypassText, updateConfigText, inChunkOnLimit;
    private List<String> msg;
    public static void load(Map<String, String> messageList) {
        for (Message message: Message.values()) {
            String obj = messageList.get(message.name());
            message.msg = Lists.newArrayList(obj);
        }
    }
    public Sender replace (String from, String to) {
        Sender sender = new Sender();
        return sender.replace(from, to);
    }
    public void send(CommandSource source) {
        new Sender().send(source);
    }
    public class Sender {
        private final Map<String, String> placeholders = new HashMap<>();
        public void send(CommandSource source) {
            for (String message: Message.this.msg) {
                sendMessage(source, replacePlaceholders(message).replace("&", "§"));
            }
        }
        private void sendMessage(CommandSource source, String message) {
            if(message.startsWith("json:")) {
                source.sendFeedback(Objects.requireNonNull(ITextComponent.Serializer.getComponentFromJson(message.substring(5))), false);
            } else {
                source.sendFeedback(new StringTextComponent(message), false);
            }
        }
        public Sender replace(String from, String to) {
            placeholders.put(from, to);
            return this;
        }
        private String replacePlaceholders(String message) {
            if (!message.contains("{")) return message;
            for (Map.Entry<String, String> entry: placeholders.entrySet()) {
                message = message.replace(entry.getKey(), entry.getValue());
            }
            return message;
        }
    }
}

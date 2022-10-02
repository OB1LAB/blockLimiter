package ob1lab.geno.events;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ob1lab.geno.Geno;
import ob1lab.geno.commands.Message;
import ob1lab.geno.Requests;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static ob1lab.geno.Geno.*;

@Mod.EventBusSubscriber(modid=Geno.MOD_ID, bus=Mod.EventBusSubscriber.Bus.FORGE, value=Dist.DEDICATED_SERVER)
public class ModEvents {
    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        Entity player = event.getEntity();
        String blockName = Objects.requireNonNull(event.getPlacedBlock().getBlock().getRegistryName()).toString();
        if (limitedList.containsKey(blockName) && (!adminBypass || !Objects.requireNonNull(player).hasPermissionLevel(4))) {
            int count = 0;
            int max_count = (int) limitedList.get(blockName).get(1);
            Chunk chunk = (Chunk) event.getWorld().getChunk(event.getPos());
            for (TileEntity tile : chunk.getTileEntityMap().values()) {
                if (Objects.requireNonNull(tile.getBlockState().getBlock().getRegistryName()).toString().equals(blockName)) {
                    count = count + 1;
                    if (count > max_count) {
                        if (player != null) {
                            Message.onLimit.replace("{item}", (String) limitedList.get(blockName).get(0)).replace("{limit}", String.valueOf(max_count)).send(player.getCommandSource());
                        }
                        event.setCanceled(true);
                        return;
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void onChunkLoaded(ChunkEvent.Load event) {
        if (!isStarted || !useWebhook) {
            return;
        }
        Chunk chunk = (Chunk) event.getChunk();
        Map<String, Integer> amount_blocks = new HashMap<>();
        for (String blockName: limitedList.keySet()) {
            amount_blocks.put(blockName, 0);
        }
        for (TileEntity tile: chunk.getTileEntityMap().values()) {
            String checkBlockName = Objects.requireNonNull(tile.getBlockState().getBlock().getRegistryName()).toString();
            if (amount_blocks.containsKey(checkBlockName)) {
                amount_blocks.replace(checkBlockName, amount_blocks.get(checkBlockName)+1);
            }
        }
        for (String blockName: limitedList.keySet()) {
            if (amount_blocks.get(blockName) > (int) limitedList.get(blockName).get(1)) {
                Map<String, String> webhook = new HashMap<>();
                webhook.put("content", webhookMsg.replace("{item}", blockName).replace("{x}", String.valueOf(chunk.getPos().x*16)).replace("{z}", String.valueOf(chunk.getPos().z*16)).replace("{world}", ((Chunk) event.getChunk()).getWorld().getDimensionKey().getLocation().toString()).replace("{limit}", String.valueOf(limitedList.get(blockName).get(1))).replace("{set}", String.valueOf(amount_blocks.get(blockName))));
                Gson GSON = new GsonBuilder().create();
                Thread request = new Thread(() -> Requests.post(webhookToken, GSON.toJson(webhook)));
                request.start();
            }
        }
    }
}

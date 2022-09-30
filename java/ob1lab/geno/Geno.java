package ob1lab.geno;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.loading.FMLPaths;
import ob1lab.geno.config.GenoConfig;
import ob1lab.geno.init.ModCommands;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod(Geno.MOD_ID)
public class Geno {
    public static final String MOD_ID = "geno";
    public static Boolean isStarted = false;
    private static final Gson GSON = new GsonBuilder().create();
    private static final File file = new File(FMLPaths.GAMEDIR.get().resolve(Paths.get("limits.json")).toUri());
    public static List<LimitBlock> limitJson;
    public static Map<String, List<Object>> limitedList = new HashMap<>();
    public static Boolean adminBypass;
    public static Boolean useWebhook;
    public static String webhookToken;
    public static String webhookMsg;
    public Geno() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, GenoConfig.SPEC, "geno-config.toml");
        MinecraftForge.EVENT_BUS.register(ModCommands.class);
        MinecraftForge.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) throws IOException {
        limitJson = readLimits();
        for (LimitBlock block: limitJson) {
            List<Object> data = Lists.newArrayList();
            data.add(block.viewName);
            data.add(block.limit);
            limitedList.put(block.id, data);
        }
        adminBypass = GenoConfig.adminBypass.get();
        useWebhook = GenoConfig.useWebhook.get();
        webhookToken = GenoConfig.webhookToken.get();
        webhookMsg = GenoConfig.webhookMsg.get();
        Map<String, String> messageList = new HashMap<>();
        messageList.put("onLimit", GenoConfig.onLimit.get());
        messageList.put("onDelete", GenoConfig.onDelete.get());
        messageList.put("itemNotInList", GenoConfig.itemNotInList.get());
        messageList.put("itemNotTileEntity", GenoConfig.itemNotTileEntity.get());
        messageList.put("addItemToLimitList", GenoConfig.addItemToLimitList.get());
        messageList.put("onUpdateItemToLimitList", GenoConfig.onUpdateItemToLimitList.get());
        messageList.put("incorrectPage", GenoConfig.incorrectPage.get());
        messageList.put("pageUp", GenoConfig.pageUp.get());
        messageList.put("pageDown", GenoConfig.pageDown.get());
        messageList.put("pageDownZero", GenoConfig.pageDownZero.get());
        messageList.put("pageDownFirst", GenoConfig.pageDownFirst.get());
        messageList.put("pageDownLast", GenoConfig.pageDownLast.get());
        messageList.put("delete", GenoConfig.delete.get());
        messageList.put("documentation", GenoConfig.documentation.get());
        messageList.put("adminBypassText", GenoConfig.adminBypassText.get());
        Message.load(messageList);
        isStarted = true;
    }
    @SubscribeEvent
    public void onServerStopping(FMLServerStoppingEvent event) {
        List<LimitBlock> data = Lists.newArrayList();
        for (String key: limitedList.keySet()) {
            String viewName = (String) limitedList.get(key).get(0);
            int limit = (int) limitedList.get(key).get(1);
            data.add(new LimitBlock(key, viewName, limit));
        }
        limitJson = data;
        writeLimits();
    }
    @SuppressWarnings("UnstableApiUsage")
    private List<LimitBlock> readLimits() throws IOException {
        try {
            String json = Files.asCharSource(file, StandardCharsets.UTF_8).read();
            return Arrays.asList(GSON.fromJson(json, LimitBlock[].class));
        } catch (IOException e) {
            Files.write(GSON.toJson(Lists.newArrayList()), file, StandardCharsets.UTF_8);
            return Lists.newArrayList();
        }
    }
    @SuppressWarnings("UnstableApiUsage")
    private void writeLimits() {
        try {
            Files.write(GSON.toJson(limitJson.toArray(new LimitBlock[0])), file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

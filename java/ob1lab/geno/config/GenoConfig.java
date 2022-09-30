package ob1lab.geno.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class GenoConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Boolean> adminBypass;
    public static final ForgeConfigSpec.ConfigValue<Boolean> useWebhook;
    public static final ForgeConfigSpec.ConfigValue<String> webhookToken;
    public static final ForgeConfigSpec.ConfigValue<String> webhookMsg;
    public static final ForgeConfigSpec.ConfigValue<String> onLimit;
    public static final ForgeConfigSpec.ConfigValue<String> onDelete;
    public static final ForgeConfigSpec.ConfigValue<String> itemNotInList;
    public static final ForgeConfigSpec.ConfigValue<String> itemNotTileEntity;
    public static final ForgeConfigSpec.ConfigValue<String> addItemToLimitList;
    public static final ForgeConfigSpec.ConfigValue<String> onUpdateItemToLimitList;
    public static final ForgeConfigSpec.ConfigValue<String> incorrectPage;
    public static final ForgeConfigSpec.ConfigValue<String> pageUp;
    public static final ForgeConfigSpec.ConfigValue<String> pageDown;
    public static final ForgeConfigSpec.ConfigValue<String> pageDownZero;
    public static final ForgeConfigSpec.ConfigValue<String> pageDownFirst;
    public static final ForgeConfigSpec.ConfigValue<String> pageDownLast;
    public static final ForgeConfigSpec.ConfigValue<String> delete;
    public static final ForgeConfigSpec.ConfigValue<Integer> viewItemsInList;
    public static final ForgeConfigSpec.ConfigValue<String> documentation;
    public static final ForgeConfigSpec.ConfigValue<String> adminBypassText;
    static {
        BUILDER.push("Settings");
        adminBypass = BUILDER.comment("Могут ли опнутые обходить ограничение").define("adminBypass", false);
        useWebhook = BUILDER.comment("Использовать дискорд вебхука").define("useWebhook", false);
        webhookToken = BUILDER.comment("Дискорд токен вебхука").define("webhookToken", "token");
        webhookMsg = BUILDER.comment("Сообщение, отправляемое в дискорд при обходе ограничений").define("webhookMsg", "```js\nПревышение {item} на координатах x: {x} z: {z} в мире {world}\nЛимит: {limit} Установлено: {set}```");
        BUILDER.pop();
        BUILDER.push("Commands");
        onLimit = BUILDER.comment("Превышение лимита").define("onLimit", "&4Ограничение {item} на чанк - {limit}");
        onDelete = BUILDER.comment("Удаление предмета из ограничителя").define("onDelete", "&2{item} успешно удалён из списка ограничений");
        itemNotInList = BUILDER.comment("Предмета нет в списке ограничений").define("itemNotInList", "&4{item} нет в списке ограничений");
        itemNotTileEntity = BUILDER.comment("Предмет не является Tile Entity").define("itemNotTileEntity", "&4{item} не является не является Tile Entity");
        addItemToLimitList = BUILDER.comment("Добавление предмета в ограничитель").define("addItemToLimitList", "&2{item} успешно добавлен в список с ограничением {amount} blocks на чанк");
        onUpdateItemToLimitList = BUILDER.comment("Обновление предмета в ограничители").define("onUpdateItemToLimitList", "&2{item} успешно обновлён в списке с ограничением {amount} blocks на чанк");
        incorrectPage = BUILDER.comment("Некорректная выбранная страница").define("incorrectPage", "&4Некорректная выбранная страница");
        pageUp = BUILDER.comment("Верхняя страница списка").define("pageUp", "&6=============[&c{page}&6/&c{max_page}&6]==============");
        pageDown = BUILDER.comment("Нижняя страница списка").define("pageDown", "json: [\"\",{\"text\":\"============\",\"color\":\"gold\"},{\"text\":\"<<<\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/bl list {previous_page}\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Предыдущая страница\"}]}}},{\"text\":\"--\",\"color\":\"gold\"},{\"text\":\">>>\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/bl list {next_page}\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Следующая страница\"}]}}},{\"text\":\"============\",\"color\":\"gold\"}]");
        pageDownZero = BUILDER.comment("Нижняя страница, когда только одна страница").define("pageDownZero", "&6===============================");
        pageDownFirst = BUILDER.comment("Нижняя первая страница").define("pageDownFirst", "json: [\"\",{\"text\":\"================\",\"color\":\"gold\"},{\"text\":\">>>\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/bl list {next_page}\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Следующая страница\"}]}}},{\"text\":\"============\",\"color\":\"gold\"}]");
        pageDownLast = BUILDER.comment("Нижняя страница, без стрелки вправо").define("pageDownLast", "json: [\"\",{\"text\":\"============\",\"color\":\"gold\"},{\"text\":\"<<<\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/bl list {previous_page}\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Предыдущая страница\"}]}}},{\"text\":\"================\",\"color\":\"gold\"}]");
        delete = BUILDER.comment("Кнопка удаления предмета").define("delete", "json: [\"\",{\"text\":\"[Удалить]\",\"color\":\"red\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/bl del {item}\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Удалить предмет из списка ограничений\",\"color\":\"yellow\"}]}}},{\"text\":\" &9{view_name} - &a{limit}\",\"color\":\"dark_aqua\"}]");
        viewItemsInList = BUILDER.comment("Количество отображаемых предметов").define("viewItemsInList", 10);
        documentation = BUILDER.comment("Документация").define("documentation", "json: [\"\",{\"text\":\"===================================================\",\"color\":\"gold\"},{\"text\":\"\\n\"},{\"text\":\"/bl add - \\u0414\\u043e\\u0431\\u0430\\u0432\\u043b\\u0435\\u043d\\u0438\\u0435 \\u0432 \\u043e\\u0433\\u0440\\u0430\\u043d\\u0438\\u0447\\u0438\\u0442\\u0435\\u043b\\u044c. \\u041f\\u0440\\u0435\\u0434\\u043c\\u0435\\u0442 \\u0434\\u043e\\u043b\\u0436\\u0435\\u043d \\u043d\\u0430\\u0445\\u043e\\u0434\\u0438\\u0442\\u044c\\u0441\\u044f \\u0432 \\u043e\\u0441\\u043d\\u043e\\u0432\\u043d\\u043e\\u0439 \\u0440\\u0443\\u043a\\u0435.\\n/bl rm - \\u0423\\u0434\\u0430\\u043b\\u0435\\u043d\\u0438\\u0435 \\u0438\\u0437 \\u043e\\u0433\\u0440\\u0430\\u043d\\u0438\\u0447\\u0438\\u0442\\u0435\\u043b\\u044f. \\u041f\\u0440\\u0435\\u0434\\u043c\\u0435\\u0442 \\u0434\\u043e\\u043b\\u0436\\u0435\\u043d \\u043d\\u0430\\u0445\\u043e\\u0434\\u0438\\u0442\\u044c\\u0441\\u044f \\u0432 \\u043e\\u0441\\u043d\\u043e\\u0432\\u043d\\u043e\\u0439 \\u0440\\u0443\\u043a\\u0435.\",\"color\":\"dark_aqua\"},{\"text\":\"\\n\"},{\"text\":\"/bl list - \\u041e\\u0442\\u043e\\u0431\\u0440\\u0430\\u0437\\u0438\\u0442\\u044c \\u0441\\u043f\\u0438\\u0441\\u043e\\u043a \\u043e\\u0433\\u0440\\u0430\\u043d\\u0438\\u0447\\u0435\\u043d\\u0438\\u0439.\",\"color\":\"dark_aqua\"},{\"text\":\"\\n\"},{\"text\":\"===================================================\",\"color\":\"gold\"},{\"text\":\"\\n\"},{\"text\":\"/bl add {count} {view_name}\\n  {count} - \\u041a\\u043e\\u043b\\u0438\\u0447\\u0435\\u0441\\u0442\\u0432\\u043e \\u043d\\u0430 \\u0447\\u0430\\u043d\\u043a.\\n  {view_name} - \\u041e\\u0442\\u043e\\u0431\\u0440\\u0430\\u0436\\u0430\\u0435\\u043c\\u043e\\u0435 \\u0438\\u043c\\u044f \\u0432 \\u0441\\u043f\\u0438\\u0441\\u043a\\u0435 \\u043e\\u0433\\u0440\\u0430\\u043d\\u0438\\u0447\\u0435\\u043d\\u0438\\u0439.\\n/bl rm {block_id}\\n  {block_id} - \\u0411\\u043b\\u043e\\u043a.\\n/bl list {page}\\n  {page} - \\u0421\\u0442\\u0440\\u0430\\u043d\\u0438\\u0446\\u0430.\\n/bl bypass - \\u0412\\u043a\\u043b\\u044e\\u0447\\u0435\\u043d\\u0438\\u0435/\\u041e\\u0442\\u043a\\u043b\\u044e\\u0447\\u0435\\u043d\\u0438\\u0435 \\u043e\\u0433\\u0440\\u0430\\u043d\\u0438\\u0447\\u0435\\u043d\\u0438\\u0439. (\\u0414\\u043b\\u044f \\u043e\\u043f\\u043d\\u0443\\u0442\\u044b\\u0445 \\u0438\\u0433\\u0440\\u043e\\u043a\\u043e\\u0432)\",\"color\":\"dark_aqua\"},{\"text\":\"\\n\"},{\"text\":\"===================================================\",\"color\":\"gold\"}]");
        adminBypassText = BUILDER.comment("Количество отображаемых предметов").define("adminBypassText", "&6Обход админами ограничений на чанк: {state}");
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}

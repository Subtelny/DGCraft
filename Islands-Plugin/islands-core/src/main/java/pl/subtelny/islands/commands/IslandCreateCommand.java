package pl.subtelny.islands.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.gui.api.crate.model.Crate;
import pl.subtelny.gui.api.crate.repository.CrateRepository;
import pl.subtelny.gui.api.crate.session.PlayerCrateSessionService;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.islander.IslanderService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.islands.skyblockisland.creator.SkyblockIslandCreateRequest;
import pl.subtelny.islands.skyblockisland.creator.SkyblockIslandCreator;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.schematic.SkyblockIslandSchematicOption;
import pl.subtelny.islands.skyblockisland.settings.SkyblockIslandSettings;
import pl.subtelny.jobs.JobsProvider;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.log.LogUtil;

import java.util.Optional;

@PluginSubCommand(command = "create", aliases = "stworz", mainCommand = IslandCommand.class)
public class IslandCreateCommand extends BaseCommand {

    private final IslanderService islanderService;

    private final SkyblockIslandCreator islandCreator;

    private final SkyblockIslandSettings settings;

    private final CrateRepository crateRepository;

    private final PlayerCrateSessionService sessionService;

    @Autowired
    public IslandCreateCommand(IslandMessages messages,
                               IslanderService islanderService,
                               SkyblockIslandCreator islandCreator,
                               SkyblockIslandSettings settings,
                               CrateRepository crateRepository,
                               PlayerCrateSessionService sessionService) {
        super(messages);
        this.islanderService = islanderService;
        this.islandCreator = islandCreator;
        this.settings = settings;
        this.crateRepository = crateRepository;
        this.sessionService = sessionService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Islander islander = islanderService.getIslander(player);

        if (args.length == 0) {
            openGuiCreator(player);
            return;
        }
        SkyblockIslandSchematicOption schematicOption = getSchematicOption(args[0]);
        createIsland(schematicOption, player, islander);
    }

    private void createIsland(SkyblockIslandSchematicOption schematicOption, Player player, Islander islander) {
        SkyblockIslandCreateRequest request = SkyblockIslandCreateRequest.builder(islander)
                .option(schematicOption)
                .build();

        islandCreator.create(request)
                .whenComplete((skyblockIsland, throwable) -> {
                    if (throwable != null) {
                        islandFailureCreate(player, throwable);
                    } else {
                        islandSucessfullyCreated(player, skyblockIsland);
                    }
                });
    }

    private void islandFailureCreate(Player player, Throwable throwable) {
        LogUtil.warning("Error while creating island: " + throwable.getMessage());
        getMessages().sendTo(player, getMessages().getRawMessage("command.island.create.internal_error"));
    }

    private void islandSucessfullyCreated(Player player, SkyblockIsland skyblockIsland) {
        JobsProvider.runSync(Islands.plugin, () -> {
            player.teleport(skyblockIsland.getSpawn());
            getMessages().sendTo(player, getMessages().getRawMessage("command.island.create.island_created"));
        });
    }

    private void openGuiCreator(Player player) {
        String creatorGui = settings.getCreatorGui();
        Crate crate = crateRepository.findInternalCrate(creatorGui)
                .orElseThrow(() -> ValidationException.of("command.island.create.gui_not_found"));
        sessionService.openSession(player, crate);
    }

    private SkyblockIslandSchematicOption getSchematicOption(String schematicOptionRaw) {
        Optional<SkyblockIslandSchematicOption> schematicOption = settings.getSchematicOption(schematicOptionRaw);
        Validation.isTrue(schematicOption.isPresent(), "command.island.create.not_found_schematic", schematicOptionRaw);
        return schematicOption.get();
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}

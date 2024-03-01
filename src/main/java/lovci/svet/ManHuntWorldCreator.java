package lovci.svet;

import org.bukkit.Server;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.codehaus.plexus.util.FileUtils;

import java.io.IOException;

public class ManHuntWorldCreator {

    public static final String WORLD_NAME = "ManHuntWorld";

    private final Server server;

    public ManHuntWorldCreator(Server server) {
        this.server = server;
    }

    public String createWorld(int worldSize){
        WorldCreator worldCreator = WorldCreator.name(WORLD_NAME);
        var world = server.createWorld(worldCreator);
        WorldBorder wb = world.getWorldBorder();
        wb.setCenter(0,0);
        wb.setSize(worldSize);
        return  world.getName();
    }

    public void removeWorld() throws IOException{
        var worldFolder = server.getWorld(ManHuntWorldCreator.WORLD_NAME).getWorldFolder();
        server.unloadWorld(ManHuntWorldCreator.WORLD_NAME, false);
        FileUtils.deleteDirectory(worldFolder);
    }
}

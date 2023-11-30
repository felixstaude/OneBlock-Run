package de.felixstaude.oneblock.util.files;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigFileManager {

    File file = new File("plugins/OneBlock-Run/config.yml");
    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

    public void setDefaults(){
        if(file.exists()){return;}

        config.set("GameTime", 3600);
        config.set("GameStartTime", 60);
        config.set("DropItemTime", 30);
        config.set("RequiredPlayers", 3);

        saveFile();
    }

    public Integer getIntegerConfig(String integer){
        return config.getInt(integer);
    }

    public String getStringConfig(String string){
        return config.getString(string);
    }

    private void saveFile(){
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

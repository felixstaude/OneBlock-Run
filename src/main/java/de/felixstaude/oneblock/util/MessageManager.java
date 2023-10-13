package de.felixstaude.oneblock.util;

import org.bukkit.Bukkit;

public class MessageManager {
    private String prefix = "OneBlock-Run ";
    public void sendMessage(String message){
        Bukkit.broadcastMessage(prefix + message);
    }

    public String getPrefix(){
        return prefix;
    }
}

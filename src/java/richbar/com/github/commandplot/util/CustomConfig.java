package richbar.com.github.commandplot.util;

import java.io.File;

import net.minecraft.server.v1_10_R1.ChatMessage;
import net.minecraft.server.v1_10_R1.EnumChatFormat;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
 
public class CustomConfig {
       
        private final String configName;
        private File configFile;
        private FileConfiguration config;
        private final Plugin plugin;
       
        public CustomConfig(Plugin plugin, String configName) {
                this.plugin = plugin;
                this.configName = configName;
                createIfNoExist();
        }

        public ChatMessage getErrorTypeString(String path){
            ChatMessage cm = new ChatMessage(getConfig().getString(path));
            cm.getChatModifier().setColor(EnumChatFormat.DARK_RED);
            return cm;
        }

        public ChatMessage getErrorFormat(String s){
            ChatMessage cm = new ChatMessage(s);
            cm.getChatModifier().setColor(EnumChatFormat.DARK_RED);
            return cm;
        }

        public String getColoredString(String path){
            return ChatColor.translateAlternateColorCodes('&', getConfig().getString(path));
        }
       
        private File getConfigFile() {
                return this.configFile;
        }
       
        public String getConfigName() {
                return this.configName;
        }
       
        public boolean doesConfigExist() {
                return getConfigFile() != null && getConfigFile().exists();
        }
       
       
        private void createIfNoExist() {
                configFile = new File(this.plugin.getDataFolder(), this.configName.replace("/", File.separator));
                if(!this.configFile.exists()) {
                        if(this.plugin.getResource(configName) != null) {
                                this.plugin.saveResource(configName, false);
                        }
                        reloadConfig();
                }
                reloadConfig();
        }
       
       
        private void reloadConfig() {
                this.configFile = new File(this.plugin.getDataFolder(), this.configName);
                this.config = YamlConfiguration.loadConfiguration(this.configFile);
        }
       
        public boolean saveConfig() {
                if(config != null && configFile != null) {
                        try {
                                config.save(configFile);
                                return true;
                        } catch (Exception ignored) {}
                }
                return false;
        }
       
        public FileConfiguration getConfig() {
                reloadConfig();
                return this.config;
        }
       
}
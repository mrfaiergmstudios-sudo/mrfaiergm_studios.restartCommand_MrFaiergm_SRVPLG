package org.mrfaiergm_studios.restartCommand_MrFaiergm_SRVPLG;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.player.PlayerJoinEvent;

public final class RestartCommand_MrFaiergm_SRVPLG extends JavaPlugin implements Listener {
    public int timer = 20;
    BukkitTask bukkittimer = null;
    public String text;
    public String text_end;
    public String text_restart_canel;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        timer = getConfig().getInt("restart-timer");
        text = getConfig().getString("restart-text");
        text_end = getConfig().getString("restart-end");
        text_restart_canel = getConfig().getString("restart-canel");
        getCommand("prestart").setExecutor(this);
        getCommand("pstop").setExecutor(this);
        getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getConsoleSender().sendMessage("=======================================================");
        Bukkit.getConsoleSender().sendMessage("Хорошего дня! Спасибо за установку плагина! - MrFaiergm");
        Bukkit.getConsoleSender().sendMessage("Вас приветствует плагин - §f[§cMrFaiergmPLG]§f");
        Bukkit.getConsoleSender().sendMessage("=======================================================");

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("=======================================================");
        Bukkit.getConsoleSender().sendMessage("Хорошего дня! Спасибо за установку плагина! - MrFaiergm");
        Bukkit.getConsoleSender().sendMessage("§f[§cMrFaiergmPLG]§f - Удачи!");
        Bukkit.getConsoleSender().sendMessage("=======================================================");

    }

    @EventHandler
    public void IsOpJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        String playername = event.getPlayer().getName();
        if (player.isOp()){
            player.sendMessage("§f[§cMrFaiergmPLG]§f " + "§l§fПриветствую вас " + playername + "§l§f!");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (command.getName().equalsIgnoreCase("prestart")){
            String playername = sender.getName();
            if (sender.isOp()){
                for (Player p : getServer().getOnlinePlayers()){
                    if (p.isOp()){
                        p.sendMessage("[§cMrFaiergmPLG] " + "§l§fКтото Запустил перезапуск сервера! Если это были не вы пропишите команду" +  "§c§l/pstop");
                    }
                }
                new BukkitRunnable(){
                    @Override
                    public void run(){
                        for (Player p : getServer().getOnlinePlayers()){
                            if(p.isOp()){
                                p.sendMessage("§f[§cMrFaiergmPLG]§f " + "§lМы узнали кто инициировал перезапуск, его ник - " + playername );
                            }
                        }
                    }
                }.runTaskLater(this, 60L);
                bukkittimer = new BukkitRunnable(){
                    @Override
                    public void run(){
                        if (timer > 0) {
                            Bukkit.broadcastMessage("§f[§cMrFaiergmPLG§c]§f " + text + timer);
                            timer--;
                        }
                        if(timer <= 0){
                            this.cancel();
                            Bukkit.broadcastMessage("§f[§cMrFaiergmPLG]§f " + text_end);
                            new BukkitRunnable(){
                                @Override
                                public void run(){
                                    timer = getConfig().getInt("restart-timer");
                                    Bukkit.spigot().restart();
                                }
                            }.runTaskLater(RestartCommand_MrFaiergm_SRVPLG.this, 100L );

                        }
                    }
                }.runTaskTimer(RestartCommand_MrFaiergm_SRVPLG.this, 0l, 20l);
            }
        }
        if (command.getName().equalsIgnoreCase("pstop")){
            String playername = sender.getName();
            if(bukkittimer == null){
                sender.sendMessage("§f[§cMrFaiergmPLG]§f " + "Перезапуск ещё не запущен!");
            }
            if(bukkittimer != null && sender.isOp()){
                bukkittimer.cancel();
                bukkittimer = null;
                Bukkit.broadcastMessage("§f[§cMrFaiergmPLG]§f " + text_restart_canel);
                new BukkitRunnable(){
                    public void run(){
                        for (Player p : getServer().getOnlinePlayers()){
                            if(p.isOp()){
                                timer = getConfig().getInt("restart-timer");
                                p.sendMessage("§f[§cMrFaiergmPLG]§f " + "§c§lПерезапуск сервера отменен Администратором - " + playername);
                            }
                        }
                    }
                }.runTaskLater(this, 100L);
            }
        } return true;
    }


//    посхалка
}

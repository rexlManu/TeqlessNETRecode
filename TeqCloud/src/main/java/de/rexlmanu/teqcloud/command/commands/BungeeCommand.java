/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package de.rexlmanu.teqcloud.command.commands;

import de.rexlmanu.teqcloud.TeqCloud;
import de.rexlmanu.teqcloud.command.Command;
import de.rexlmanu.teqcloud.server.BungeeServer;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 12.05.2018 / 03:21                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public final class BungeeCommand extends Command {
    public BungeeCommand() {
        super("bungee", "Verwalte den Bungeecord.");
    }

    @Override
    public void perform(final String[] args) {
        final BungeeServer bungeeServer = TeqCloud.getInstance().getBungeeServer();
        if (args.length == 2) {
            switch (args[1].toLowerCase()) {
                case "start":
                    bungeeServer.start();
                    break;
                case "stop":
                    bungeeServer.stop();
                    break;
                case "konsole":
                    bungeeServer.toggleScreen();
                    TeqCloud.getInstance().getLogger().info("Tippe 'exit' ein um die Konsole zuverlassen.");
                    break;
                case "restart":
                    bungeeServer.restart();
                    break;
                default:
                    break;
            }
        } else if (args.length > 2) {
            if (args[1].equalsIgnoreCase("command")) {
                final StringBuilder stringBuilder = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    stringBuilder.append(args[i] + " ");
                }
                final String command = stringBuilder.toString();
                System.out.println(command);
                bungeeServer.sendCommand(command);
            } else {
                this.sendArgs();
            }
        } else {
            this.sendArgs();
        }
    }

    private void sendArgs() {
        System.out.println("Bungee - Argübersicht");
        System.out.println("->bungee start");
        System.out.println("->bungee restart");
        System.out.println("->bungee stop");
        System.out.println("->bungee konsole");
        System.out.println("->bungee command <Command>");
    }
}

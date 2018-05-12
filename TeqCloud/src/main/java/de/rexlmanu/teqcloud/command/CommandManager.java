/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package de.rexlmanu.teqcloud.command;


import com.google.common.reflect.ClassPath;
import de.rexlmanu.teqcloud.TeqCloud;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 12.05.2018 / 00:24                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public final class CommandManager {

    @Getter
    private final List<Command> commands;
    private Thread commandThread;
    private BufferedReader reader;

    public CommandManager() {
        this.commands = new ArrayList<>();
    }

    public void registerCommands() {
        try {
            for (final ClassPath.ClassInfo classInfo : ClassPath.from(this.getClass().getClassLoader()).getTopLevelClasses("de.rexlmanu.teqcloud.command.commands")) {
                final Class<?> clazz = Class.forName(classInfo.getName());
                if (Command.class.isAssignableFrom(clazz)) {
                    final Command command = (Command) clazz.newInstance();
                    TeqCloud.getInstance().getLogger().debug("Command " + command.getCommandLabel() + " wird registiert");
                    this.registerCommand(command);
                }
            }
        } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void enableConsole() {
        this.commandThread = new Thread(this::console);
        this.commandThread.start();
    }

    public void disableConsole() {
        this.commandThread.interrupt();
    }

    private void console() {
        try {
            this.reader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = this.reader.readLine()) != null) {
                if (line.equals("")) continue;

                if (TeqCloud.getInstance().getBungeeServer().isConsole()) {
                    if (line.equalsIgnoreCase("exit")) {
                        TeqCloud.getInstance().getLogger().info("Du hast die Konsole verlassen.");
                        TeqCloud.getInstance().getBungeeServer().toggleScreen();
                    } else {
                        TeqCloud.getInstance().getBungeeServer().sendCommand(line);
                    }
                } else {
                    String[] args = null;
                    if (!line.contains(" ")) {
                        args = new String[]{line};
                    } else args = line.split(" ");
                    boolean found = false;
                    for (final Command command : this.commands) {
                        if (args[0].equalsIgnoreCase(command.getCommandLabel())) {
                            command.perform(args);
                            found = true;
                        }
                    }
                    if (!found)
                        TeqCloud.getInstance().getLogger().info("Für eine Commandübersicht tippe bitte 'help' ein.");

                }

            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private void registerCommand(final Command command) {
        this.commands.add(command);
    }
}

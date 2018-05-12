/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package de.rexlmanu.teqcloud;

import de.rexlmanu.teqcloud.command.CommandManager;
import de.rexlmanu.teqcloud.file.FileManager;
import de.rexlmanu.teqcloud.file.VersionDownloader;
import de.rexlmanu.teqcloud.logger.LogLevel;
import de.rexlmanu.teqcloud.logger.Logger;
import de.rexlmanu.teqcloud.server.BungeeServer;
import de.rexlmanu.teqcloud.utils.AsyncSession;
import lombok.Getter;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 11.05.2018 / 23:34                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public final class TeqCloud {

    @Getter
    private static TeqCloud instance;
    @Getter
    private final Logger logger;
    @Getter
    private CommandManager commandManager;
    @Getter
    private FileManager fileManager;
    @Getter
    private VersionDownloader versionDownloader;
    @Getter
    private BungeeServer bungeeServer;
    @Getter
    private AsyncSession asyncSession;

    public TeqCloud(final LogLevel logLevel) {
        this.logger = new Logger(logLevel);
        instance = this;
    }

    public void onLaunch() {
        this.logger.info("TeqCloud v1 von rexlManu");
        this.logger.info("CloudSystem wird gestartet.");
        this.fetchClasses();

        this.commandManager.registerCommands();
        this.commandManager.enableConsole();

    }

    private void fetchClasses() {
        this.commandManager = new CommandManager();
        this.fileManager = new FileManager();
        this.versionDownloader = new VersionDownloader();
        this.bungeeServer = new BungeeServer();
        this.asyncSession = new AsyncSession();
    }

    public void onShutdown() {
        this.logger.info("TeqCloud wird beendet.");
    }
}

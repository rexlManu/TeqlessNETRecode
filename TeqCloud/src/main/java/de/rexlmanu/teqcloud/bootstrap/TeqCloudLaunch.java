/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package de.rexlmanu.teqcloud.bootstrap;

import de.rexlmanu.teqcloud.TeqCloud;
import de.rexlmanu.teqcloud.logger.LogLevel;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 11.05.2018 / 23:30                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public final class TeqCloudLaunch {

    public static void main(final String[] args) {
        LogLevel logLevel = LogLevel.INFO;
        if (args.length == 1) {
            final String[] data = args[0].split("=");
            if (data[0].equalsIgnoreCase("loglevel")) {
                logLevel = LogLevel.valueOf(data[1].toUpperCase());
            }
        }

        final TeqCloud teqCloud = new TeqCloud(logLevel);
        teqCloud.onLaunch();
        Runtime.getRuntime().addShutdownHook(new Thread(teqCloud::onShutdown));
    }
}

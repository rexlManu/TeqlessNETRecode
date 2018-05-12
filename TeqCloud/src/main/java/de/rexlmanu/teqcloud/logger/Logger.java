/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package de.rexlmanu.teqcloud.logger;

import lombok.AllArgsConstructor;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 11.05.2018 / 23:36                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

@AllArgsConstructor
public final class Logger {

    private LogLevel logLevel;

    public void log(final LogLevel logLevel, final String message) {
        if (this.logLevel.getPriority() >= logLevel.getPriority())
            System.out.println("[" + logLevel.getName() + "] " + message);
    }

    public void info(final String message) {
        this.log(LogLevel.INFO, message);
    }

    public void warning(final String message) {
        this.log(LogLevel.WARNING, message);
    }

    public void error(final String message) {
        this.log(LogLevel.ERROR, message);
    }

    public void debug(final String message) {
        this.log(LogLevel.DEBUG, message);
    }

}

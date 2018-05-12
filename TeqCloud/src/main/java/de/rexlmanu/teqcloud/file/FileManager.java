/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package de.rexlmanu.teqcloud.file;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 12.05.2018 / 01:40                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public final class FileManager {

    private final String[] files;
    private final Map<String, File> fileMap;

    public FileManager() {
        this.files = new String[]{
                "teqcloud/proxy",
                "teqcloud/config",
                "teqcloud/templates",
                "teqcloud/temp",
                "teqcloud/maps"};
        this.fileMap = new HashMap<>();
        this.createFiles();
    }

    private void createFiles() {
        for (final String file : this.files) {
            final File tempFile = new File("./" + file);
            tempFile.mkdir();
            this.fileMap.put(file.split("/")[1], tempFile);
        }
    }

    public File getDirectory(final String fileName) {
        return this.fileMap.get(fileName);
    }
}

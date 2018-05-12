/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package de.rexlmanu.teqcloud.file;

import de.rexlmanu.teqcloud.TeqCloud;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 12.05.2018 / 02:19                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public final class VersionDownloader {

    public void downloadJar(final File file, final Versions versions) {
        final File output = new File(file, versions.getJarName());
        if (output.exists()) return;
        try {
            TeqCloud.getInstance().getLogger().info("Downloading " + versions.getJarName() + "");
            FileUtils.copyURLToFile(new URL(versions.getDownloadLink()), output);
            final URL url = new URL(versions.getDownloadLink());
            final URLConnection urlConn = url.openConnection();
            urlConn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            final InputStream inputStream = urlConn.getInputStream();
            Files.copy(inputStream, new File(file, versions.getJarName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
            TeqCloud.getInstance().getLogger().info("Fertig mit downloaden von " + versions.getJarName() + "");
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @AllArgsConstructor
    @Getter
    public enum Versions {

        BUNGEECORD("BungeeCord.jar", "https://ci.md-5.net/job/BungeeCord/1311/artifact/bootstrap/target/BungeeCord.jar"),
        SPIGOT("spigot-1.8.8.jar", ""),
        WATERFALL("Waterfall.jar", "https://ci.destroystokyo.com/job/Waterfall/lastSuccessfulBuild/artifact/Waterfall-Proxy/bootstrap/target/Waterfall.jar");

        private String jarName;
        private String downloadLink;
    }
}

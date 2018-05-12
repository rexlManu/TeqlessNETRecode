/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package de.rexlmanu.teqcloud.server;

import de.rexlmanu.teqcloud.TeqCloud;
import de.rexlmanu.teqcloud.file.VersionDownloader;
import lombok.Getter;
import lombok.Setter;

import java.io.*;

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

public final class BungeeServer implements Server {

    @Getter
    @Setter
    private boolean console;
    private Process process;
    private final ProcessBuilder builder;
    private final String minRam;
    private final String maxRam;
    private BufferedReader reader;
    private BufferedWriter writer;

    public BungeeServer() {
        final File proxy = TeqCloud.getInstance().getFileManager().getDirectory("proxy");
        this.minRam = "-Xms128M";
        this.maxRam = "-Xmx512M";
        this.console = false;
        if (!new File(proxy, VersionDownloader.Versions.WATERFALL.getJarName()).exists()) {
            TeqCloud.getInstance().getVersionDownloader().downloadJar(TeqCloud.getInstance().getFileManager().getDirectory("proxy"), VersionDownloader.Versions.WATERFALL);
        }
        this.builder = new ProcessBuilder("java", "-jar", this.minRam, this.maxRam, VersionDownloader.Versions.WATERFALL.getJarName());
        this.builder.redirectErrorStream(true);
        this.builder.directory(proxy);
    }

    @Override
    public void start() {
        TeqCloud.getInstance().getLogger().info("Bungeecord Server wird gestartet.");
        try {
            this.process = this.builder.start();
            final InputStream inputStream = this.process.getInputStream();

            new Thread(() -> {
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                try {
                    while ((line = bufferedReader.readLine()) != null) {
                        if (this.console) System.out.println(line);
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        TeqCloud.getInstance().getLogger().info("Bungeecord Server wird gestoppt.");
        this.sendCommand("end");
        this.process.destroy();
    }

    @Override
    public void restart() {
        this.stop();
        this.start();
    }

    @Override
    public void toggleScreen() {
        this.console = !this.console;
    }

    public void sendCommand(final String command) {
        final OutputStream outputStream = this.process.getOutputStream();
        final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        try {
            bufferedWriter.write(command);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}

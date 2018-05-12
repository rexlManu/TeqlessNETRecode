/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package de.rexlmanu.teqcloud.utils;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 09.05.2018 / 12:04                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class AsyncSession {

    private static AsyncSession instance;
    private final ListeningExecutorService executorService;

    public AsyncSession() {
        this.executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(8));
    }

    public void executeAsync(final Runnable runnable) {
        this.executorService.execute(runnable);
    }

    public void scheduleAsync(final Runnable runnable, final Long sleepTime, final TimeUnit timeUnit) {
        this.executorService.execute(() -> {
            try {
                timeUnit.sleep(sleepTime);
                runnable.run();
            } catch (final Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    public void scheduleAsync(final Runnable runnable, final Long initializeTime, final Long sleepTime,
                              final TimeUnit timeUnit) {
        this.executorService.execute(() -> {
            try {
                timeUnit.sleep(initializeTime);
                while (true) {
                    timeUnit.sleep(sleepTime);
                    runnable.run();
                }
            } catch (final Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    public <T> void executeAsync(final T type, final Consumer<T> consumer) {
        this.executorService.execute(() -> consumer.accept(type));
    }

    public <T> void executeAsync(final T type, final Consumer<T> consumer, final Long sleepTime,
                                 final TimeUnit timeUnit) {
        this.executorService.execute(() -> {
            try {
                timeUnit.sleep(sleepTime);
                consumer.accept(type);
            } catch (final Exception exc) {
                exc.printStackTrace();
            }
        });
    }

    public static AsyncSession getInstance() {
        return AsyncSession.instance;
    }

    static {
        AsyncSession.instance = null;
        if (AsyncSession.instance == null) {
            AsyncSession.instance = new AsyncSession();
        }
    }
}

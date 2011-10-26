package org.bukkit.event;


import org.bukkit.TestServer;
import org.bukkit.event.test.SpeedTestEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Logger;

import static org.mockito.Mockito.*;

public class TestEventListener implements Listener {
    private JavaPlugin testPlugin;
    private final long itercount = 100000000L;

    @Before
    public void setUp() {
        testPlugin = mock(JavaPlugin.class);
        when(testPlugin.getPluginLoader()).thenReturn(new JavaPluginLoader(TestServer.getInstance()));
        // TestServer.getInstance().getPluginManager().registerEvents(this, testPlugin);
    }

    @Test
    public void speedTest() {
        Logger logger = TestServer.getInstance().getLogger();
        SpeedTestEvent testEvent = new SpeedTestEvent();
        long starttime = System.currentTimeMillis();
        logger.info("Beginning speed test, " + itercount + " iterations");
        for (long i = 0; i < itercount; i++) {
            TestServer.getInstance().getPluginManager().callEvent(testEvent);
        }
        long length = System.currentTimeMillis() - starttime;
        double perEvent = (double) length / (double) itercount;
        logger.info("End " + itercount + " iterations in "
                + (length / 1000.0) + " seconds: " + perEvent + "ms per event");
        logger.info("Event calls per second: " + (long) (itercount / (length / 1000.0)));
    }

    /* @EventHandler(event = SpeedTestEvent.class, priority = EventPriority.NORMAL)
    public void noopListener(SpeedTestEvent event) {
    } */
}

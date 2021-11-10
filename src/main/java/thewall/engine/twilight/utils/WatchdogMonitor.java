package thewall.engine.twilight.utils;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WatchdogMonitor implements WatchdogTimeMonitor{
    private final static Logger logger = LogManager.getLogger(WatchdogMonitor.class);
    volatile boolean keepAlive;
    volatile boolean isRun = true;
    volatile int timeout = 15000;
    private WatchdogThread thread;

    public void shutdown(){
        isRun = false;
    }

    @Override
    public long getKeepAliveTime() {
        return thread.lastTime;
    }

    class WatchdogThread extends Thread {
        public WatchdogThread(){
            setName("Watchdog Service");
        }



        private void init(){
            long startTime = System.currentTimeMillis();
            while (!keepAlive){
                if(System.currentTimeMillis() - startTime > timeout){
                    logger.error("Watchdog hasn't receive keep-alive command in time, closing");
                    System.exit(408);
                }
            }
            logger.info("Watchdog keep-alive received successfully, watchdog init complete");
        }

        boolean isAlert = false;
        volatile long lastTime = System.currentTimeMillis();
        @Override
        public void run() {
            init();
            keepAlive = false;
            while (isRun){
                if(keepAlive){
                    lastTime = System.currentTimeMillis();
                    keepAlive = false;
                    continue;
                }

                if(System.currentTimeMillis() - lastTime > (timeout / 2)) {
                    if (!isAlert) {
                        logger.warn("Watchdog detected a longer pause in the program, I am investigating the situation. Stand by.");
                        isAlert = true;
                    }
                }

                if(System.currentTimeMillis() - lastTime > timeout){
                    logger.fatal("Watchdog timeout, the watchdog did not receive a keep-alive command. The program has probably crashed");
                    new Thread(() -> {
                        WatchdogMonitor.this.stop();
                        System.exit(403);
                    }).start();
                    break;
                }
            }
        }
    }

    public void keepAlive(){
        keepAlive = true;
    }

    public void timeout(int millis){
        this.timeout = millis;
    }

    public void start(){
        logger.info("Watchdog started, working in background active");
        thread = new WatchdogThread();
        thread.start();
    }

    @SneakyThrows
    public void stop(){
        logger.info("Stopping watchdog, service going down...");
        isRun = false;
        Thread.sleep(100);
        if(thread.isAlive()){
            logger.info("Watchdog hasn't switch down in time, killing process");
            thread.interrupt();
        }
    }

}

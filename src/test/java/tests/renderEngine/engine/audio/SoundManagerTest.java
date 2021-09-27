package tests.renderEngine.engine.audio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thewall.game.tengine.audio.SoundChannel;
import thewall.game.tengine.audio.SoundManager;

class SoundManagerTest {
    private SoundManager soundManager;

    @Test
    void TestMethod() throws InterruptedException {
        SoundChannel soundChannel =  soundManager.playBackground(0.2f, 0.2f, "./res/music/theme1.wav");
        Thread.sleep(1000);
        while(true){
            if(!soundChannel.isOpen()){
                break;
            }
            Thread.onSpinWait();
        }
    }

    @BeforeEach
    void setUp(){
        soundManager = new SoundManager();
    }
}
package thewall.engine.tengine.debugger.console;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public final class ConsoleOutProxy extends PrintStream {
    @Setter
    private static DebugConsole console;
    @Getter
    private static final ConsoleOutProxy instance = new ConsoleOutProxy(System.out);

    private final static Logger logger = LogManager.getLogger("System.out");


    private ConsoleOutProxy(PrintStream systemOut){
        super(new OutStream());

        OutStream.getInstance().systemOut = systemOut;
    }

    private static class OutStream extends OutputStream {
        private PrintStream systemOut;

        private static final Logger logger = LogManager.getLogger(OutStream.class);

        @Getter
        private static OutStream instance;

        private final List<Character> characters = new ArrayList<>();

        public OutStream(){
            if(getInstance() != null){
                throw new IllegalStateException("Console proxy can has only one instance");
            }
            instance = this;
        }

        private void flushConsole(){
            StringBuilder stringBuilder = new StringBuilder();
            for(char character : characters){
                if(character == 0x0A || character == 0x0D){
                    continue;
                }
                stringBuilder.append((character));
            }
            console.info(stringBuilder.toString().trim());
            logger.info(stringBuilder.toString().trim());
            characters.clear();
        }

        @Override
        public void write(int b) throws IOException {
            if(systemOut != null && console != null){
                if(characters.size() > 4096){
                    logger.warn("Buffer print limit, over 2048 characters, flushing out...");
                    flushConsole();
                    return;
                }

                if(b == 0x0A){
                    if(!(characters.size() < 2)) {
                        flushConsole();
                    }
                }
                characters.add((char) b);
            }
        }
    }
}

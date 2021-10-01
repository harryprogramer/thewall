package thewall.engine.tengine.input.mouse;
/**
 A raw TEngine mouse interface. This can be get mouse movement and scrolly wheel.
 @author many
 */
public interface MouseWheel {
    /**
     * @return Movement of mouse wheel
     */
    int getDWheel();

    /**
     * @return Movement of axis X
     */
    int getDX();

    /**
     * @return Movement of axis Y
     */
    int getDY();
}

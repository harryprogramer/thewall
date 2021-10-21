package thewall.engine.twilight.input.keyboard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.lwjgl.glfw.GLFW.*;

public enum KeyboardKeys {
    W_KEY,
    S_KEY,
    A_KEY,
    D_KEY,
    Q_KEY,
    E_KEY,
    R_KEY,
    T_KEY,
    Y_KEY,
    U_KEY,
    I_KEY,
    O_KEY,
    P_KEY,
    F_KEY,
    G_KEY,
    H_KEY,
    J_KEY,
    K_KEY,
    L_KEY,
    Z_KEY,
    X_KEY,
    C_KEY,
    V_KEY,
    B_KEY,
    N_KEY,
    M_KEY,
    RIGHT_SHIFT_KEY,
    ESCAPE_KEY,
    SPACE_KEY,
    LEFT_SHIFT_KEY,
    LEFT_CONTROL_KEY,
    RIGHT_CONTROL_KEY,
    CAPSLOCK_KEY,
    TAB_KEY,
    F1_KEY,
    F2_KEY,
    F3_KEY,
    F4_KEY,
    F5_KEY,
    F6_KEY,
    F7_KEY,
    F8_KEY,
    F9_KEY,
    F10_KEY,
    F11_KEY,
    F12_KEY
    ;
    private static final Logger logger = LogManager.getLogger(KeyboardKeys.class);

    private static String lastEnumError = "";
    private static int lastErrorCode = -1;

    @Contract(pure = true)
    @Nullable
    public static KeyboardKeys keyToEnum(int code){
        switch (code){
            case GLFW_KEY_W -> {
                return W_KEY;
            }

            case GLFW_KEY_D -> {
                return D_KEY;
            }

            case GLFW_KEY_A -> {
                return A_KEY;
            }

            case GLFW_KEY_S -> {
                return S_KEY;
            }

            case GLFW_KEY_ESCAPE -> {
                return ESCAPE_KEY;
            }

            case GLFW_KEY_SPACE -> {
                return SPACE_KEY;
            }

            case GLFW_KEY_LEFT_SHIFT -> {
                return LEFT_SHIFT_KEY;
            }

            case GLFW_KEY_LEFT_CONTROL -> {
                return LEFT_CONTROL_KEY;
            }

            case GLFW_KEY_RIGHT_CONTROL -> {
                return RIGHT_CONTROL_KEY;
            }

            case GLFW_KEY_Q -> {
                return Q_KEY;
            }

            case GLFW_KEY_R -> {
                return R_KEY;
            }

            case GLFW_KEY_T -> {
                return T_KEY;
            }

            case GLFW_KEY_Y -> {
                return Y_KEY;
            }

            case GLFW_KEY_U -> {
                return U_KEY;
            }

            case GLFW_KEY_I -> {
                return I_KEY;
            }

            case GLFW_KEY_O -> {
                return O_KEY;
            }

            case GLFW_KEY_P -> {
                return P_KEY;
            }

            case GLFW_KEY_F -> {
                return F_KEY;
            }

            case GLFW_KEY_G -> {
                return G_KEY;
            }

            case GLFW_KEY_H -> {
                return H_KEY;
            }

            case GLFW_KEY_J -> {
                return J_KEY;
            }

            case GLFW_KEY_K -> {
                return K_KEY;
            }

            case GLFW_KEY_L -> {
                return L_KEY;
            }

            case GLFW_KEY_Z -> {
                return Z_KEY;
            }

            case GLFW_KEY_X -> {
                return X_KEY;
            }

            case GLFW_KEY_C -> {
                return C_KEY;
            }

            case GLFW_KEY_V -> {
                return V_KEY;
            }

            case GLFW_KEY_B -> {
                return B_KEY;
            }

            case GLFW_KEY_N -> {
                return N_KEY;
            }

            case GLFW_KEY_M -> {
                return M_KEY;
            }

            case GLFW_KEY_CAPS_LOCK -> {
                return CAPSLOCK_KEY;
            }

            case GLFW_KEY_TAB -> {
                return TAB_KEY;
            }

            case GLFW_KEY_F1 -> {
                return F1_KEY;
            }

            case GLFW_KEY_F2 -> {
                return F2_KEY;
            }

            case GLFW_KEY_F3 -> {
                return F3_KEY;
            }

            case GLFW_KEY_F4 -> {
                return F4_KEY;
            }

            case GLFW_KEY_F5 -> {
                return F5_KEY;
            }

            case GLFW_KEY_F6-> {
                return F6_KEY;
            }

            case GLFW_KEY_F7-> {
                return F7_KEY;
            }

            case GLFW_KEY_F8 -> {
                return F8_KEY;
            }

            case GLFW_KEY_F9 -> {
                return F9_KEY;
            }

            case GLFW_KEY_F10 -> {
                return F10_KEY;
            }

            case GLFW_KEY_F11 -> {
                return F11_KEY;
            }

            case GLFW_KEY_F12 -> {
                return F12_KEY;
            }



            default -> {
                if(!(lastErrorCode == code)) {
                    logger.warn("Unknown key id: " + code);
                }
                lastErrorCode = code;
                return null;
            }
        }
    }

    public static int enumToKey(@NotNull KeyboardKeys key){
        switch (key){
            case ESCAPE_KEY -> {
                return GLFW_KEY_ESCAPE;
            }

            case A_KEY -> {
                return GLFW_KEY_A;
            }

            case D_KEY -> {
                return GLFW_KEY_D;
            }

            case W_KEY -> {
                return GLFW_KEY_W;
            }

            case S_KEY -> {
                return GLFW_KEY_S;
            }

            case Q_KEY -> {
                return GLFW_KEY_Q;
            }

            case E_KEY -> {
                return GLFW_KEY_E;
            }

            case R_KEY -> {
                return GLFW_KEY_R;
            }

            case T_KEY -> {
                return GLFW_KEY_T;
            }

            case Y_KEY -> {
                return GLFW_KEY_Y;
            }

            case U_KEY -> {
                return GLFW_KEY_U;
            }

            case I_KEY -> {
                return GLFW_KEY_I;
            }

            case O_KEY -> {
                return GLFW_KEY_O;
            }

            case P_KEY -> {
                return GLFW_KEY_P;
            }

            case F_KEY -> {
                return GLFW_KEY_F;
            }

            case G_KEY -> {
                return GLFW_KEY_G;
            }

            case H_KEY -> {
                return GLFW_KEY_H;
            }

            case J_KEY -> {
                return GLFW_KEY_J;
            }

            case K_KEY -> {
                return GLFW_KEY_K;
            }

            case L_KEY -> {
                return GLFW_KEY_L;
            }

            case Z_KEY -> {
                return GLFW_KEY_Z;
            }

            case X_KEY -> {
                return GLFW_KEY_X;
            }

            case C_KEY -> {
                return GLFW_KEY_C;
            }

            case V_KEY -> {
                return GLFW_KEY_V;
            }

            case B_KEY -> {
                return GLFW_KEY_B;
            }

            case N_KEY -> {
                return GLFW_KEY_N;
            }

            case M_KEY -> {
                return GLFW_KEY_M;
            }

            case SPACE_KEY -> {
                return GLFW_KEY_SPACE;
            }

            case LEFT_SHIFT_KEY -> {
                return GLFW_KEY_LEFT_SHIFT;
            }

            case RIGHT_SHIFT_KEY -> {
                return GLFW_KEY_RIGHT_SHIFT;
            }

            case LEFT_CONTROL_KEY -> {
                return GLFW_KEY_LEFT_CONTROL;
            }

            default -> {
                if(!(key.name().equalsIgnoreCase(lastEnumError))) {
                    logger.warn("Unknown enum key: " + key.name());
                }
                lastEnumError = key.name();
                return 0;
            }
        }
    }
}

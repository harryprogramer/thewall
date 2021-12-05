package thewall.engine.twilight.renderer.opengl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30C;
import org.lwjgl.system.NativeType;

@SuppressWarnings("unused")
public interface GL3 extends GL2 {
    int GL_MAJOR_VERSION = 33307;
    int GL_MINOR_VERSION = 33308;
    int GL_NUM_EXTENSIONS = 33309;
    int GL_CONTEXT_FLAGS = 33310;
    int GL_CONTEXT_FLAG_FORWARD_COMPATIBLE_BIT = 1;
    int GL_COMPARE_REF_TO_TEXTURE = 34894;
    int GL_CLIP_DISTANCE0 = 12288;
    int GL_CLIP_DISTANCE1 = 12289;
    int GL_CLIP_DISTANCE2 = 12290;
    int GL_CLIP_DISTANCE3 = 12291;
    int GL_CLIP_DISTANCE4 = 12292;
    int GL_CLIP_DISTANCE5 = 12293;
    int GL_CLIP_DISTANCE6 = 12294;
    int GL_CLIP_DISTANCE7 = 12295;
    int GL_MAX_CLIP_DISTANCES = 3378;
    int GL_MAX_VARYING_COMPONENTS = 35659;
    int GL_VERTEX_ATTRIB_ARRAY_INTEGER = 35069;
    int GL_SAMPLER_1D_ARRAY = 36288;
    int GL_SAMPLER_2D_ARRAY = 36289;
    int GL_SAMPLER_1D_ARRAY_SHADOW = 36291;
    int GL_SAMPLER_2D_ARRAY_SHADOW = 36292;
    int GL_SAMPLER_CUBE_SHADOW = 36293;
    int GL_UNSIGNED_INT_VEC2 = 36294;
    int GL_UNSIGNED_INT_VEC3 = 36295;
    int GL_UNSIGNED_INT_VEC4 = 36296;
    int GL_INT_SAMPLER_1D = 36297;
    int GL_INT_SAMPLER_2D = 36298;
    int GL_INT_SAMPLER_3D = 36299;
    int GL_INT_SAMPLER_CUBE = 36300;
    int GL_INT_SAMPLER_1D_ARRAY = 36302;
    int GL_INT_SAMPLER_2D_ARRAY = 36303;
    int GL_UNSIGNED_INT_SAMPLER_1D = 36305;
    int GL_UNSIGNED_INT_SAMPLER_2D = 36306;
    int GL_UNSIGNED_INT_SAMPLER_3D = 36307;
    int GL_UNSIGNED_INT_SAMPLER_CUBE = 36308;
    int GL_UNSIGNED_INT_SAMPLER_1D_ARRAY = 36310;
    int GL_UNSIGNED_INT_SAMPLER_2D_ARRAY = 36311;
    int GL_MIN_PROGRAM_TEXEL_OFFSET = 35076;
    int GL_MAX_PROGRAM_TEXEL_OFFSET = 35077;
    int GL_QUERY_WAIT = 36371;
    int GL_QUERY_NO_WAIT = 36372;
    int GL_QUERY_BY_REGION_WAIT = 36373;
    int GL_QUERY_BY_REGION_NO_WAIT = 36374;
    int GL_MAP_READ_BIT = 1;
    int GL_MAP_WRITE_BIT = 2;
    int GL_MAP_INVALIDATE_RANGE_BIT = 4;
    int GL_MAP_INVALIDATE_BUFFER_BIT = 8;
    int GL_MAP_FLUSH_EXPLICIT_BIT = 16;
    int GL_MAP_UNSYNCHRONIZED_BIT = 32;
    int GL_BUFFER_ACCESS_FLAGS = 37151;
    int GL_BUFFER_MAP_LENGTH = 37152;
    int GL_BUFFER_MAP_OFFSET = 37153;
    int GL_CLAMP_VERTEX_COLOR = 35098;
    int GL_CLAMP_FRAGMENT_COLOR = 35099;
    int GL_CLAMP_READ_COLOR = 35100;
    int GL_FIXED_ONLY = 35101;
    int GL_DEPTH_COMPONENT32F = 36012;
    int GL_DEPTH32F_STENCIL8 = 36013;
    int GL_FLOAT_32_UNSIGNED_INT_24_8_REV = 36269;
    int GL_TEXTURE_RED_TYPE = 35856;
    int GL_TEXTURE_GREEN_TYPE = 35857;
    int GL_TEXTURE_BLUE_TYPE = 35858;
    int GL_TEXTURE_ALPHA_TYPE = 35859;
    int GL_TEXTURE_LUMINANCE_TYPE = 35860;
    int GL_TEXTURE_INTENSITY_TYPE = 35861;
    int GL_TEXTURE_DEPTH_TYPE = 35862;
    int GL_UNSIGNED_NORMALIZED = 35863;
    int GL_RGBA32F = 34836;
    int GL_RGB32F = 34837;
    int GL_RGBA16F = 34842;
    int GL_RGB16F = 34843;
    int GL_R11F_G11F_B10F = 35898;
    int GL_UNSIGNED_INT_10F_11F_11F_REV = 35899;
    int GL_RGB9_E5 = 35901;
    int GL_UNSIGNED_INT_5_9_9_9_REV = 35902;
    int GL_TEXTURE_SHARED_SIZE = 35903;
    int GL_FRAMEBUFFER = 36160;
    int GL_READ_FRAMEBUFFER = 36008;
    int GL_DRAW_FRAMEBUFFER = 36009;
    int GL_RENDERBUFFER = 36161;
    int GL_STENCIL_INDEX1 = 36166;
    int GL_STENCIL_INDEX4 = 36167;
    int GL_STENCIL_INDEX8 = 36168;
    int GL_STENCIL_INDEX16 = 36169;
    int GL_RENDERBUFFER_WIDTH = 36162;
    int GL_RENDERBUFFER_HEIGHT = 36163;
    int GL_RENDERBUFFER_INTERNAL_FORMAT = 36164;
    int GL_RENDERBUFFER_RED_SIZE = 36176;
    int GL_RENDERBUFFER_GREEN_SIZE = 36177;
    int GL_RENDERBUFFER_BLUE_SIZE = 36178;
    int GL_RENDERBUFFER_ALPHA_SIZE = 36179;
    int GL_RENDERBUFFER_DEPTH_SIZE = 36180;
    int GL_RENDERBUFFER_STENCIL_SIZE = 36181;
    int GL_RENDERBUFFER_SAMPLES = 36011;
    int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE = 36048;
    int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME = 36049;
    int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL = 36050;
    int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE = 36051;
    int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LAYER = 36052;
    int GL_FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING = 33296;
    int GL_FRAMEBUFFER_ATTACHMENT_COMPONENT_TYPE = 33297;
    int GL_FRAMEBUFFER_ATTACHMENT_RED_SIZE = 33298;
    int GL_FRAMEBUFFER_ATTACHMENT_GREEN_SIZE = 33299;
    int GL_FRAMEBUFFER_ATTACHMENT_BLUE_SIZE = 33300;
    int GL_FRAMEBUFFER_ATTACHMENT_ALPHA_SIZE = 33301;
    int GL_FRAMEBUFFER_ATTACHMENT_DEPTH_SIZE = 33302;
    int GL_FRAMEBUFFER_ATTACHMENT_STENCIL_SIZE = 33303;
    int GL_FRAMEBUFFER_DEFAULT = 33304;
    int GL_INDEX = 33314;
    int GL_COLOR_ATTACHMENT0 = 36064;
    int GL_COLOR_ATTACHMENT1 = 36065;
    int GL_COLOR_ATTACHMENT2 = 36066;
    int GL_COLOR_ATTACHMENT3 = 36067;
    int GL_COLOR_ATTACHMENT4 = 36068;
    int GL_COLOR_ATTACHMENT5 = 36069;
    int GL_COLOR_ATTACHMENT6 = 36070;
    int GL_COLOR_ATTACHMENT7 = 36071;
    int GL_COLOR_ATTACHMENT8 = 36072;
    int GL_COLOR_ATTACHMENT9 = 36073;
    int GL_COLOR_ATTACHMENT10 = 36074;
    int GL_COLOR_ATTACHMENT11 = 36075;
    int GL_COLOR_ATTACHMENT12 = 36076;
    int GL_COLOR_ATTACHMENT13 = 36077;
    int GL_COLOR_ATTACHMENT14 = 36078;
    int GL_COLOR_ATTACHMENT15 = 36079;
    int GL_COLOR_ATTACHMENT16 = 36080;
    int GL_COLOR_ATTACHMENT17 = 36081;
    int GL_COLOR_ATTACHMENT18 = 36082;
    int GL_COLOR_ATTACHMENT19 = 36083;
    int GL_COLOR_ATTACHMENT20 = 36084;
    int GL_COLOR_ATTACHMENT21 = 36085;
    int GL_COLOR_ATTACHMENT22 = 36086;
    int GL_COLOR_ATTACHMENT23 = 36087;
    int GL_COLOR_ATTACHMENT24 = 36088;
    int GL_COLOR_ATTACHMENT25 = 36089;
    int GL_COLOR_ATTACHMENT26 = 36090;
    int GL_COLOR_ATTACHMENT27 = 36091;
    int GL_COLOR_ATTACHMENT28 = 36092;
    int GL_COLOR_ATTACHMENT29 = 36093;
    int GL_COLOR_ATTACHMENT30 = 36094;
    int GL_COLOR_ATTACHMENT31 = 36095;
    int GL_DEPTH_ATTACHMENT = 36096;
    int GL_STENCIL_ATTACHMENT = 36128;
    int GL_DEPTH_STENCIL_ATTACHMENT = 33306;
    int GL_MAX_SAMPLES = 36183;
    int GL_FRAMEBUFFER_COMPLETE = 36053;
    int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT = 36054;
    int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT = 36055;
    int GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER = 36059;
    int GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER = 36060;
    int GL_FRAMEBUFFER_UNSUPPORTED = 36061;
    int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE = 36182;
    int GL_FRAMEBUFFER_UNDEFINED = 33305;
    int GL_FRAMEBUFFER_BINDING = 36006;
    int GL_DRAW_FRAMEBUFFER_BINDING = 36006;
    int GL_READ_FRAMEBUFFER_BINDING = 36010;
    int GL_RENDERBUFFER_BINDING = 36007;
    int GL_MAX_COLOR_ATTACHMENTS = 36063;
    int GL_MAX_RENDERBUFFER_SIZE = 34024;
    int GL_INVALID_FRAMEBUFFER_OPERATION = 1286;
    int GL_DEPTH_STENCIL = 34041;
    int GL_UNSIGNED_INT_24_8 = 34042;
    int GL_DEPTH24_STENCIL8 = 35056;
    int GL_TEXTURE_STENCIL_SIZE = 35057;
    int GL_HALF_FLOAT = 5131;
    int GL_RGBA32UI = 36208;
    int GL_RGB32UI = 36209;
    int GL_RGBA16UI = 36214;
    int GL_RGB16UI = 36215;
    int GL_RGBA8UI = 36220;
    int GL_RGB8UI = 36221;
    int GL_RGBA32I = 36226;
    int GL_RGB32I = 36227;
    int GL_RGBA16I = 36232;
    int GL_RGB16I = 36233;
    int GL_RGBA8I = 36238;
    int GL_RGB8I = 36239;
    int GL_RED_INTEGER = 36244;
    int GL_GREEN_INTEGER = 36245;
    int GL_BLUE_INTEGER = 36246;
    int GL_ALPHA_INTEGER = 36247;
    int GL_RGB_INTEGER = 36248;
    int GL_RGBA_INTEGER = 36249;
    int GL_BGR_INTEGER = 36250;
    int GL_BGRA_INTEGER = 36251;
    int GL_TEXTURE_1D_ARRAY = 35864;
    int GL_TEXTURE_2D_ARRAY = 35866;
    int GL_PROXY_TEXTURE_2D_ARRAY = 35867;
    int GL_PROXY_TEXTURE_1D_ARRAY = 35865;
    int GL_TEXTURE_BINDING_1D_ARRAY = 35868;
    int GL_TEXTURE_BINDING_2D_ARRAY = 35869;
    int GL_MAX_ARRAY_TEXTURE_LAYERS = 35071;
    int GL_COMPRESSED_RED_RGTC1 = 36283;
    int GL_COMPRESSED_SIGNED_RED_RGTC1 = 36284;
    int GL_COMPRESSED_RG_RGTC2 = 36285;
    int GL_COMPRESSED_SIGNED_RG_RGTC2 = 36286;
    int GL_R8 = 33321;
    int GL_R16 = 33322;
    int GL_RG8 = 33323;
    int GL_RG16 = 33324;
    int GL_R16F = 33325;
    int GL_R32F = 33326;
    int GL_RG16F = 33327;
    int GL_RG32F = 33328;
    int GL_R8I = 33329;
    int GL_R8UI = 33330;
    int GL_R16I = 33331;
    int GL_R16UI = 33332;
    int GL_R32I = 33333;
    int GL_R32UI = 33334;
    int GL_RG8I = 33335;
    int GL_RG8UI = 33336;
    int GL_RG16I = 33337;
    int GL_RG16UI = 33338;
    int GL_RG32I = 33339;
    int GL_RG32UI = 33340;
    int GL_RG = 33319;
    int GL_COMPRESSED_RED = 33317;
    int GL_COMPRESSED_RG = 33318;
    int GL_RG_INTEGER = 33320;
    int GL_TRANSFORM_FEEDBACK_BUFFER = 35982;
    int GL_TRANSFORM_FEEDBACK_BUFFER_START = 35972;
    int GL_TRANSFORM_FEEDBACK_BUFFER_SIZE = 35973;
    int GL_TRANSFORM_FEEDBACK_BUFFER_BINDING = 35983;
    int GL_INTERLEAVED_ATTRIBS = 35980;
    int GL_SEPARATE_ATTRIBS = 35981;
    int GL_PRIMITIVES_GENERATED = 35975;
    int GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN = 35976;
    int GL_RASTERIZER_DISCARD = 35977;
    int GL_MAX_TRANSFORM_FEEDBACK_INTERLEAVED_COMPONENTS = 35978;
    int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_ATTRIBS = 35979;
    int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_COMPONENTS = 35968;
    int GL_TRANSFORM_FEEDBACK_VARYINGS = 35971;
    int GL_TRANSFORM_FEEDBACK_BUFFER_MODE = 35967;
    int GL_TRANSFORM_FEEDBACK_VARYING_MAX_LENGTH = 35958;
    int GL_VERTEX_ARRAY_BINDING = 34229;
    int GL_FRAMEBUFFER_SRGB = 36281;

    int GL_R8_SNORM = 36756;
    int GL_RG8_SNORM = 36757;
    int GL_RGB8_SNORM = 36758;
    int GL_RGBA8_SNORM = 36759;
    int GL_R16_SNORM = 36760;
    int GL_RG16_SNORM = 36761;
    int GL_RGB16_SNORM = 36762;
    int GL_RGBA16_SNORM = 36763;
    int GL_SIGNED_NORMALIZED = 36764;
    int GL_SAMPLER_BUFFER = 36290;
    int GL_INT_SAMPLER_2D_RECT = 36301;
    int GL_INT_SAMPLER_BUFFER = 36304;
    int GL_UNSIGNED_INT_SAMPLER_2D_RECT = 36309;
    int GL_UNSIGNED_INT_SAMPLER_BUFFER = 36312;
    int GL_COPY_READ_BUFFER = 36662;
    int GL_COPY_WRITE_BUFFER = 36663;
    int GL_PRIMITIVE_RESTART = 36765;
    int GL_PRIMITIVE_RESTART_INDEX = 36766;
    int GL_TEXTURE_BUFFER = 35882;
    int GL_MAX_TEXTURE_BUFFER_SIZE = 35883;
    int GL_TEXTURE_BINDING_BUFFER = 35884;
    int GL_TEXTURE_BUFFER_DATA_STORE_BINDING = 35885;
    int GL_TEXTURE_RECTANGLE = 34037;
    int GL_TEXTURE_BINDING_RECTANGLE = 34038;
    int GL_PROXY_TEXTURE_RECTANGLE = 34039;
    int GL_MAX_RECTANGLE_TEXTURE_SIZE = 34040;
    int GL_SAMPLER_2D_RECT = 35683;
    int GL_SAMPLER_2D_RECT_SHADOW = 35684;
    int GL_UNIFORM_BUFFER = 35345;
    int GL_UNIFORM_BUFFER_BINDING = 35368;
    int GL_UNIFORM_BUFFER_START = 35369;
    int GL_UNIFORM_BUFFER_SIZE = 35370;
    int GL_MAX_VERTEX_UNIFORM_BLOCKS = 35371;
    int GL_MAX_GEOMETRY_UNIFORM_BLOCKS = 35372;
    int GL_MAX_FRAGMENT_UNIFORM_BLOCKS = 35373;
    int GL_MAX_COMBINED_UNIFORM_BLOCKS = 35374;
    int GL_MAX_UNIFORM_BUFFER_BINDINGS = 35375;
    int GL_MAX_UNIFORM_BLOCK_SIZE = 35376;
    int GL_MAX_COMBINED_VERTEX_UNIFORM_COMPONENTS = 35377;
    int GL_MAX_COMBINED_GEOMETRY_UNIFORM_COMPONENTS = 35378;
    int GL_MAX_COMBINED_FRAGMENT_UNIFORM_COMPONENTS = 35379;
    int GL_UNIFORM_BUFFER_OFFSET_ALIGNMENT = 35380;
    int GL_ACTIVE_UNIFORM_BLOCK_MAX_NAME_LENGTH = 35381;
    int GL_ACTIVE_UNIFORM_BLOCKS = 35382;
    int GL_UNIFORM_TYPE = 35383;
    int GL_UNIFORM_SIZE = 35384;
    int GL_UNIFORM_NAME_LENGTH = 35385;
    int GL_UNIFORM_BLOCK_INDEX = 35386;
    int GL_UNIFORM_OFFSET = 35387;
    int GL_UNIFORM_ARRAY_STRIDE = 35388;
    int GL_UNIFORM_MATRIX_STRIDE = 35389;
    int GL_UNIFORM_IS_ROW_MAJOR = 35390;
    int GL_UNIFORM_BLOCK_BINDING = 35391;
    int GL_UNIFORM_BLOCK_DATA_SIZE = 35392;
    int GL_UNIFORM_BLOCK_NAME_LENGTH = 35393;
    int GL_UNIFORM_BLOCK_ACTIVE_UNIFORMS = 35394;
    int GL_UNIFORM_BLOCK_ACTIVE_UNIFORM_INDICES = 35395;
    int GL_UNIFORM_BLOCK_REFERENCED_BY_VERTEX_SHADER = 35396;
    int GL_UNIFORM_BLOCK_REFERENCED_BY_GEOMETRY_SHADER = 35397;
    int GL_UNIFORM_BLOCK_REFERENCED_BY_FRAGMENT_SHADER = 35398;
    int GL_INVALID_INDEX = -1;

    int GL_CONTEXT_PROFILE_MASK = 37158;
    int GL_CONTEXT_CORE_PROFILE_BIT = 1;
    int GL_CONTEXT_COMPATIBILITY_PROFILE_BIT = 2;
    int GL_MAX_VERTEX_OUTPUT_COMPONENTS = 37154;
    int GL_MAX_GEOMETRY_INPUT_COMPONENTS = 37155;
    int GL_MAX_GEOMETRY_OUTPUT_COMPONENTS = 37156;
    int GL_MAX_FRAGMENT_INPUT_COMPONENTS = 37157;
    int GL_FIRST_VERTEX_CONVENTION = 36429;
    int GL_LAST_VERTEX_CONVENTION = 36430;
    int GL_PROVOKING_VERTEX = 36431;
    int GL_QUADS_FOLLOW_PROVOKING_VERTEX_CONVENTION = 36428;
    int GL_TEXTURE_CUBE_MAP_SEAMLESS = 34895;
    int GL_SAMPLE_POSITION = 36432;
    int GL_SAMPLE_MASK = 36433;
    int GL_SAMPLE_MASK_VALUE = 36434;
    int GL_TEXTURE_2D_MULTISAMPLE = 37120;
    int GL_PROXY_TEXTURE_2D_MULTISAMPLE = 37121;
    int GL_TEXTURE_2D_MULTISAMPLE_ARRAY = 37122;
    int GL_PROXY_TEXTURE_2D_MULTISAMPLE_ARRAY = 37123;
    int GL_MAX_SAMPLE_MASK_WORDS = 36441;
    int GL_MAX_COLOR_TEXTURE_SAMPLES = 37134;
    int GL_MAX_DEPTH_TEXTURE_SAMPLES = 37135;
    int GL_MAX_INTEGER_SAMPLES = 37136;
    int GL_TEXTURE_BINDING_2D_MULTISAMPLE = 37124;
    int GL_TEXTURE_BINDING_2D_MULTISAMPLE_ARRAY = 37125;
    int GL_TEXTURE_SAMPLES = 37126;
    int GL_TEXTURE_FIXED_SAMPLE_LOCATIONS = 37127;
    int GL_SAMPLER_2D_MULTISAMPLE = 37128;
    int GL_INT_SAMPLER_2D_MULTISAMPLE = 37129;
    int GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE = 37130;
    int GL_SAMPLER_2D_MULTISAMPLE_ARRAY = 37131;
    int GL_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = 37132;
    int GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = 37133;
    int GL_DEPTH_CLAMP = 34383;
    int GL_GEOMETRY_SHADER = 36313;
    int GL_GEOMETRY_VERTICES_OUT = 36314;
    int GL_GEOMETRY_INPUT_TYPE = 36315;
    int GL_GEOMETRY_OUTPUT_TYPE = 36316;
    int GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS = 35881;
    int GL_MAX_GEOMETRY_UNIFORM_COMPONENTS = 36319;
    int GL_MAX_GEOMETRY_OUTPUT_VERTICES = 36320;
    int GL_MAX_GEOMETRY_TOTAL_OUTPUT_COMPONENTS = 36321;
    int GL_LINES_ADJACENCY = 10;
    int GL_LINE_STRIP_ADJACENCY = 11;
    int GL_TRIANGLES_ADJACENCY = 12;
    int GL_TRIANGLE_STRIP_ADJACENCY = 13;
    int GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS = 36264;
    int GL_FRAMEBUFFER_ATTACHMENT_LAYERED = 36263;
    int GL_PROGRAM_POINT_SIZE = 34370;
    int GL_MAX_SERVER_WAIT_TIMEOUT = 37137;
    int GL_OBJECT_TYPE = 37138;
    int GL_SYNC_CONDITION = 37139;
    int GL_SYNC_STATUS = 37140;
    int GL_SYNC_FLAGS = 37141;
    int GL_SYNC_FENCE = 37142;
    int GL_SYNC_GPU_COMMANDS_COMPLETE = 37143;
    int GL_UNSIGNALED = 37144;
    int GL_SIGNALED = 37145;
    int GL_SYNC_FLUSH_COMMANDS_BIT = 1;
    long GL_TIMEOUT_IGNORED = -1L;
    int GL_ALREADY_SIGNALED = 37146;
    int GL_TIMEOUT_EXPIRED = 37147;
    int GL_CONDITION_SATISFIED = 37148;
    int GL_WAIT_FAILED = 37149;
    int GL_SRC1_COLOR = 35065;
    int GL_ONE_MINUS_SRC1_COLOR = 35066;
    int GL_ONE_MINUS_SRC1_ALPHA = 35067;
    int GL_MAX_DUAL_SOURCE_DRAW_BUFFERS = 35068;
    int GL_ANY_SAMPLES_PASSED = 35887;
    int GL_SAMPLER_BINDING = 35097;
    int GL_RGB10_A2UI = 36975;
    int GL_TEXTURE_SWIZZLE_R = 36418;
    int GL_TEXTURE_SWIZZLE_G = 36419;
    int GL_TEXTURE_SWIZZLE_B = 36420;
    int GL_TEXTURE_SWIZZLE_A = 36421;
    int GL_TEXTURE_SWIZZLE_RGBA = 36422;
    int GL_TIME_ELAPSED = 35007;
    int GL_TIMESTAMP = 36392;
    int GL_VERTEX_ATTRIB_ARRAY_DIVISOR = 35070;
    int GL_INT_2_10_10_10_REV = 36255;

    void glBindVertexArray(int vaoID);

    void glDeleteVertexArrays(Integer vao);

    int glGenVertexArrays();

    /**
     * Generate mipmaps for a specified texture target.
     *
     * @param target the target to which the texture whose mimaps to generate is bound. One of:<br><table><tr><td>{@link GL11#GL_TEXTURE_1D TEXTURE_1D}</td><td>{@link GL11#GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL12#GL_TEXTURE_3D TEXTURE_3D}</td><td>{@link GL30C#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td><td>{@link GL30C#GL_TEXTURE_2D_ARRAY TEXTURE_2D_ARRAY}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP TEXTURE_CUBE_MAP}</td></tr></table>
     *
     * @see <a target="_blank" href="http://docs.gl/gl4/glGenerateMipmap">Reference Page</a>
     */
    void glGenerateMipmap(int target);
}
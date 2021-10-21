package thewall;

import org.jetbrains.annotations.NotNull;

import java.lang.constant.Constable;
import java.lang.constant.ConstantDesc;
import java.lang.invoke.MethodHandles;
import java.util.Optional;

public final class TestNumber extends Number implements Comparable<TestNumber>, Constable, ConstantDesc {
    @Override
    public int intValue() {
        return 0;
    }

    @Override
    public long longValue() {
        return 0;
    }

    @Override
    public float floatValue() {
        return 0;
    }

    @Override
    public double doubleValue() {
        return 0;
    }

    @Override
    public int compareTo(@NotNull TestNumber o) {
        return 0;
    }

    @Override
    public Optional<? extends ConstantDesc> describeConstable() {
        return Optional.empty();
    }

    @Override
    public Object resolveConstantDesc(MethodHandles.Lookup lookup) throws ReflectiveOperationException {
        return null;
    }
}

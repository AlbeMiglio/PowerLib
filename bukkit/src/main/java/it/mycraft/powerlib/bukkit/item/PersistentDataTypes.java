package it.mycraft.powerlib.bukkit.item;

import com.google.common.collect.ImmutableSet;
import org.bukkit.persistence.PersistentDataType;

public class PersistentDataTypes {

    private static ImmutableSet<PersistentDataType> values;

    public static ImmutableSet<PersistentDataType> values() {
        return values;
    }

    static {
        values = ImmutableSet.<PersistentDataType>builder()
                .add(PersistentDataType.BYTE)
                .add(PersistentDataType.BYTE_ARRAY)
                .add(PersistentDataType.DOUBLE)
                .add(PersistentDataType.BOOLEAN)
                .add(PersistentDataType.FLOAT)
                .add(PersistentDataType.INTEGER)
                .add(PersistentDataType.INTEGER_ARRAY)
                .add(PersistentDataType.LONG)
                .add(PersistentDataType.LONG_ARRAY)
                .add(PersistentDataType.SHORT)
                .add(PersistentDataType.STRING)
                .add(PersistentDataType.TAG_CONTAINER)
                .add(PersistentDataType.TAG_CONTAINER_ARRAY)
                .build();
    }
}

module it.mycraft.powerlib.common {
    exports it.mycraft.powerlib.common.chat;
    exports it.mycraft.powerlib.common.chance;
    exports it.mycraft.powerlib.common.enums;
    exports it.mycraft.powerlib.common.utils;
    exports it.mycraft.powerlib.common.configuration;
    requires com.google.common;
    requires java.json;
    requires lombok;
    requires org.yaml.snakeyaml;
    requires com.google.gson;
}
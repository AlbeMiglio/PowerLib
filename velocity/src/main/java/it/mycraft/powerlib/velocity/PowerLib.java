package it.mycraft.powerlib.velocity;

import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;

public class PowerLib {

    @Getter
    private static ProxyServer proxy;

    public static void inject(ProxyServer proxyServer) {
        proxy = proxyServer;
    }
}

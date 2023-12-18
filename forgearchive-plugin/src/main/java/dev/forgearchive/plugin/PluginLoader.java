package dev.forgearchive.plugin;

import java.util.*;
public final class PluginLoader {
    private final Map<String, PluginDescriptor> loaded = new HashMap<>();

    public void register(PluginDescriptor d) { loaded.put(d.id(), d); }
    public Optional<PluginDescriptor> get(String id) { return Optional.ofNullable(loaded.get(id)); }
    public Collection<PluginDescriptor> all() { return Collections.unmodifiableCollection(loaded.values()); }

}

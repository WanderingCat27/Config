package org.notdev.config;


import com.google.gson.*;
import net.minecraft.util.Identifier;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// https://github.com/QuiltServerTools/HeyThatsMine/blob/master/src/main/java/com/github/fabricservertools/htm/config/HTMConfig.java
public class ConfigBuilder {

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Identifier.class, new IdentifierSerializer())
            .registerTypeAdapter(Identifier.class, new IdentifierDeserializer())
            .setPrettyPrinting()
            .create();

    protected Map<String, Object> configMap = new HashMap<>();

    public ConfigBuilder() {
    }

    public static ConfigBuilder loadConfig(File file) {
        ConfigBuilder config;

        if (file.exists() && file.isFile()) {
            try (
                    FileInputStream fileInputStream = new FileInputStream(file);
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
            ) {
                config = GSON.fromJson(bufferedReader, ConfigBuilder.class);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load config", e);
            }
        } else {
            config = new ConfigBuilder();
        }

        config.saveConfig(file);

        return config;
    }

    public void addItem(String name, Object var) {
        configMap.put(name, var);
    }

    public void saveConfig(File config) {
        try (
                FileOutputStream stream = new FileOutputStream(config);
                Writer writer = new OutputStreamWriter(stream, StandardCharsets.UTF_8)
        ) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            System.err.println("Failed to save config");
        }
    }

    public Object getObject(String key) {
        if(!configMap.containsKey(key))
            return null;
        return configMap.get(key);
    }

    public int getInt(String key) {
        Object object = getObject(key);
        if(object != null) {
        return object instanceof Double ? (int) Math.round((double) object) : (int) object;
        }
        throw new RuntimeException("Object " + key + " does not exist (null pointer)");
    }

    public int getLong(String key) {
        Object object = getObject(key);
        if(object != null) {
            return object instanceof Double ? (int) Math.round((double) object) : (int) object;
        }
        throw new RuntimeException("Object " + key + " does not exist (null pointer)");
    }

    public double getDouble(String key) {
        Object object = getObject(key);
        if(object != null) {
            return (double) object;
        }
        throw new RuntimeException("Object " + key + " does not exist (null pointer)");
    }

    public float getFloat(String key) {
        Object object = getObject(key);
        if(object != null) {
            return (float) object;
        }
        throw new RuntimeException("Object " + key + " does not exist (null pointer)");
    }

    public String getString(String key) {
        Object object = getObject(key);
        if(object != null) {
            return (String) object;
        }
        throw new RuntimeException("Object " + key + " does not exist (null pointer)");
    }

    public Object[] getArray(String key) {
        Object object = getObject(key);
        if(object != null) {
            return (Object[]) object;
        }
        throw new RuntimeException("Object " + key + " does not exist (null pointer)");
    }

    public HashMap getMap(String key) {
        Object object = getObject(key);
        if(object != null) {
            return (HashMap) object;
        }
        throw new RuntimeException("Object " + key + " does not exist (null pointer)");
    }

    public static class IdentifierSerializer implements JsonSerializer<Identifier> {
        @Override
        public JsonElement serialize(Identifier src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }
    }

    public static class IdentifierDeserializer implements JsonDeserializer<Identifier> {
        @Override
        public Identifier deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json.isJsonObject()) {
                return GSON.fromJson(json, Identifier.class);
            }

            return Identifier.tryParse(json.getAsString());
        }
    }
}

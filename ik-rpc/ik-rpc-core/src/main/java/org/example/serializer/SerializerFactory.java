package org.example.serializer;

import org.example.SpiSerializerLoader;

import java.util.HashMap;
import java.util.Map;

public class SerializerFactory {
    private static Map<String , Serializer> SerializerMap = new HashMap<String , Serializer>()
    {{  put("json" , new JsonSerializer());
        put("jdk",new JdkSerializer());
        put("hessian", new HessianSerializer());
        put("kryo", new KryoSerializer());
    }};
    static{
        SpiSerializerLoader.loadSerializer(Serializer.class);
    }

    private static final Serializer DEFAULT_SERIALIZER = SerializerMap.get("jdk");

    public static Serializer getInstance(String key){
        return SerializerMap.getOrDefault(key, DEFAULT_SERIALIZER);
    }

}

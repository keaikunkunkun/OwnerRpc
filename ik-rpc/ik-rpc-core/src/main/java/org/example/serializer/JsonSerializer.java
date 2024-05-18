package org.example.serializer;



import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.RpcRequest;
import org.example.model.RpcResponse;

import java.io.IOException;

public class JsonSerializer implements Serializer{
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Override
    public <T> byte[] serialize(T object) throws IOException {
        byte[] bytes = OBJECT_MAPPER.writeValueAsBytes(object);
        return bytes;
    }

    //在反序列化时object数组会进行类型擦除
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        T t = OBJECT_MAPPER.readValue(bytes, type);
        if(t instanceof RpcRequest){
           return handleRequest((RpcRequest) t,type);
        } else if (t instanceof RpcResponse) {
           return  handleResponse((RpcResponse) t,type);
        }
        return t;
    }

    public <T> T handleRequest(RpcRequest rpcRequest , Class<T> type) throws IOException{
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] args = rpcRequest.getArgs();
        for (int i = 0 ; i < parameterTypes.length ; i++){
            Class<?> parameterType = parameterTypes[i];
            if(!parameterType.isAssignableFrom(args[i].getClass())){
                byte[] bytes = OBJECT_MAPPER.writeValueAsBytes(args[i]);
                args[i] = OBJECT_MAPPER.readValue(bytes, parameterType);
            }
        }
        return type.cast(rpcRequest);
    }

    public <T> T handleResponse(RpcResponse rpcResponse , Class<T> type) throws IOException{
        byte[] bytes = OBJECT_MAPPER.writeValueAsBytes(rpcResponse.getResult());
        rpcResponse.setResult(OBJECT_MAPPER.readValue(bytes,rpcResponse.getType()));
        return type.cast(rpcResponse);
    }

}

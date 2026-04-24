package com.example.demo.service;

import com.example.demo.thrift.userInfo;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.springframework.stereotype.Service;

@Service
public class ThriftUserService {
    
    /**
     * 序列化userInfo对象为字节数组
     */
    public byte[] serialize(userInfo info) throws TException {
        TSerializer serializer = new TSerializer(new TBinaryProtocol.Factory());
        return serializer.serialize(info);
    }
    
    /**
     * 反序列化字节数组为userInfo对象
     */
    public userInfo deserialize(byte[] data) throws TException {
        userInfo info = new userInfo();
        TDeserializer deserializer = new TDeserializer(new TBinaryProtocol.Factory());
        deserializer.deserialize(info, data);
        return info;
    }
}
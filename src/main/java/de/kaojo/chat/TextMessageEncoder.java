/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author jwinter
 */
public class TextMessageEncoder implements
        Encoder.Text<Message> {

    @Override
    public String encode(Message object) throws EncodeException {
        String result = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            result =  mapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public void init(EndpointConfig config) {
        
    }

    @Override
    public void destroy() {
        
    }

}

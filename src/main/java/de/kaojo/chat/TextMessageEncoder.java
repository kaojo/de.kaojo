/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.chat;

import de.kaojo.chat.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(TextMessageEncoder.class.getName()).log(Level.SEVERE, null, ex);
            throw new EncodeException(object, "Could not encode Message", ex);
        }
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }

}

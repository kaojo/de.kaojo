/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.chat;

import de.kaojo.chat.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author jwinter
 */
public class TextMessageDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(String s) throws DecodeException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(s, Message.class);
        } catch (IOException ex) {
            Logger.getLogger(TextMessageDecoder.class.getName()).log(Level.SEVERE, null, ex);
            throw new DecodeException(s, "Encoded message could not be parsed to Message.class");
        }
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

}

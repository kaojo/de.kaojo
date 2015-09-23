package de.kaojo.context.util;

import de.kaojo.ejb.exceptions.ChatManagerException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author jwinter
 */
public class FacesContextHelper {

    public static HttpServletRequest getRequest() {
        HttpServletRequest request
                = (HttpServletRequest) FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from FacesContext");
        }
        return request;
    }

    public static void manageException(ChatManagerException ex) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, ex.getMessage(), null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}

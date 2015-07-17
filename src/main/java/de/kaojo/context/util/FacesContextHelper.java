package de.kaojo.context.util;

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

}

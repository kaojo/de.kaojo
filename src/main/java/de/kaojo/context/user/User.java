/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.context.user;

import de.kaojo.ejb.dto.UserDTO;
import java.util.Date;

/**
 *
 * @author julian.winter
 */
public interface User {

    public Boolean getActive();

    public void setActive(boolean active);

    public Date getBirthday();

    public void setBirthday(Date birthday);

    public String getDisplayName();

    public void setDisplayName(String displayName);

    public String getFirstName();

    public void setFirstName(String firstName);

    public String getLastName();

    public void setLastName(String lastName);

    public String getMail();

    public void setMail(String mail);

    public String getUserId();

    public User build(UserDTO userDTO);

}

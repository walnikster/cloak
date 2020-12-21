package com.airhacks.ping.boundary;

import java.security.Principal;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 *
 * @author airhacks.com
 */
@Path("ping")
public class PingResource {

    @Inject
    @ConfigProperty(name = "message")
    String message;

    @Inject
    JsonWebToken token;

    @Inject
    Principal principal;

    @GET
    @RolesAllowed({ "admin", "normal", "monitoring" })
    public String ping() {
        return this.message + " Jakarta EE with MicroProfile 2+! NoAuth " + token.getRawToken() + token.getGroups();
    }

    @GET
    @Path("normal")
    @RolesAllowed("normal")
    public String normal() {
        return this.message + " Jakarta EE with MicroProfile 2+! NORMAL " + principal.getName() + " "
                + token.getGroups();
    }

    @GET
    @Path("admin")
    @RolesAllowed("admin")
    public String admin() {
        return this.message + " Jakarta EE with MicroProfile 2+ ADMIN! " + principal.getName() + " "
                + token.getGroups();
    }

    @GET
    @Path("monitor")
    @RolesAllowed({ "monitoring", "admin" })
    public String monitor() {
        return this.message + " Jakarta EE with MicroProfile 2+! MONITOR " + principal.getName() + " "
                + token.getGroups();
    }

}

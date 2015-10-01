package bg.jug.website.users;

import bg.jug.website.entities.User;

import javax.inject.Inject;
import javax.mvc.annotation.Controller;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @author Vladimir Tsanev
 */
@Controller
@Path("login")
public class UserController {

    @Inject
    UserManager userManager;

    @Inject
    UserContext userContext;

    @GET
    public String showLogin() {
        return "login.jsp";
    }

    @POST
    public String login(@FormParam("userName") String userName, @FormParam("password") String password) {

        User user = userManager.getUser(userName, password);
        if (user == null) {
            return "login.jsp";
        }
        userContext.setCurrentUser(user);
        return "redirect:/";
    }

}

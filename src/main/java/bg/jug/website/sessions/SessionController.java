package bg.jug.website.sessions;

import bg.jug.website.entities.JugSession;
import bg.jug.website.entities.User;
import bg.jug.website.users.LoggedIn;

import javax.inject.Inject;
import javax.mvc.Models;
import javax.mvc.annotation.Controller;
import javax.mvc.annotation.View;
import javax.mvc.binding.BindingResult;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.stream.Collectors;

/**
 * @author Vladimir Tsanev
 */
@Controller
@Path("sessions")
public class SessionController {
    @Inject
    Models models;

    @Inject
    SessionManager sessionManager;

    @Inject
    @LoggedIn
    User currentUser;

    @Inject
    Errors errors;

    @Inject
    BindingResult bindingResult;

    @GET
    @Path("/all")
    @Controller
    public String allSessions() {
        models.put("submissions", sessionManager.getAllSessions());
        return "sessions.jsp";
    }

    @GET
    @Path("/user")
    @Controller
    @View("sessions.jsp")
    public void userSessions() {
        models.put("submissions", sessionManager.getSessionsForUser(currentUser));
    }

    @GET
    @Path("/new")
    @Controller
    public String newSession() {
        return "newSession.jsp";
    }

    @POST
    @Path("/new")
    @Controller
    @ValidateOnExecution(type = ExecutableType.NONE)
    public Response newSession(@Valid @BeanParam JugSubmission submission) {
        if (bindingResult.isFailed()) {
            errors.setMessages(bindingResult.getAllViolations().stream().map(this::toMessage).collect(Collectors.toList()));
            return Response.status(Response.Status.BAD_REQUEST).entity("newSession.jsp").build();
        }
        sessionManager.submitSession(new JugSession(submission.getTitle(), submission.getDescription(), currentUser));
        return Response.seeOther(URI.create("sessions/user")).build();
    }

    private String toMessage(ConstraintViolation<?> constraintViolation) {
        String path = constraintViolation.getPropertyPath().toString();
        return path.substring(path.lastIndexOf('.') + 1) + ": " + constraintViolation.getMessage();

    }

}

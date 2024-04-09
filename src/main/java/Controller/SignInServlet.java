package Controller;

import Database.AgentDB;
import Database.UserDB;
import Model.Agent;
import Model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.apache.taglibs.standard.functions.Functions;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SignInServlet", value = "/signIn")
public class SignInServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/SignIn.jsp").forward(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String page, message = "Something went wrong";
        try {
            List<User> userList = new UserDB().getAll();
            List<Agent> agentList = new AgentDB().getAll();
            User user = null;
            for(User u: userList){
                if (u.getUsername().equals(request.getParameter("username")))
                    user = u;
            }
            Agent agent = null;
            for(Agent a: agentList){
                if (a.getUsername().equals(request.getParameter("username")))
                    agent = a;
            }
            if (user != null){
                String userHashedPassword = new Sha256Hash(request.getParameter("password"), user.getSalt(), 5000).toBase64();

                if (userHashedPassword.equals(user.getPassword())) {
                    UsernamePasswordToken token = new UsernamePasswordToken(Functions.escapeXml(request.getParameter("username")),
                            Functions.escapeXml(userHashedPassword));
                    Subject currentUser = SecurityUtils.getSubject();
                    token.setRememberMe(true);
                    currentUser.login(token);
                    page = "index.jsp";
                } else {
                    message = "Incorrect username or password OR you have been suspended, please contact one of our agents/admins";
                    page = "Error.jsp";
                }
            }
            else if (agent != null){
                String agentHashedPassword = new Sha256Hash(request.getParameter("password"), agent.getSalt(), 5000).toBase64();

                if (agentHashedPassword.equals(agent.getPassword())) {
                    UsernamePasswordToken token = new UsernamePasswordToken(Functions.escapeXml(request.getParameter("username")),
                            Functions.escapeXml(agentHashedPassword));
                    Subject currentUser = SecurityUtils.getSubject();
                    token.setRememberMe(true);
                    currentUser.login(token);
                    page = "index.jsp";
                } else {
                    message = "Incorrect username or password OR you have been suspended, please contact one of our agents/admins";
                    page = "Error.jsp";
                }
            }
            else {
                message = "Something went wrong";
                page = "Error.jsp";
            }

        } catch (UnknownAccountException e) {
            message = "Unknown Account";
            page = "Error.jsp";
        } catch (IncorrectCredentialsException ice) {
            message = "Incorrect Credentials";
            page = "Error.jsp";
        } catch (LockedAccountException lae) {
            message = "Locked Account";
            page = "Error.jsp";
        } catch (ExcessiveAttemptsException eae) {
            message = "Excessive Attempts";
            page = "Error.jsp";
        } catch (AuthenticationException ae) {
            message = "Authentication Error (Incorrect username or password OR you have been suspended, please contact one of our agents/admins )";
            page = "Error.jsp";
        } catch (UnavailableSecurityManagerException usme) {
            message = "Unavailable Security Manager";
            page = "Error.jsp";
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher(page).forward(request, response);
    }
}
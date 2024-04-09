package Controller;

import Database.AgentDB;
import Database.UserDB;
import Database.VendorDB;
import Model.Agent;
import Model.Helper;
import Model.User;
import Model.Vendor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.apache.taglibs.standard.functions.Functions;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ChangePasswordServlet", value = "/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("ChangePassword.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String page, message = "Something went wrong";
        try {
            String newPassword = Functions.escapeXml(request.getParameter("newPassword"));
            String confirmPassword = Functions.escapeXml(request.getParameter("confirmPassword"));
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser.isAuthenticated() && (currentUser.hasRole("agent") || currentUser.hasRole("admin") || currentUser.hasRole("user"))) {
                if (newPassword.equals(Functions.escapeXml(confirmPassword))){
                    if (newPassword.length() >= 8 && newPassword.length() <= 16){
                        if ((currentUser.hasRole("agent")) || (currentUser.hasRole("admin"))){
                            Agent agent = Helper.GetAgent(currentUser.getPrincipal().toString());
                            AgentDB agentDB = new AgentDB();
                            SecureRandomNumberGenerator generator = new SecureRandomNumberGenerator();
                            String salt = generator.nextBytes().toHex();
                            String hashedPassword = new Sha256Hash(newPassword, salt, 5000).toBase64();
                            Agent a = new Agent(agent.getAgentId(), Functions.escapeXml(agent.getFirstName()), Functions.escapeXml(agent.getLastName()),
                                    Functions.escapeXml(agent.getPhone()), Functions.escapeXml(agent.getFax()), Functions.escapeXml(agent.getEmail()),
                                    Functions.escapeXml(agent.getUsername()), Functions.escapeXml(hashedPassword), agent.getRole(), salt);
                            agentDB.update(a);
                            page = "index.jsp";
                        }
                        else if (currentUser.hasRole("user")){
                            User user = Helper.GetUser(currentUser.getPrincipal().toString());
                            UserDB userDB = new UserDB();
                            SecureRandomNumberGenerator generator = new SecureRandomNumberGenerator();
                            String salt = generator.nextBytes().toHex();
                            String hashedPassword = new Sha256Hash(newPassword, salt, 5000).toBase64();
                            User u = new User(user.getId(), Functions.escapeXml(user.getFirstName()), Functions.escapeXml(user.getLastName()),
                                    Functions.escapeXml(user.getPhone()), Functions.escapeXml(user.getEmail()), Functions.escapeXml(user.getUsername()),
                                    Functions.escapeXml(hashedPassword), user.getActive(), user.getRole(), salt);
                            userDB.update(u);
                            page = "index.jsp";
                        }
                        else {
                            page = "Error.jsp";
                            message = "Passwords does not match";
                        }

                        }
                    else {
                        page = "Error.jsp";
                        message = "Password's length should be between 8 and 16 characters";
                    }
                }
                else{
                    page = "Error.jsp";
                    message = "Passwords does not match";
                }
            }
            else {
                page = "Error.jsp";
                message = "You do not have Permission to access it.";
            }
        } catch (Exception e) {
            page = "Error.jsp";
            message = "Passwords does not match";
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher(page).forward(request, response);
    }
}

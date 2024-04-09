package Controller;

import Database.AgentDB;
import Database.UserDB;
import Database.VendorDB;
import Model.Agent;
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

@WebServlet(name = "AddAgentServlet", value = "/AddAgentServlet")
public class AddAgentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("AddAgent.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String page, message = "Something went wrong";
        try {
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser.isAuthenticated() && (currentUser.hasRole("admin"))) {
                String firstName = Functions.escapeXml(request.getParameter("firstName"));
                String lastName = Functions.escapeXml(request.getParameter("lastName"));
                String phone = Functions.escapeXml(request.getParameter("phone"));
                String fax = Functions.escapeXml(request.getParameter("fax"));
                String email = Functions.escapeXml(request.getParameter("email"));
                String password = Functions.escapeXml(request.getParameter("password"));
                String confirmPassword = Functions.escapeXml(request.getParameter("confirmPassword"));

                if (password.equals(Functions.escapeXml(confirmPassword))){
                    if (password.length() >= 8 && password.length() <= 16){
                        String userName = Functions.escapeXml(firstName) +"."+ Functions.escapeXml(lastName);
                        AgentDB agentDB = new AgentDB();
                        List<Agent> agents = agentDB.getAll();
                        int agentID;
                        if(agents != null)
                            agentID= agents.subList(agents.size() - 1, agents.size()).get(0).getAgentId() + 1;
                        else
                            agentID = 1;
                        SecureRandomNumberGenerator generator = new SecureRandomNumberGenerator();
                        String salt = generator.nextBytes().toHex();
                        String hashedPassword = new Sha256Hash(password, salt, 5000).toBase64();
                        Agent a = new Agent(agentID, Functions.escapeXml(firstName), Functions.escapeXml(lastName), Functions.escapeXml(phone),
                                Functions.escapeXml(fax), Functions.escapeXml(email), Functions.escapeXml(userName),
                                Functions.escapeXml(hashedPassword), 3, salt);
                        agentDB.insert(a);
                        page = "index.jsp";
                        message = userName + " and password " + password ;
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

package Controller;

import Model.Agent;
import Model.Helper;
import Model.Property;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetAgentPropertiesServlet", value = "/GetAgentPropertiesServlet")
public class GetAgentPropertiesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try {
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser.isAuthenticated() && (currentUser.hasRole("agent"))) {
                Agent agent = Helper.GetAgent(currentUser.getPrincipals().toString());
                ArrayList<Property> agentProperties = Helper.GetAgentProperties(agent.getAgentId());
                request.setAttribute("properties", agentProperties);
                request.setAttribute("agent", agent);
                request.setAttribute("message", Helper.message);
                request.getRequestDispatcher(Helper.page).forward(request, response);
            }
            else if (currentUser.isAuthenticated() && (currentUser.hasRole("admin"))) {
                Agent agent = Helper.GetAgent(currentUser.getPrincipals().toString());
                ArrayList<Property> agentProperties = Helper.GetUnarchivedProperties();
                request.setAttribute("properties", agentProperties);
                request.setAttribute("agent", agent);
                request.setAttribute("message", Helper.message);
                request.getRequestDispatcher(Helper.page).forward(request, response);
            }
            else {
                request.setAttribute("message", "You do not have Permission to access it.");
                request.getRequestDispatcher("Error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("message", "Something went wrong");
            request.getRequestDispatcher("Error.jsp").forward(request, response);
        }
    }
}

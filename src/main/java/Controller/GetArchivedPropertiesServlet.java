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

@WebServlet(name = "GetArchivedPropertiesServlet", value = "/GetArchivedPropertiesServlet")
public class GetArchivedPropertiesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try {
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser.isAuthenticated() && (currentUser.hasRole("agent"))) {
                Agent agent = Helper.GetAgent(currentUser.getPrincipal().toString());
                ArrayList<Property> properties = Helper.GetArchivedProperties(agent.getAgentId());
                request.setAttribute("properties", properties);
                request.setAttribute("agent", agent);
                request.setAttribute("message", Helper.message);
                request.getRequestDispatcher(Helper.page).forward(request, response);
            }
            else if (currentUser.isAuthenticated() && (currentUser.hasRole("admin"))) {
                Agent agent = Helper.GetAgent(currentUser.getPrincipal().toString());
                ArrayList<Property> properties = Helper.GetArchivedPropertiesAdmin();
                request.setAttribute("properties", properties);
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

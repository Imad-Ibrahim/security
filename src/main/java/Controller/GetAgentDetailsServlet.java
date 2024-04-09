package Controller;

import Database.AgentDB;
import Model.Agent;
import Model.Helper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.taglibs.standard.functions.Functions;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "GetAgentDetailsServlet", value = "/GetAgentDetailsServlet")
public class GetAgentDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser.isAuthenticated() && (currentUser.hasRole("admin"))) {
                Agent admin = Helper.GetAgent(currentUser.getPrincipal().toString());
                Optional<Agent> agent = new AgentDB().getByID(Integer.parseInt(Functions.escapeXml(request.getParameter("agentID"))));
                request.setAttribute("agent", agent.get());
                request.setAttribute("admin", admin);
                request.getRequestDispatcher("EditAgent.jsp").forward(request, response);
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

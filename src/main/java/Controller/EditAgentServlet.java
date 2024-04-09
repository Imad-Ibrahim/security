package Controller;

import Database.AgentDB;
import Model.Agent;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.taglibs.standard.functions.Functions;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "EditAgentServlet", value = "/EditAgentServlet")
public class EditAgentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("EditAgent.jsp").forward(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser.isAuthenticated() && (currentUser.hasRole("admin"))) {
                Optional<Agent> agent = new AgentDB().getByID(Integer.parseInt(Functions.escapeXml(request.getParameter("agentID"))));
                AgentDB agentDB = new AgentDB();
                String userName = Functions.escapeXml(request.getParameter("firstName")) +"."+ Functions.escapeXml(request.getParameter("lastName"));
                Agent a = new Agent(agent.get().getAgentId(), Functions.escapeXml(request.getParameter("firstName")),
                        Functions.escapeXml(request.getParameter("lastName")), Functions.escapeXml(request.getParameter("phone")),
                        Functions.escapeXml(request.getParameter("fax")), Functions.escapeXml(request.getParameter("email")),
                        userName, agent.get().getPassword(), agent.get().getRole(), agent.get().getSalt());
                agentDB.update(a);
                request.getRequestDispatcher("index.jsp").forward(request, response);
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

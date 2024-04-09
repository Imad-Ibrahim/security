package Controller;

import Database.*;
import Model.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.taglibs.standard.functions.Functions;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "MoreDetailsServlet", value = "/MoreDetailsServlet")
public class MoreDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try {
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser.isAuthenticated() && currentUser.hasRole("admin")){
                request.setAttribute("admin", Helper.GetAgent(currentUser.getPrincipal().toString()));
            }
            Propertynotes propertynotes = new Propertynotes();
            Optional<Property> property = new PropertyDB().getByID(Integer.parseInt(Functions.escapeXml(request.getParameter("propertyID"))));
            Optional<Style> style = new StyleDB().getByID(property.get().getStyleId());
            Optional<Propertytype> propertyType = new PropertytypeDB().getByID(property.get().getTypeId());
            Optional<Garagetype> garageType = new GaragetypeDB().getByID(property.get().getGarageId());
            Optional<Agent> agent = new AgentDB().getByID(property.get().getAgentId());
            Optional<Vendor> vendor = new VendorDB().getByID(property.get().getVendorId());
            if (currentUser.isAuthenticated() && (currentUser.hasRole("user"))) {
                User user = Helper.GetUser(currentUser.getPrincipal().toString());
                List<Propertynotes> propertyNoteList = new PropertynotesDB().getAll();
                if (propertyNoteList != null)
                    propertynotes = Helper.getPropertyNote(propertyNoteList, property.get().getId(), user);
            }
            List<String> imagesName = Helper.getFileNames(getServletContext(), property.get());
            request.setAttribute("property", property.get());
            request.setAttribute("style", style.get());
            request.setAttribute("propertyType", propertyType.get());
            request.setAttribute("garageType", garageType.get());
            request.setAttribute("agent", agent.get());
            request.setAttribute("vendor", vendor.get());
            request.setAttribute("propertynote", propertynotes);
            request.setAttribute("imagesName", imagesName);
            request.getRequestDispatcher("MoreDetails.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("message", "Something went wrong");
            request.getRequestDispatcher("Error.jsp").forward(request, response);
        }
    }
}

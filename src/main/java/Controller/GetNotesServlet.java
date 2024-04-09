package Controller;

import Database.PropertyDB;
import Database.PropertynotesDB;
import Model.Helper;
import Model.Property;
import Model.Propertynotes;
import Model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.taglibs.standard.functions.Functions;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "GetNotesServlet", value = "/GetNotesServlet")
public class GetNotesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try {
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser.isAuthenticated() && (currentUser.hasRole("user"))) {
                Propertynotes propertynote;
                Optional<Property> property;
                User user = Helper.GetUser(currentUser.getPrincipal().toString());
                property = new PropertyDB().getByID(Integer.parseInt(Functions.escapeXml(request.getParameter("propertyID"))));
                List<Propertynotes> propertyNoteList = new PropertynotesDB().getAll();
                if (propertyNoteList != null)
                    propertynote = Helper.getPropertyNote(propertyNoteList, Integer.parseInt(Functions.escapeXml(request.getParameter("propertyID"))), user);
                else
                    propertynote = new Propertynotes();
                request.setAttribute("propertyID", property.get().getId());
                request.setAttribute("propertynote", propertynote);
                request.getRequestDispatcher("Note.jsp").forward(request, response);
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

package Controller;

import Database.PropertyDB;
import Model.Helper;
import Model.Property;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.taglibs.standard.functions.Functions;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "UnarchivePropertiesServlet", value = "/UnarchivePropertiesServlet")
public class UnarchivePropertiesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try {
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser.isAuthenticated() && (currentUser.hasRole("agent") || currentUser.hasRole("admin"))) {
                PropertyDB propertyDB = new PropertyDB();
                Optional<Property> property = propertyDB.getByID(Integer.parseInt(Functions.escapeXml(request.getParameter("propertyID"))));
                property.get().setArchived(0);
                propertyDB.update(property.get());
                request.getRequestDispatcher("GetArchivedPropertiesServlet").forward(request, response);
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

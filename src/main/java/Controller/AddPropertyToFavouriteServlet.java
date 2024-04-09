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

@WebServlet(name = "AddPropertyToFavouriteServlet", value = "/AddPropertyToFavouriteServlet")
public class AddPropertyToFavouriteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String page, message = "Something went wrong";
        try {
            Subject currentUser = SecurityUtils.getSubject();
            if (!currentUser.isAuthenticated() || (currentUser.hasRole("user"))) {
                PropertyDB propertyDB = new PropertyDB();
                Optional<Property> property = propertyDB.getByID(Integer.parseInt(Functions.escapeXml(request.getParameter("propertyID"))));
                Cookie c = new Cookie(String.valueOf(property.get().getId()), String.valueOf(property.get().getId()));
                c.setMaxAge(365 * 24 * 60 * 60);
                response.addCookie(c);
                page = "GetPropertiesServlet";
            }
            else {
                page = "Error.jsp";
                message = "You do not have Permission to access it.";
            }
        } catch (NumberFormatException e) {
            page = "Error.jsp";
            message = "Something went wrong";
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher(page).forward(request, response);
    }
}

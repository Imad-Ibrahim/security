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

@WebServlet(name = "DeletePropertyImageServlet", value = "/DeletePropertyImageServlet")
public class DeletePropertyImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser.isAuthenticated() && (currentUser.hasRole("agent") || currentUser.hasRole("admin"))) {
                Optional<Property> property = new PropertyDB().getByID(Integer.parseInt(Functions.escapeXml(request.getParameter("propertyID"))));
                String imageName = Functions.escapeXml(request.getParameter("imageName"));
                Helper.deleteImage(Functions.escapeXml(imageName), property.get(), getServletContext());
                response.sendRedirect(request.getContextPath() + "/Fill_InPropertyDetailsFormServlet?propertyID=" + property.get().getId());
            }
            else {
                request.setAttribute("message", "You do not have Permission to access it.");
                request.getRequestDispatcher("Error.jsp").forward(request, response);
            }
        }  catch (Exception e) {
            request.setAttribute("message", "Something went wrong");
            request.getRequestDispatcher("Error.jsp").forward(request, response);
        }
    }
}

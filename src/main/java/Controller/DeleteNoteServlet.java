package Controller;

import Database.PropertyDB;
import Database.PropertynotesDB;
import Model.Property;
import Model.Propertynotes;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.taglibs.standard.functions.Functions;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "DeleteNoteServlet", value = "/DeleteNoteServlet")
public class DeleteNoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String page, message = "Something went wrong";
        try{
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser.isAuthenticated() && (currentUser.hasRole("user"))) {
                PropertynotesDB propertynotesDB = new PropertynotesDB();
                Optional<Propertynotes> propertynotes = propertynotesDB.getByID(Integer.parseInt(Functions.escapeXml(request.getParameter("propertyNoteID"))));
                propertynotesDB.delete(propertynotes.get());
                page = "GetPropertiesServlet";
            }
            else {
                page = "Error.jsp";
                message = "You do not have Permission to access it.";
            }
        }  catch (Exception e) {
            message = "Something went wrong";
            page = "Error.jsp";
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher(page).forward(request, response);
    }
}

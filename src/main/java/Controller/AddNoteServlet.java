package Controller;

import Database.PropertynotesDB;
import Model.Helper;
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

@WebServlet(name = "AddNoteServlet", value = "/AddNoteServlet")
public class AddNoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Note.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page, message = "Something went wrong";
        try{
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser.isAuthenticated() && (currentUser.hasRole("user"))) {
                String note = Functions.escapeXml(request.getParameter("note"));
                String propertyID = Functions.escapeXml(request.getParameter("propertyID"));
                PropertynotesDB propertynotesDB = new PropertynotesDB();
                User user = Helper.GetUser(currentUser.getPrincipals().toString());
                List<Propertynotes> propertyNoteList = propertynotesDB.getAll();
                if (propertyNoteList != null){
                    Propertynotes propertynotes = Helper.getPropertyNote(propertyNoteList, Integer.parseInt(Functions.escapeXml(propertyID)), user);
                    if (propertynotes != null){
                        propertynotes.setNote(Functions.escapeXml(note));
                        propertynotesDB.update(propertynotes);
                    }
                    else {
                        int newID = propertyNoteList.subList(propertyNoteList.size() - 1, propertyNoteList.size()).get(0).getId() + 1;
                        propertynotesDB.insert(new Propertynotes(newID, Integer.parseInt(Functions.escapeXml(propertyID)), user.getId(), Functions.escapeXml(note)));
                    }
                }
                else
                    propertynotesDB.insert(new Propertynotes(1, Integer.parseInt(Functions.escapeXml(propertyID)), user.getId(), Functions.escapeXml(note)));
                page = "index.jsp";
            }
            else {
                message = "You do not have Permission to access it.";
                page = "Error.jsp";
            }
        } catch (Exception e) {
            message = "Something went wrong";
            page = "Error.jsp";
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher(page).forward(request, response);
    }
}

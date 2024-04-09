package Controller;


import Database.UserDB;
import Model.Helper;
import Model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.taglibs.standard.functions.Functions;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EditProfileServlet", value = "/EditProfileServlet")
public class EditProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("EditProfile.jsp").forward(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page, message = "Something went wrong";
        try{
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser.isAuthenticated() && (currentUser.hasRole("user"))) {
                String userName = Functions.escapeXml(request.getParameter("firstName")) +"."+ Functions.escapeXml(request.getParameter("lastName"));
                User user = Helper.GetUser(currentUser.getPrincipal().toString());
                UserDB userDB = new UserDB();
                User u = new User(user.getId(), Functions.escapeXml(request.getParameter("firstName")), Functions.escapeXml(request.getParameter("lastName")),
                        Functions.escapeXml(request.getParameter("phone")), Functions.escapeXml(request.getParameter("email")),
                        Functions.escapeXml(userName), Functions.escapeXml(user.getPassword()), user.getActive(), user.getRole(),
                        user.getSalt());
                userDB.update(u);
                page = "index.jsp";
            }
            else {
                page = "Error.jsp";
                message = "You do not have Permission to access it.";
            }
        }  catch (Exception e) {
            page = "Error.jsp";
            message = "Something went wrong";
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher(page).forward(request, response);
    }
}

package Controller;


import Model.*;
import lombok.SneakyThrows;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Fill_InUserProfileFormServlet", value = "/Fill_InUserProfileFormServlet")
public class Fill_InUserProfileFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String page, message = "Something went wrong";
        try {
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser.isAuthenticated() && (currentUser.hasRole("user"))) {
                User user = Helper.GetUser(currentUser.getPrincipal().toString());
                request.setAttribute("user", user);
                page = "EditProfile.jsp";
            }
            else {
                message = "You do not have Permission to access it.";
                page = "Error.jsp";
            }
        } catch (Exception e) {
            page = "Error.jsp";
            message = "Something went wrong";
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher(page).forward(request, response);
    }
}
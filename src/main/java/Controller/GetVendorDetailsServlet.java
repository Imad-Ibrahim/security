package Controller;

import Database.VendorDB;
import Model.Agent;
import Model.Helper;
import Model.Vendor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.taglibs.standard.functions.Functions;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "GetVendorDetailsServlet", value = "/GetVendorDetailsServlet")
public class GetVendorDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser.isAuthenticated() && (currentUser.hasRole("agent") || currentUser.hasRole("admin"))) {
                Agent agent = Helper.GetAgent(currentUser.getPrincipal().toString());
                Optional<Vendor> vendor = new VendorDB().getByID(Integer.parseInt(Functions.escapeXml(request.getParameter("vendorID"))));
                request.setAttribute("vendor", vendor.get());
                request.setAttribute("agent", agent);
                request.getRequestDispatcher("EditVendor.jsp").forward(request, response);
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

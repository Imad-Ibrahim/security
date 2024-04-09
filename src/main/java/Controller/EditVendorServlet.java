package Controller;

import Database.VendorDB;
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

@WebServlet(name = "EditVendorServlet", value = "/EditVendorServlet")
public class EditVendorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*try {
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser.isAuthenticated() && (currentUser.hasRole("agent") || currentUser.hasRole("admin"))) {
                Optional<Vendor> vendor = new VendorDB().getByID(Integer.parseInt(Functions.escapeXml(request.getParameter("vendorID"))));
                VendorDB vendorDB = new VendorDB();
                Vendor v = new Vendor(vendor.get().getVendorId(), Functions.escapeXml(request.getParameter("firstName")),
                        Functions.escapeXml(request.getParameter("lastName")), Functions.escapeXml(request.getParameter("street")),
                        Functions.escapeXml(request.getParameter("city")), Functions.escapeXml(request.getParameter("phone")),
                        Functions.escapeXml(request.getParameter("email")));
                vendorDB.update(v);
                request.getRequestDispatcher("GetAllVendorsServlet").forward(request, response);
            }
            else {
                request.setAttribute("message", "You do not have Permission to access it.");
                request.getRequestDispatcher("Error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("message", "Something went wrong");
            request.getRequestDispatcher("Error.jsp").forward(request, response);
        }*/
        request.getRequestDispatcher("EditVendor.jsp").forward(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser.isAuthenticated() && (currentUser.hasRole("agent") || currentUser.hasRole("admin"))) {
                Optional<Vendor> vendor = new VendorDB().getByID(Integer.parseInt(Functions.escapeXml(request.getParameter("vendorID"))));
                VendorDB vendorDB = new VendorDB();
                Vendor v = new Vendor(vendor.get().getVendorId(), Functions.escapeXml(request.getParameter("firstName")),
                        Functions.escapeXml(request.getParameter("lastName")), Functions.escapeXml(request.getParameter("street")),
                        Functions.escapeXml(request.getParameter("city")), Functions.escapeXml(request.getParameter("phone")),
                        Functions.escapeXml(request.getParameter("email")));
                vendorDB.update(v);
                request.getRequestDispatcher("index.jsp").forward(request, response);
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

package Controller;

import Database.UserDB;
import Database.VendorDB;
import Model.User;
import Model.Vendor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.apache.taglibs.standard.functions.Functions;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SignUpServlet", value = "/SignUpServlet")
public class SignUpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("SignUp.jsp").forward(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String page, message = "Something went wrong";
        try {
            String firstName = Functions.escapeXml(request.getParameter("firstName"));
            String lastName = Functions.escapeXml(request.getParameter("lastName"));
            String phone = Functions.escapeXml(request.getParameter("phone"));
            String email = Functions.escapeXml(request.getParameter("email"));
            String userName = Functions.escapeXml(firstName) +"."+ Functions.escapeXml(lastName);
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser.isAuthenticated() && (currentUser.hasRole("agent") || currentUser.hasRole("admin"))) {
                String street = Functions.escapeXml(request.getParameter("street"));
                String city = Functions.escapeXml(request.getParameter("city"));
                VendorDB vendorDB = new VendorDB();
                List<Vendor> vendors = vendorDB.getAll();
                int vendorID;
                if (vendors != null)
                    vendorID = vendors.subList(vendors.size() - 1, vendors.size()).get(0).getVendorId() + 1;
                else
                    vendorID = 1;
                Vendor vendor = new Vendor(vendorID, Functions.escapeXml(firstName), Functions.escapeXml(lastName), Functions.escapeXml(street),
                        Functions.escapeXml(city), Functions.escapeXml(phone), Functions.escapeXml(email));
                vendorDB.insert(vendor);
                page = "index.jsp";
            }
            else {
                String password = Functions.escapeXml(request.getParameter("password"));
                String confirmPassword = Functions.escapeXml(request.getParameter("confirmPassword"));

                if (password.equals(Functions.escapeXml(confirmPassword))){
                    if (password.length() >= 8 && password.length() <= 16){
                        UserDB userDB = new UserDB();
                        List<User> users = userDB.getAll();
                        int userID;
                        if(users != null)
                            userID= users.subList(users.size() - 1, users.size()).get(0).getId() + 1;
                        else
                            userID = 1;
                        SecureRandomNumberGenerator generator = new SecureRandomNumberGenerator();
                        String salt = generator.nextBytes().toHex();
                        String hashedPassword = new Sha256Hash(password, salt, 5000).toBase64();
                        User user = new User(userID, Functions.escapeXml(firstName), Functions.escapeXml(lastName), Functions.escapeXml(phone),
                                Functions.escapeXml(email), Functions.escapeXml(userName), Functions.escapeXml(hashedPassword), 1, 1, salt);
                        userDB.insert(user);
                        page = "index.jsp";
                        message = userName;
                    }
                    else {
                        page = "Error.jsp";
                        message = "Password's length should be between 8 and 16 characters";
                    }
                }
                else{
                    page = "Error.jsp";
                    message = "Passwords does not match";
                }
            }
        } catch (Exception e) {
            page = "Error.jsp";
            message = "Passwords does not match";
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher(page).forward(request, response);
    }
}
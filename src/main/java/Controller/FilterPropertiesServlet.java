package Controller;

import Database.PropertyDB;
import Model.Agent;
import Model.Helper;
import Model.Property;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.taglibs.standard.functions.Functions;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "FilterPropertiesServlet", value = "/FilterPropertiesServlet")
public class FilterPropertiesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Properties.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page, message = "Something went wrong";
        try {
            String city = Functions.escapeXml(request.getParameter("city").replaceAll("\\s", ""));
            String priceFrom = Functions.escapeXml(request.getParameter("priceFrom"));
            String priceTo = Functions.escapeXml(request.getParameter("priceTo"));
            String numOfBedRooms = Functions.escapeXml(request.getParameter("numberOfBedrooms"));
            String numOfBathRooms = Functions.escapeXml(request.getParameter("numberOfBathrooms"));
            PropertyDB propertyDB = new PropertyDB();
            List<Property> propertyList = propertyDB.getAll();
            if (!Functions.escapeXml(city).equals("") && !Functions.escapeXml(priceFrom).equals("")
                    && !Functions.escapeXml(priceTo).equals("") && !Functions.escapeXml(numOfBedRooms).equals("") && !Functions.escapeXml(numOfBathRooms).equals("")) {
                ArrayList<Property> properties = Filter(request, propertyList, Functions.escapeXml(city), Double.parseDouble(Functions.escapeXml(priceFrom)),
                        Double.parseDouble(Functions.escapeXml(priceTo)), Integer.parseInt(Functions.escapeXml(numOfBedRooms)), Integer.parseInt(Functions.escapeXml(numOfBathRooms)));
                Subject currentUser = SecurityUtils.getSubject();
                if (!currentUser.isAuthenticated() && (currentUser.hasRole("user"))) {
                    Cookie c = new Cookie(city, priceTo);
                    c.setMaxAge(365 * 24 * 60 * 60);
                    response.addCookie(c);
                }
                if (currentUser.isAuthenticated() && (currentUser.hasRole("agent") || currentUser.hasRole("admin"))) {
                    Agent agent = Helper.GetAgent(currentUser.getPrincipal().toString());
                    request.setAttribute("agent", agent);
                }
                request.setAttribute("properties", properties);
                page = "Properties.jsp";
            }
            else {
                page = "Error.jsp";
                message = "Something went wrong, please fill in all fields";
            }
        } catch (Exception e) {
            page = "Error.jsp";
            message = "Something went wrong";
        }
        request.setAttribute("message", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
    public ArrayList<Property> Filter(HttpServletRequest request, List<Property> propertyList, String city, Double priceFrom, Double priceTo, Integer numOfBedRooms, Integer numOfBathRooms){
        ArrayList<Property> properties = new ArrayList<>();
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated() && (currentUser.hasRole("agent"))) {
            Agent agent = Helper.GetAgent(currentUser.getPrincipal().toString());
            for(Property property : propertyList){
                if (property.getCity().equals(city) &&
                        (property.getPrice() >= priceFrom && property.getPrice() <= priceTo) &&
                        property.getBedrooms().equals(numOfBedRooms) &&
                        property.getBathrooms().equals(numOfBathRooms) &&
                        property.getArchived().equals(0) &&
                        property.getAgentId().equals(agent.getAgentId())){
                    properties.add(property);
                }
            }
        }
        else {
            for(Property property : propertyList){
                if (property.getCity().equals(city) && (property.getPrice() >= priceFrom && property.getPrice() <= priceTo) && property.getBedrooms().equals(numOfBedRooms)
                        && property.getBathrooms().equals(numOfBathRooms) && property.getArchived().equals(0)){
                    properties.add(property);
                }
            }
        }
        return properties;
    }
}

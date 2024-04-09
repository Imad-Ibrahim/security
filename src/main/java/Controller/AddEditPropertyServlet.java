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
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "AddEditPropertyServlet", value = "/AddEditPropertyServlet")
@MultipartConfig
public class AddEditPropertyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("AddEditProperty.jsp").forward(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser.isAuthenticated() && (currentUser.hasRole("agent") || currentUser.hasRole("admin"))) {
                String street = Functions.escapeXml(request.getParameter("street"));
                String city = Functions.escapeXml(request.getParameter("city").replaceAll("\\s", ""));
                String listingNum = Functions.escapeXml(request.getParameter("listingNum"));
                String styleId = Functions.escapeXml(request.getParameter("styleId"));
                String typeId = Functions.escapeXml(request.getParameter("typeId"));
                String bedrooms = Functions.escapeXml(request.getParameter("bedrooms"));
                String bathrooms = Functions.escapeXml(request.getParameter("bathrooms"));
                String squarefeet = Functions.escapeXml(request.getParameter("squarefeet"));
                String berRating = Functions.escapeXml(request.getParameter("berRating"));
                String description = Functions.escapeXml(request.getParameter("description"));
                String lotsize = Functions.escapeXml(request.getParameter("lotsize"));
                String garagesize = Functions.escapeXml(request.getParameter("garagesize"));
                String garageId = Functions.escapeXml(request.getParameter("garageId"));
                String vendorID = Functions.escapeXml(request.getParameter("vendorID"));
                String price = Functions.escapeXml(request.getParameter("price"));
                //List<Part> files = request.getParts().stream().filter(part -> part.getName().equals("photo")).collect(Collectors.toList());
                String btnValue = Functions.escapeXml(request.getParameter("btnValue"));
                String propertyID = Functions.escapeXml(request.getParameter("propertyID"));
                PropertyDB propertyDB = new PropertyDB();
                Date sqlDate = Date.valueOf(LocalDate.now());
                Agent agent = Helper.GetAgent(currentUser.getPrincipals().toString());
                if (btnValue.equals("Edit") && propertyID != null){
                    Optional<Property> property = propertyDB.getByID(Integer.parseInt(Functions.escapeXml(propertyID)));
                    property.get().setStreet(Functions.escapeXml(street));
                    property.get().setCity(Functions.escapeXml(city));
                    property.get().setListingNum((Integer.parseInt(Functions.escapeXml(listingNum))));
                    property.get().setStyleId(Integer.parseInt(Functions.escapeXml(styleId)));
                    property.get().setTypeId(Integer.parseInt(Functions.escapeXml(typeId)));
                    property.get().setBedrooms(Integer.parseInt(Functions.escapeXml(bedrooms)));
                    property.get().setBathrooms(Integer.parseInt(Functions.escapeXml(bathrooms)));
                    property.get().setSquarefeet(Integer.parseInt(Functions.escapeXml(squarefeet)));
                    property.get().setBerRating(Functions.escapeXml(berRating));
                    property.get().setDescription(Functions.escapeXml(description));
                    property.get().setLotsize(Functions.escapeXml(lotsize));
                    property.get().setGaragesize(Integer.parseInt(Functions.escapeXml(garagesize)));
                    property.get().setGarageId(Integer.parseInt(Functions.escapeXml(garageId)));
                    property.get().setPrice(Double.parseDouble(Functions.escapeXml(price)));
                    property.get().setVendorId(Integer.parseInt(Functions.escapeXml(vendorID)));
                    propertyDB.update(property.get());
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                else if (btnValue.equals("Add")){
                    List<Property> properties = propertyDB.getAll();
                    Comparator<Property> ascendingByDate = Comparator.comparing(Property::getId);
                    properties.sort(ascendingByDate);
                    Collections.reverse(properties);
                    //String photoName = files.get(0).getName().replaceAll(files.get(0).getName(), listingNum+".jpg");
                    Property newProperty = new Property(
                            properties.get(0).getId()+1,
                            Functions.escapeXml(street),
                            Functions.escapeXml(city),
                            Integer.parseInt(Functions.escapeXml(listingNum)),
                            Integer.parseInt(Functions.escapeXml(styleId)),
                            Integer.parseInt(Functions.escapeXml(typeId)),
                            Integer.parseInt(Functions.escapeXml(bedrooms)),
                            Integer.parseInt(Functions.escapeXml(bathrooms)),
                            Integer.parseInt(Functions.escapeXml(squarefeet)),
                            Functions.escapeXml(berRating),
                            Functions.escapeXml(description),
                            Functions.escapeXml(lotsize),
                            Integer.parseInt(Functions.escapeXml(garagesize)),
                            Integer.parseInt(Functions.escapeXml(garageId)),
                            agent.getAgentId(),
                            Functions.escapeXml("photoName"),
                            Double.parseDouble(Functions.escapeXml(price)),
                            sqlDate,
                            0,
                            Integer.parseInt(Functions.escapeXml(vendorID)));
                    propertyDB.insert(newProperty);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                else {
                    request.setAttribute("message", "Something went wrong");
                    request.getRequestDispatcher("Error.jsp").forward(request, response);
                }
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
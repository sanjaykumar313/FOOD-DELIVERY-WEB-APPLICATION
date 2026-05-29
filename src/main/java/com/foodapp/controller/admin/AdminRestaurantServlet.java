package com.foodapp.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.foodapp.dao.RestaurantDAO;
import com.foodapp.model.Restaurant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/restaurants")
public class AdminRestaurantServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Max file size: 2MB
    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024;

    // Upload folder relative to webapp
    private static final String UPLOAD_DIR = "images/restaurants";

    // ─── GET: List + Edit form ────────────────────────
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        RestaurantDAO restaurantDAO = new RestaurantDAO();

        if ("edit".equals(action)) {

            // Load restaurant for edit form
            int restaurantId = Integer.parseInt(
                    request.getParameter("id"));

            Restaurant restaurant =
                    restaurantDAO.getRestaurantById(restaurantId);

            request.setAttribute("restaurant", restaurant);

            request.getRequestDispatcher(
                    "/admin/edit-restaurant.jsp")
                    .forward(request, response);

        } else if ("delete".equals(action)) {

            // Delete restaurant
            int restaurantId = Integer.parseInt(
                    request.getParameter("id"));

            restaurantDAO.deleteRestaurant(restaurantId);

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/restaurants?success=deleted");

        } else {

            // List all restaurants
            ArrayList<Restaurant> restaurantList =
                    restaurantDAO.getAllRestaurantsForAdmin();

            request.setAttribute("restaurantList", restaurantList);

            request.getRequestDispatcher(
                    "/admin/restaurants.jsp")
                    .forward(request, response);
        }
    }

    // ─── POST: Add or Update ──────────────────────────
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Determine upload path on server
        String uploadPath = getServletContext()
                .getRealPath("") + File.separator + UPLOAD_DIR;

        // Create upload directory if not exists
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Setup FileUpload
        DiskFileItemFactory factory =
                new DiskFileItemFactory();

        ServletFileUpload upload =
                new ServletFileUpload(factory);

        upload.setFileSizeMax(MAX_FILE_SIZE);

        // Form field values
        String action       = null;
        String restaurantId = null;
        String name         = null;
        String cuisineType  = null;
        String address      = null;
        String rating       = null;
        String deliveryTime = null;
        String isActive     = null;
        String imagePath    = null;
        String existingImagePath = null;

        try {

            // Parse multipart request
            List<FileItem> items =
                    upload.parseRequest(request);

            for (FileItem item : items) {

                if (item.isFormField()) {

                    // Text fields
                    String fieldName = item.getFieldName();
                    String fieldValue =
                            item.getString("UTF-8");

                    switch (fieldName) {
                        case "action":
                            action = fieldValue; break;
                        case "restaurantId":
                            restaurantId = fieldValue; break;
                        case "name":
                            name = fieldValue; break;
                        case "cuisineType":
                            cuisineType = fieldValue; break;
                        case "address":
                            address = fieldValue; break;
                        case "rating":
                            rating = fieldValue; break;
                        case "deliveryTime":
                            deliveryTime = fieldValue; break;
                        case "isActive":
                            isActive = fieldValue; break;
                        case "existingImagePath":
                            existingImagePath = fieldValue; break;
                    }

                } else {

                    // File field
                    String fileName = item.getName();

                    if (fileName != null &&
                            !fileName.trim().isEmpty()) {

                        // Validate file type
                        String lower = fileName.toLowerCase();

                        if (lower.endsWith(".jpg") ||
                            lower.endsWith(".jpeg") ||
                            lower.endsWith(".png") ||
                            lower.endsWith(".webp")) {

                            // Unique filename
                            String uniqueName =
                                    System.currentTimeMillis()
                                    + "_" + fileName;

                            File file = new File(
                                    uploadPath + File.separator
                                    + uniqueName);

                            item.write(file);

                            imagePath = UPLOAD_DIR
                                    + "/" + uniqueName;

                        } else {

                            request.setAttribute(
                                    "errorMessage",
                                    "Only JPG, PNG, WEBP images allowed!");
                        }
                    }
                }
            }

            // Use existing image if none uploaded
            if (imagePath == null || imagePath.isEmpty()) {
                imagePath = existingImagePath;
            }

            // Fallback default image
            if (imagePath == null || imagePath.isEmpty()) {
                imagePath = "images/restaurants/default.jpg";
            }

            RestaurantDAO restaurantDAO = new RestaurantDAO();

            if ("add".equals(action)) {

                // ── Add new restaurant ────────────────
                Restaurant restaurant = new Restaurant();

                restaurant.setName(name);
                restaurant.setCuisineType(cuisineType);
                restaurant.setAddress(address);
                restaurant.setRating(
                        Double.parseDouble(rating));
                restaurant.setDeliveryTime(deliveryTime);
                restaurant.setImagePath(imagePath);
                restaurant.setActive(
                        "true".equals(isActive));

                restaurantDAO.addRestaurant(restaurant);

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin/restaurants?success=added");

            } else if ("update".equals(action)) {

                // ── Update existing restaurant ────────
                Restaurant restaurant = new Restaurant();

                restaurant.setRestaurantId(
                        Integer.parseInt(restaurantId));
                restaurant.setName(name);
                restaurant.setCuisineType(cuisineType);
                restaurant.setAddress(address);
                restaurant.setRating(
                        Double.parseDouble(rating));
                restaurant.setDeliveryTime(deliveryTime);
                restaurant.setImagePath(imagePath);
                restaurant.setActive(
                        "true".equals(isActive));

                restaurantDAO.updateRestaurant(restaurant);

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin/restaurants?success=updated");
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/restaurants?error=upload_failed");
        }
    }
}

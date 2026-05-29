package com.foodapp.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.foodapp.dao.MenuDAO;
import com.foodapp.dao.RestaurantDAO;
import com.foodapp.model.MenuItem;
import com.foodapp.model.Restaurant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/menu-items")
public class AdminMenuServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Max file size: 2MB
    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024;

    // Upload folder relative to webapp
    private static final String UPLOAD_DIR = "images/menu";

    // ─── GET: List + Edit form ────────────────────────
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        MenuDAO menuDAO = new MenuDAO();
        RestaurantDAO restaurantDAO = new RestaurantDAO();

        if ("edit".equals(action)) {

            // Load item for edit form
            int itemId = Integer.parseInt(
                    request.getParameter("id"));

            MenuItem item = menuDAO.getMenuItemById(itemId);

            // Load restaurant list for dropdown
            ArrayList<Restaurant> restaurantList =
                    restaurantDAO.getAllRestaurantsForAdmin();

            request.setAttribute("menuItem", item);
            request.setAttribute("restaurantList", restaurantList);

            request.getRequestDispatcher(
                    "/admin/edit-menu-item.jsp")
                    .forward(request, response);

        } else if ("delete".equals(action)) {

            // Delete menu item
            int itemId = Integer.parseInt(
                    request.getParameter("id"));

            menuDAO.deleteMenuItem(itemId);

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/menu-items?success=deleted");

        } else {

            // List all menu items + restaurant list
            ArrayList<MenuItem> menuList =
                    menuDAO.getAllMenuItemsForAdmin();

            ArrayList<Restaurant> restaurantList =
                    restaurantDAO.getAllRestaurantsForAdmin();

            request.setAttribute("menuList", menuList);
            request.setAttribute("restaurantList", restaurantList);

            request.getRequestDispatcher(
                    "/admin/menu-items.jsp")
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
        String action         = null;
        String itemId         = null;
        String restaurantId   = null;
        String itemName       = null;
        String description    = null;
        String price          = null;
        String isAvailable    = null;
        String imagePath      = null;
        String existingImagePath = null;

        try {

            // Parse multipart request
            List<FileItem> items =
                    upload.parseRequest(request);

            for (FileItem item : items) {

                if (item.isFormField()) {

                    String fieldName = item.getFieldName();
                    String fieldValue =
                            item.getString("UTF-8");

                    switch (fieldName) {
                        case "action":
                            action = fieldValue; break;
                        case "itemId":
                            itemId = fieldValue; break;
                        case "restaurantId":
                            restaurantId = fieldValue; break;
                        case "itemName":
                            itemName = fieldValue; break;
                        case "description":
                            description = fieldValue; break;
                        case "price":
                            price = fieldValue; break;
                        case "isAvailable":
                            isAvailable = fieldValue; break;
                        case "existingImagePath":
                            existingImagePath = fieldValue; break;
                    }

                } else {

                    // File field
                    String fileName = item.getName();

                    if (fileName != null &&
                            !fileName.trim().isEmpty()) {

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
                imagePath = "images/menu/default.jpg";
            }

            MenuDAO menuDAO = new MenuDAO();

            if ("add".equals(action)) {

                // ── Add new menu item ─────────────────
                MenuItem menuItem = new MenuItem();

                menuItem.setRestaurantId(
                        Integer.parseInt(restaurantId));
                menuItem.setItemName(itemName);
                menuItem.setDescription(description);
                menuItem.setPrice(
                        Double.parseDouble(price));
                menuItem.setImagePath(imagePath);
                menuItem.setAvailable(
                        "true".equals(isAvailable));

                menuDAO.addMenuItem(menuItem);

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin/menu-items?success=added");

            } else if ("update".equals(action)) {

                // ── Update existing menu item ─────────
                MenuItem menuItem = new MenuItem();

                menuItem.setItemId(
                        Integer.parseInt(itemId));
                menuItem.setRestaurantId(
                        Integer.parseInt(restaurantId));
                menuItem.setItemName(itemName);
                menuItem.setDescription(description);
                menuItem.setPrice(
                        Double.parseDouble(price));
                menuItem.setImagePath(imagePath);
                menuItem.setAvailable(
                        "true".equals(isAvailable));

                menuDAO.updateMenuItem(menuItem);

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin/menu-items?success=updated");
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/menu-items?error=upload_failed");
        }
    }
}

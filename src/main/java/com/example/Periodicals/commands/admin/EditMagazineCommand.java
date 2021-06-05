package com.example.Periodicals.commands.admin;

import com.example.Periodicals.Controller.dto.MagazineCreationInput;
import com.example.Periodicals.commands.Command;
import com.example.Periodicals.model.entity.Category;
import com.example.Periodicals.model.service.CategoryService;
import com.example.Periodicals.model.service.MagazineService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

public class EditMagazineCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = "/app/get_Catalog";
        MagazineCreationInput input = buildMagInput(request);
        MagazineService magazineService = new MagazineService();
        boolean status = magazineService.updateMagazine(input);
        if (status) {
            List<Category> list = new CategoryService().findAll();
            request.setAttribute("status", status);
            request.setAttribute("categories", list);
            page = "redirect:/get_mag_info?id=" + input.getId();
        }

        return page;
    }

    private MagazineCreationInput buildMagInput(HttpServletRequest request) {
        return new MagazineCreationInput.Builder()
                .setName(request.getParameter("name"))
                .setPublisher(request.getParameter("publisher"))
                .setDescription(request.getParameter("description"))
                .setCategory(setCategoryId(request))
                .setId(Long.parseLong(request.getParameter("magazine_id")))
                .setPrice(request.getParameter("price")).build();
    }

    private Category setCategoryId(HttpServletRequest request) {
        Category category = new Category();
        category.setId(Integer.parseInt(request.getParameter("category_id")));
        return category;
    }
}

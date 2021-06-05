package com.example.Periodicals.commands.admin;

import com.example.Periodicals.commands.Command;
import com.example.Periodicals.model.entity.Category;
import com.example.Periodicals.model.entity.Magazine;
import com.example.Periodicals.model.service.CategoryService;
import com.example.Periodicals.model.service.MagazineService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class GetMagazineEditPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        Long id= Long.valueOf(request.getParameter("id"));
        MagazineService magazineService=new MagazineService();
        Optional<Magazine> magazine=magazineService.find(id);
        CategoryService categoryService=new CategoryService();
        List<Category> categories=categoryService.findAll();
        if(magazine.isPresent()){
            request.setAttribute("magazine",magazine.get());
            request.setAttribute("categories",categories);
        }
        return "/WEB-INF/jsp/edit_page.jsp";
    }
}

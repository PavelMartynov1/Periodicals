package com.example.Periodicals.commands.user;

import com.example.Periodicals.commands.Command;
import com.example.Periodicals.model.entity.Magazine;
import com.example.Periodicals.model.entity.MagazineSearchParameters;
import com.example.Periodicals.model.service.CategoryService;
import com.example.Periodicals.model.service.MagazineService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.logging.Logger;

public class GetCatalogPageCommand implements Command {
    private static Logger LOGGER = Logger.getLogger(GetCatalogPageCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request) {
        String page="/WEB-INF/jsp/catalog.jsp";
     //   if (request.getParameter("filter") != null) {
            filter(request);
  //          return page;
  //      }
       // request.setAttribute("categories", new CategoryService().findAll());
       // request.setAttribute("magazines", new MagazineService().findAll());
        return page;
    }

    private void filter(HttpServletRequest request) {
        List<Magazine> list;
        request.removeAttribute("magazines");
        MagazineSearchParameters parameters = buildSearchParameters(request);
        request.setAttribute("categories", new CategoryService().findAll());
        int rows = new MagazineService().getCountWithParams(parameters);
        int nOfPages = rows / parameters.getRecordsPerPage();
        if (nOfPages % parameters.getRecordsPerPage() > 0) {
            nOfPages++;
        }
        list = new MagazineService().findBySearchParameters(parameters);
        request.setAttribute("magazines", list);
        request.setAttribute("nOfPages", nOfPages);
        request.setAttribute("searchParams",parameters);
    }

    private MagazineSearchParameters buildSearchParameters(HttpServletRequest request) {
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String price = request.getParameter("price");
        String recordsPerPage = request.getParameter("recordsPerPage");
        String currentPage = request.getParameter("currentPage");
        MagazineSearchParameters parameters = new MagazineSearchParameters();
        if (name!=null && !name.isEmpty()) {
            parameters.setName(name);
        }
        if (category!=null && !category.equals("all") && !category.isEmpty()) {
                parameters.setCategory(category);
        }
        if (price!=null && !price.equals("ignore")) {
            if (price.equals("ASC")) {
                parameters.setPrice_asc(true);
            } else{
                parameters.setPrice_asc(false);
            }
        }
        if(recordsPerPage!=null) {
            parameters.setRecordsPerPage(Integer.parseInt(recordsPerPage));
        }else {
            parameters.setRecordsPerPage(10);
        }
        if (currentPage != null) {
            parameters.setCurrentPage(Integer.parseInt(currentPage));
        } else {
            parameters.setCurrentPage(0);
        }
        return parameters;
    }

}

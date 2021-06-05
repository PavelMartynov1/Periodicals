package com.example.Periodicals.commands.user;

import com.example.Periodicals.commands.Command;
import com.example.Periodicals.model.entity.Magazine;
import com.example.Periodicals.model.service.MagazineService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class GetMagPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public String execute(HttpServletRequest request) {
        long id= Long.parseLong(request.getParameter("id"));
        Optional<Magazine> result=new MagazineService().find(id);
        if (result.isPresent()){
            request.setAttribute("magazine",result.get());
        } else{
            LOGGER.info("no magazine was found with id "+id);
            return "/WEB-INF/jsp/catalog.jsp";
        }

        return "/WEB-INF/jsp/magazine_page.jsp";
    }
}

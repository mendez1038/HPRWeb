package com.hpr.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.david.training.exceptions.DataException;
import com.david.training.model.Contenido;
import com.david.training.model.Usuario;
import com.david.training.service.ContenidoService;
import com.david.training.service.Results;
import com.david.training.service.impl.ContenidoServiceImpl;
import com.hpr.web.util.SessionManager;

@WebServlet("/biblioteca")
public class BibliotecaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Ajusta a tu configuración real
    private static final int DEFAULT_PAGE_SIZE = 12;
    private static final int PAGING_PAGE_COUNT = 3;

    private final ContenidoService contenidoService = new ContenidoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Usuario en sesión
        Usuario u = (Usuario) SessionManager.get(req, "user");
        if (u == null) {
            resp.sendRedirect(req.getContextPath() + ViewPaths.LOGIN);
            return;
        }

        // Idioma (fallback "es")
        String idioma = "es";
        Object locObj = SessionManager.get(req, "USER_LOCALE");
        if (locObj != null) {
            String s = locObj.toString();
            if (s.length() >= 2) idioma = s.substring(0, 2).toLowerCase();
        }

        // Paginación
        int page = parseInt(req.getParameter("pageBiblioteca"), 1);
        int size = parseInt(req.getParameter("size"), DEFAULT_PAGE_SIZE);
        if (page <= 0) page = 1;
        if (size <= 0) size = DEFAULT_PAGE_SIZE;

        // Tu DAO/Service usa startIndex 1-based
        int startIndex = ((page - 1) * size) + 1;

        
        Results<Contenido> resultados = null;

        try {

            resultados = contenidoService.miLista(u.getEmail(), idioma, startIndex, size);
            

            // Igual que Favoritos: la JSP itera una LISTA PLANA
            req.setAttribute("resultados_biblioteca", resultados.getPage());
            req.setAttribute("total_biblioteca", resultados.getTotal());

            int totalPages = (int) Math.ceil(resultados.getTotal() / (double) size);
            int firstPagedPage = Math.max(1, page - PAGING_PAGE_COUNT);
            int lastPagedPage  = Math.min(totalPages, page + PAGING_PAGE_COUNT);

            req.setAttribute("pageBiblioteca", page);
            req.setAttribute("size", size);
            req.setAttribute("totalPagesBiblioteca", totalPages);
            req.setAttribute("firstPagedPageBiblioteca", firstPagedPage);
            req.setAttribute("lastPagedPageBiblioteca", lastPagedPage);

        } catch (DataException e) {
            throw new ServletException("Error al cargar la biblioteca del usuario", e);
        } catch (Exception e) {
            throw new ServletException(e);
        } 
        // Forward a la vista
        req.getRequestDispatcher("/html/biblioteca-usuario.jsp").forward(req, resp);
    }

    private int parseInt(String s, int def) { try { return Integer.parseInt(s);} catch(Exception e){ return def; } }
}

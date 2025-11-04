package com.hpr.web.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.david.training.dao.LineaPedidoDAO;
import com.david.training.dao.PedidoDAO;
import com.david.training.dao.impl.LineaPedidoDAOImpl;
import com.david.training.dao.impl.PedidoDAOImpl;
import com.david.training.dao.util.JDBCUtils; // para cerrar
import com.david.training.exceptions.DataException;
import com.david.training.model.Pedido;
import com.david.training.model.Usuario; // asumiendo que tu sesión guarda Usuario
import com.david.training.service.Results;
import com.hpr.web.util.SessionManager;
// AJUSTA este import por tu clase de conexión
import com.david.training.dao.util.ConnectionManager;

@WebServlet(ControllerPaths.PEDIDOS)
public class PedidoHistorialServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(PedidoHistorialServlet.class);
    private final PedidoDAO pedidoDAO = new PedidoDAOImpl();
    private final LineaPedidoDAO lineaDAO = new LineaPedidoDAOImpl();
    
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Usuario u = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);
        if (u == null) { response.sendRedirect(request.getContextPath()+ ViewPaths.LOGIN); return; }

        int page = parseInt(request.getParameter(ParameterNames.PAGE_HISTORIAL), 1);
        int size = parseInt(request.getParameter("size"), 10);
        if (size <= 0) size = 10;
        if (page <= 0) page = 1;
        int startIndex = ((page - 1) * size) + 1; // tu DAO usa 1-based

        Connection c = null;
        try {
            c = ConnectionManager.getConnection();
            Results<Pedido> results = pedidoDAO.findByUsuario(c, u.getEmail(), startIndex, size);
            // Cargar líneas (simple; ojo: hace N consultas = N pedidos)
            for (Pedido p : results.getPage()) {
                p.setLineas(lineaDAO.findByPedido(c, p.getIdPedido()));
            }

            request.setAttribute(AttributeNames.RESULTADOS_HISTORIAL, results.getPage());
            request.setAttribute(AttributeNames.TOTAL_HISTORIAL, results.getTotal());
            request.setAttribute("pageHistorial", page);
            request.setAttribute("size", size);
            request.getRequestDispatcher(ViewPaths.HISTORIAL).forward(request, response);

        } catch (DataException | SQLException e) {
            logger.error("Error listando pedidos", e);
            request.setAttribute("error", "error.generico");
            request.getRequestDispatcher(ViewPaths.HOME).forward(request, response);
        } finally {
            try {
				JDBCUtils.closeConnection(c);
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    private int parseInt(String s, int def) { try { return Integer.parseInt(s); } catch(Exception e){ return def; } }
}

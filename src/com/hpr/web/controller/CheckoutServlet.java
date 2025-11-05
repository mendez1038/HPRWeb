package com.hpr.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.david.training.dao.LineaPedidoDAO;
import com.david.training.dao.PedidoDAO;
import com.david.training.dao.impl.LineaPedidoDAOImpl;
import com.david.training.dao.impl.PedidoDAOImpl;
import com.david.training.exceptions.DataException;
import com.david.training.model.Usuario;
import com.david.training.model.Contenido;
import com.david.training.model.Pedido;
import com.david.training.service.PedidoService;
import com.david.training.service.impl.PedidoServiceImpl;
import com.hpr.web.util.SessionManager;
import com.hpr.web.model.Carrito; // tu clase del carrito
import com.hpr.web.model.LineaCarrito;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(CheckoutServlet.class);
    private final PedidoDAO pedidoDAO = new PedidoDAOImpl();
    private final LineaPedidoDAO lineaDAO = new LineaPedidoDAOImpl();
    private final PedidoService pedidoService = new PedidoServiceImpl(pedidoDAO, lineaDAO);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1) Usuario logueado
        Usuario u = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);
        if (u == null) {
            response.sendRedirect(request.getContextPath() + ViewPaths.LOGIN);
            return;
        }

        // 2) Recuperar carrito de sesión (AHORA COMO OBJETO, NO COMO LIST)
        Carrito carrito = (Carrito) SessionManager.get(request, "carrito");
        if (carrito == null) {
            request.setAttribute("error", "El carrito está vacío.");
            request.getRequestDispatcher(ViewPaths.CARRITO).forward(request, response);
            return;
        }

     // Obtenemos las líneas del carrito
        List<LineaCarrito> lc = carrito.getLineas();

        // Inicializamos la lista de productos
        List<Contenido> productos = new ArrayList<>();

        // Recorremos cada línea y sacamos su Contenido
        for (LineaCarrito lineaCarrito : lc) {
            productos.add(lineaCarrito.getContenido());
        }

        // Comprobamos si hay productos
        if (productos.isEmpty()) {
            request.setAttribute("error", "El carrito está vacío.");
            request.getRequestDispatcher(ViewPaths.CARRITO).forward(request, response);
            return;
        }


        // 3) Convertir a lista de IDs de contenido
        List<Integer> idsContenido = productos.stream()
                .map(Contenido::getIdContenido)
                .collect(Collectors.toList());

        try {
            // 4) Checkout en el servicio
            Pedido p = pedidoService.checkout(u.getEmail(), idsContenido);

            // 5) Limpiar carrito
            carrito.vaciar(); // o carrito.clear(), según lo tengas
            SessionManager.set(request, "carrito", carrito);

            logger.info("Pedido {} creado para usuario {}", p.getIdPedido(), u.getEmail());

            // 6) Redirigir al historial
            response.sendRedirect(request.getContextPath() + "/pedidos");

        } catch (DataException e) {
            logger.warn("Error en checkout", e);
            request.setAttribute("error", "No se pudo completar la compra: " + e.getMessage());
            request.getRequestDispatcher(ViewPaths.CARRITO).forward(request, response);
        }
    }
}

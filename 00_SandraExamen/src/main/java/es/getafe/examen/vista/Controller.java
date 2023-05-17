package es.getafe.examen.vista;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.getafe.examen.modelo.Fabricante;
import es.getafe.examen.modelo.Producto;
import es.getafe.examen.negocio.Tienda;
import es.getafe.examen.negocio.TiendaImpl;
import es.getafe.examen.persistencia.FabricanteDao;
import es.getafe.examen.persistencia.FabricanteDaoImpl;
import es.getafe.examen.persistencia.ProductoDao;
import es.getafe.examen.persistencia.ProductoDaoImpl;

@WebServlet("/home/*")
@SuppressWarnings("serial")
public class Controller extends HttpServlet {

	String context;
	private Tienda neg;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String path = req.getPathInfo();
		
		switch (path) {
		case "/listado_productos":
			req.getRequestDispatcher("/WEB-INF/vistas/listado-productos.jsp").forward(req, resp);
			break;
		case "/productos_fabricante":
			Set<Fabricante> fabricante = neg.getFabricantesActivos();
			System.out.println(fabricante.size());
			req.setAttribute("fabs", fabricante);
			req.getRequestDispatcher("/WEB-INF/vistas/productos-fabricante.jsp").forward(req, resp);
			break;
		case "/productos_fabricante_html":
			fabricante = neg.getFabricantesActivos();
			System.out.println(fabricante.size());
			req.setAttribute("fabs", fabricante);
			req.getRequestDispatcher("/WEB-INF/vistas/productos-fabricante-html.jsp").forward(req, resp);
			break;
		case "/productos_fabricante_json":
			fabricante = neg.getFabricantesActivos();
			System.out.println(fabricante.size());
			req.setAttribute("fabs", fabricante);
			req.getRequestDispatcher("/WEB-INF/vistas/productos-fabricante-json.jsp").forward(req, resp);
			break;
		case "/ofertas":
			Set<Producto> productos = neg.getProductos();
			System.out.println(productos);
			req.setAttribute("productos", productos);
			req.getRequestDispatcher("/WEB-INF/vistas/ofertas.jsp").forward(req, resp);
			break;
		case "/menu_principal":
		default:
			req.getRequestDispatcher("/WEB-INF/vistas/menu-principal.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String path = req.getPathInfo();
		
		HttpSession sesion = req.getSession();
		
		switch(path) {
		case "/listado_productos":
			String desc = req.getParameter("descripcion");
			Set<Producto> productos;
			if(desc != null) {
				productos = neg.getProductos(desc.trim());
			} else {
				productos = neg.getProductos("");
			}
			sesion.setAttribute("productos", productos);
			resp.sendRedirect(context + "/home/listado_productos");
			break;
		case "/productos_fabricante":
			String idFab = req.getParameter("idFabricante");
			int id;
			if(idFab != null) {
				try {
					id = Integer.parseInt(idFab);
					Fabricante fab = neg.getFabricanteConProductos(id);
					sesion.setAttribute("fab", fab);
					resp.sendRedirect(context + "/home/productos_fabricante");
				} catch (NumberFormatException e) {
					resp.sendRedirect(context + "/home/cerrar_sesion");
				}
			}
			break;
		case "/productos_fabricante_html_respuesta":
			idFab = req.getParameter("idFabricante");
			if(idFab != null) {
				try {
					id = Integer.parseInt(idFab);
					Fabricante fab = neg.getFabricanteConProductos(id);
					sesion.setAttribute("fab", fab);
					req.getRequestDispatcher("/WEB-INF/vistas/productos-fabricante-html-respuesta.jsp").forward(req, resp);
				} catch (NumberFormatException e) {
					resp.sendRedirect(context + "/home/cerrar_sesion");
				}
			}
			break;
		case "/productos_fabricante_json_respuesta":
			idFab = req.getParameter("idFabricante");
			if(idFab != null) {
				try {
					id = Integer.parseInt(idFab);
					Fabricante fab = neg.getFabricanteConProductos(id);
					sesion.setAttribute("fab", fab);
					
					Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
					String json = gson.toJson(fab.getProductos());
										
					resp.getWriter().println(json);
					
//					req.getRequestDispatcher("/WEB-INF/vistas/productos-fabricante-html-respuesta.jsp").forward(req, resp);
				} catch (NumberFormatException e) {
					resp.sendRedirect(context + "/home/cerrar_sesion");
				}
			}
			break;
		case "/ofertas":
			String idsParam = req.getParameter("id_prods");
			String dtosParam = req.getParameter("descuentos");
			if(idsParam != null && dtosParam != null) {
				String[] ids = idsParam.split(",");
				String[] dtos = dtosParam.split(",");
				try {
					int[] idsProds = new int[ids.length];
					double[] dtosProds = new double[dtos.length];
					for(int i = 0; i < ids.length; i++) {
						idsProds[i] = Integer.parseInt(ids[i]);
						dtosProds[i] = Double.parseDouble(dtos[i]);
					}
					
				} catch (NumberFormatException e) {
					// TODO: handle exception
				}
			}
			break;
		}
	}
	
	@Override
	public void init() throws ServletException {
		ServletContext app = getServletContext();
		
		context = app.getContextPath();
		
		app.setAttribute("context", context);
		app.setAttribute("home", context + "/home");
		app.setAttribute("css", context + "/css");
		
		neg = new TiendaImpl();
	}
}

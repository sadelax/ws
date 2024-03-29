package negocio;

import java.util.LinkedList;
import java.util.List;

import modelo.Cliente;
import modelo.Cuenta;
import modelo.Extracto;
import modelo.Movimiento;
import modelo.Tarjeta;
import persistencia.ClienteDao;
import persistencia.ClienteDaoJPA;
import persistencia.CuentaDao;
import persistencia.CuentaDaoJPA;
import persistencia.ExtractoDao;
import persistencia.ExtractoDaoJPA;

@SuppressWarnings("serial")
public class GeneraExtractoImpl implements GeneraExtracto {

	private ClienteDao cd;
	private ExtractoDao ed;
	private CuentaDao cued;
	
	public GeneraExtractoImpl() {
		cd = new ClienteDaoJPA();
		ed  = new ExtractoDaoJPA();
		cued = new CuentaDaoJPA();
	}
	
	@Override
	public List<Cliente> getClientes(String nombre) {
		List<Cliente> clientes;
		if(nombre == null || nombre.length() == 0)
			clientes = new LinkedList<>(cd.findAll());
		else
			clientes = cd.findByValue(nombre);
		return clientes;
	}

	@Override
	public Extracto generaExtracto(Cuenta cuenta, int anyo, int mes) {
		Extracto generado = ed.extractoFechaEager(cuenta, anyo, mes);
		return generado;
	}

	@Override
	public Cliente getClienteConCuentas(int idCliente) {
		Cliente cliente = cd.findByIdEager(idCliente);
		return cliente;
	}

	@Override
	public Cliente getClienteConCuentas(Cliente c) {
		c = getClienteConCuentas(c.getIdCliente());
		return c;
	}

	@Override
	public List<Movimiento> getMovimientos(Extracto ext, Tarjeta tjta) {
		Extracto e = ed.findByIdEager(ext.getIdExtracto());
		List<Movimiento> movimientos = new LinkedList<>();
		for (Movimiento movimiento : e.getMovimientos()) {
			if (movimiento.getTarjeta().equals(tjta)) {
				movimientos.add(movimiento);
			} else {
				System.out.println("movimiento.getTarjeta not equals a tarjeta sus tostring lol");
			}
		}
		System.out.println("movimientos neg" + movimientos);
		return movimientos;
	}

	@Override
	public Cuenta getCuenta(int id) {
		return cued.findByIdEager(id);
	}

}

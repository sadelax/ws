package colecciones.genericos.listas;

public class ListaArray<E> implements Lista<E> {
	
	private Object[] almacen;
	private int cantidad;
	
	public ListaArray() {
		almacen = new Object[10];
		cantidad = 0;
	}
	
	public ListaArray(int tam) {
		almacen = new Object[tam];
		cantidad = 0;
	}
	
	@Override
	public void agregar(E dato) {
		if(almacen.length == cantidad) {
			redimensionar();
		}
		almacen[cantidad] = dato;
		cantidad++;
	}

	@Override
	public E eliminar(int pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E buscar(int pos) {
		return (E)almacen[pos];
	}

	@Override
	public boolean estaVacia() {
		return cantidad == 0;
	}

	@Override
	public int tamanyo() {
		return cantidad;
	}
	
	private void redimensionar() {
		Object[] nuevo = new Object[almacen.length * 2];
		for(int i = 0; i < almacen.length; i++) {
			nuevo[i] = almacen[i];
		}
		almacen = nuevo;
	}
}

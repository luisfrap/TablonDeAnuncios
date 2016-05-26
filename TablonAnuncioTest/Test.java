import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import junio2013.*;

public class Test {

	TablonDeAnuncios tablon = null;

	@Before
	public void inicializa() {
		tablon = new TablonDeAnuncios();
	}

	@After
	public void finaliza() {
		tablon = null;
	}

	// Test 1
	@org.junit.Test
	public void inicialmenteTieneUnAnuncio() {
		int cantidadEsperada = 1;
		assertEquals(cantidadEsperada, tablon.anunciosPublicados());
	}

	// Test 2
	@org.junit.Test
	public void crearAnuncioDeEmpresa() {
		int cantidadEsperada = tablon.anunciosPublicados() + 1;
		Anuncio anuncio = new Anuncio("Inserccion de anuncio", "Anuncio", "LA EMPRESA");
		IBaseDeDatosDeAnunciantes bdAnunciantes = null;
		IBaseDeDatosDePagos bdPagos = null;
		tablon.publicarAnuncio(anuncio, bdAnunciantes, bdPagos);

		assertEquals(cantidadEsperada, tablon.anunciosPublicados());
	}

	// Test 3
	@SuppressWarnings("unchecked")
	@org.junit.Test
	public void crearAnuncioNoEmpresa() {
		int cantidadEsperada = tablon.anunciosPublicados();
		Anuncio anuncio = new Anuncio("Inserccion de anuncio", "Anuncio", "ANUNCIANTE");
		IBaseDeDatosDeAnunciantes bdAnunciantes = (IBaseDeDatosDeAnunciantes) mock(IBaseDeDatosDeAnunciantes.class);
		AnuncianteNoExisteException excepcion = (AnuncianteNoExisteException)mock(AnuncianteNoExisteException.class);
		IBaseDeDatosDePagos bdPagos = (IBaseDeDatosDePagos) mock(IBaseDeDatosDePagos.class);
	
		
		when(bdAnunciantes.buscarAnunciante(anuncio.anunciante_)).thenReturn(true);
		when(bdPagos.anuncianteTieneSaldo(anuncio.anunciante_)).thenReturn(true);
		when(!bdPagos.anuncianteTieneSaldo(anuncio.anunciante_)).thenReturn(false);

	//	when(!bdAnunciantes.buscarAnunciante(anuncio.anunciante_)).thenThrow(excepcion);

		tablon.publicarAnuncio(anuncio, bdAnunciantes, bdPagos);
		assertEquals(cantidadEsperada, tablon.anunciosPublicados());

		verify(bdAnunciantes).buscarAnunciante(anuncio.anunciante_);
		verify(bdPagos).anuncianteTieneSaldo(anuncio.anunciante_);
	}

	// Test 5

	@org.junit.Test
	public void publicarDosAnunciosIgualesDeLaEmpresa() {
		int cantidadEsperada = tablon.anunciosPublicados() + 1;

		Anuncio anuncio1, anuncio2 = null;
		// Este se publica
		anuncio1 = new Anuncio("Prueba de Anuncio", "Anuncio cosas", "LA EMPRESA");

		// Este no se publica
		anuncio2 = new Anuncio("Prueba de Anuncio", "Anuncio cosas", "LA EMPRESA");

		IBaseDeDatosDeAnunciantes bdAnunciantes = null;
		IBaseDeDatosDePagos bdPagos = null;
		tablon.publicarAnuncio(anuncio1, bdAnunciantes, bdPagos);
		if (tablon.buscarAnuncioPorTitulo("Prueba de Anuncio") == null) {

			// Buscamos si existe el segundo anuncio antes de publicat
			tablon.publicarAnuncio(anuncio2, bdAnunciantes, bdPagos);
		}

		assertEquals(cantidadEsperada, tablon.anunciosPublicados());

	}

	// Test 6
	@org.junit.Test
	public void publicarDosAnunciosYBorrarElPrimero() {
		Anuncio resultadoEsperado = null;

		Anuncio anuncio, anuncio2 = null;
		anuncio = new Anuncio("Prueba de Anuncio", "Anuncio cosas", "LA EMPRESA");
		anuncio2 = new Anuncio("Prueba de Anuncio 2", "Anuncio cosas", "LA EMPRESA");
		IBaseDeDatosDeAnunciantes bdAnunciantes = null;
		IBaseDeDatosDePagos bdPagos = null;
		tablon.publicarAnuncio(anuncio, bdAnunciantes, bdPagos);
		tablon.publicarAnuncio(anuncio2, bdAnunciantes, bdPagos);
		tablon.borrarAnuncio("Prueba de Anuncio", "LA EMPRESA");
		Anuncio resultado = tablon.buscarAnuncioPorTitulo("Prueba de Anuncio");

		assertEquals(resultadoEsperado, resultado);

	}
}

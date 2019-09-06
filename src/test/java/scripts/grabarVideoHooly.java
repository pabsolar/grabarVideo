package scripts;

import java.awt.RenderingHints.Key;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;


import Tools.tools;
import pageFactory.pageIndex;

public class grabarVideoHooly {

	tools tools;
	pageIndex objIndex;
	
	List<String> errores = new ArrayList<>();
	
	String Caso = "caso0001_Validar_valor_rut_invalido";
	String _directory = "scripts";
	String _class = Caso;
	String _method = "";
	
	
	
	boolean continuar = true;
	
	@BeforeTest
	public void ejecutarBrowser() {
		tools = new tools();
		try {
			System.out.println("Se ejecuta chromedriver");
			Thread.sleep(100);
			//tools.setUrl("https://qa.hooly.app/login/");
			tools.init();
			Thread.sleep(1000);
			tools.iniciarGrabacion();
			Thread.sleep(300);
			tools.screenshot("scripts", Caso , "Cargar URL");
			System.out.println("Se inicia el paso 1");
			Thread.sleep(100);
			
		} catch (Exception e) {
			continuar = false;
			tools.skipScript(e);
			tools.stop();
		}
		
	}
	
	@Test (priority = 0)
	public void ingresarRutInvalido() {
		objIndex = new pageIndex(tools.getDriver());
		try {
			tools.screenshot("scripts", Caso , "Hooly Cargado");
			System.out.println("Se Ingresa un RUT no valido");
			Thread.sleep(300);
			objIndex.textRut.click();
			Thread.sleep(300);
			objIndex.textRut.sendKeys("1K2K3K4K");
			tools.screenshot("scripts", Caso , "Ingresar RUT no valido");
			Thread.sleep(300);
			
		} catch (Exception e) {
			continuar = false;
			tools.skipScript(e);
			System.out.println("El paso " + _method + "no ha podido ser ejecutado satisfactoriamente, se detiene el script");
			tools.stop();
		}
		
	}
	
	@Test (priority = 1)
	public void cargarEvidencias() {
		try {
			
			System.out.println("Cargando datos a GIT");
			
//			ProcessBuilder processBuilder = new ProcessBuilder();
//			processBuilder.command("bash", "-c", "cd " + System.getProperty("user.dir")+"/evidencia/" + 
//					" ; git init ; " +
//					" git add . ; " +
//					" git commit -m \"all\" ;" +
//					" git fetch ; " +
//					" git pull --rebase origin master ; " +
//					" git commit -m \"AutoTest\" ; " +
//					" git remote add origin https://github.com/AFP-Capital/hooly-evidencia.git ; " +
//					" git push  ; " +
//					" git push origin master ; " +
//					" git push -u origin master");
//			Process p = processBuilder.start();
			
			Thread.sleep(3000);
			
		} catch (Exception e) {
			continuar = false;
			tools.skipScript(e);
			System.out.println("El paso " + _method + "no ha podido ser ejecutado satisfactoriamente, se detiene el script");
			tools.stop();
		}
		
	}
	
	@AfterTest
	public void cerrarBrowser() {
		try {
			
			System.out.println("Cerrando Caso");
			tools.screenshot("scripts", Caso , "Prueba Finalizada");
			Thread.sleep(3000);
			tools.finalizarGrabacion();
			Thread.sleep(1000);
			tools.stop();
			
		} catch (Exception e) {
			continuar = false;
			tools.skipScript(e);
			System.out.println("El paso " + _method + "no ha podido ser ejecutado satisfactoriamente, se detiene el script");
			tools.stop();
		}
		
	}
}

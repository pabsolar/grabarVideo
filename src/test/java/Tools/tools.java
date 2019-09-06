package Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
//Fecha
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;

import java.awt.*; 
import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

import org.junit.*;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.monte.media.math.Rational;
import org.monte.media.Format;
import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class tools {
	
	private String url;
	private String browser;
	private WebDriver driver;
	private JavascriptExecutor executor;
	
	private static ScreenRecorder screenRecorder;
	
	
	
	private WebDriverWait wait;
	private String root;
	private int count_screenshot;
	private String nombreCarpeta;
	public String carpetaEvidencia;
	public String evidenciaVideo = "EvidenciaEnVideo";
	
	public tools(){
		this.url = "https://qa.hooly.app/login/";
		//this.url = "https://ventasdesarrolloafp.com/inicio";
		this.browser = "chrome";
		this.root = "";
		this.count_screenshot = 0;
		
		//Crear nombre de carpeta con fecha y hora
		Date date = new Date();
		DateFormat hourFormat = new SimpleDateFormat("HH-mm-ss");
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		this.nombreCarpeta = "Prueba Ejecutada el " + dateFormat.format(date) + " " + hourFormat.format(date);
	}
	
	
	/* Get and Setter */
	public WebDriver getDriver() {
		return this.driver;
	}
	
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getBrowser() {
		return this.browser;
	}
	
	public void setBrowser(String browser) {
		browser = browser.toLowerCase();
		if(browser.equalsIgnoreCase("chrome") || browser.equalsIgnoreCase("ie") || browser.equalsIgnoreCase("firefox")) {
			this.browser = browser;
		}
	}
	
	
	/* Initializer */
	public WebDriver init() throws MalformedURLException, IOException, AWTException {
		switch(this.browser) {
			case "chrome":
				System.out.println(System.getProperty("user.dir") + "/chromedirver");
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/chromedriver");
				this.driver = new ChromeDriver();
				this.driver.manage().deleteAllCookies();
				this.driver.manage().window().maximize();
				this.driver.get(this.url);
				

				GraphicsConfiguration gc = GraphicsEnvironment
		                .getLocalGraphicsEnvironment()
		                .getDefaultScreenDevice()
		                .getDefaultConfiguration();

		        screenRecorder = new ScreenRecorder(gc,
		                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
		                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
		                        CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
		                        DepthKey, (int)24, FrameRateKey, Rational.valueOf(15),
		                        QualityKey, 1.0f,
		                        KeyFrameIntervalKey, (int) (15 * 60)),
		                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,"black",
		                        FrameRateKey, Rational.valueOf(30)),
		                null);
				break;
			case "ie":
				break;
			case "firefox":
				break;
			default:
				System.out.println("Ese navegador no existe.");
		}
		this.driver.manage().deleteAllCookies();
		this.driver.manage().window().maximize();
		this.driver.get(this.url);
		this.wait = new WebDriverWait(this.driver, 0);
		this.executor = (JavascriptExecutor)this.driver;
		return this.driver;
	}
	
	public void stop() {
		if(this.driver != null) {
			this.driver.close();
			this.driver.quit();
		}
	}
	

	/* Evidence */
	public void screenshot(String _directory, String _class, String _method) throws IOException{
		String s_route = "";
		if(_directory == null || _directory == "") {
			s_route = _class;
		}else {
			_directory = _directory.replace("/",".");
			s_route = _directory + "." + _class;
		}
		if(this.root.isEmpty()) {
			this.root = (new File(".")).getCanonicalPath();
		}
		
		String path = System.getProperty("user.dir")+"/evidencia/" + this.nombreCarpeta + "/"+ s_route+"/";
		
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(path + this.count_screenshot + "_" +_method +".png"));
		
		this.count_screenshot++;
	}
	
    
    public void iniciarGrabacion() throws IOException {
        screenRecorder.start();
    }
    
    public void nombreVideo(String _directory, String _class, String _method) throws IOException{
		String s_route = "";
		
		if(_directory == null || _directory == "") {
			s_route = _class;
		}else {
			_directory = _directory.replace("/",".");
			s_route = _directory + "." + _class;
		}
		if(this.root.isEmpty()) {
			this.root = (new File(".")).getCanonicalPath();
		}
		
		String path = System.getProperty("user.dir")+"/evidencia/" + this.nombreCarpeta + "/"+ s_route+"/";
		
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		FileUtils.copyFile(src, new File(path + this.count_screenshot + "_" +_method));
		
		
	}

    public void finalizarGrabacion() throws IOException {
    	
    	 screenRecorder.stop();
    	 
         
    }
	
	
	/* WebElement tools*/
	public void click(WebElement element){
    	this.wait.until(ExpectedConditions.visibilityOf(element));
    	element.click();
    }
	
	public boolean available(WebElement element) {
		System.out.print("Método: available. ");
		boolean available = true;
		try {
			element.getSize();
		}catch (Exception e) {
			available = false;
		}
		System.out.println("avaiable: " + available);
		return available;
	}
	
	public WebElement findElementByContent(List<WebElement> list, String content) {
		System.out.print("Método: findElementByContent. ");
		WebElement element = null;
		content = content.toLowerCase();
		System.out.print("Cantidad: " + list.size() + ". ");
    	for(int i=0; i<list.size();i++) {
    		int index = list.get(i).getText().toLowerCase().indexOf(content);
			if(index != -1) {
				element = list.get(i);
				break;
			}
		}   	
    	if(element != null)
    		System.out.println("Se encontró el elemento.");
    	else {
    		System.out.println("No se encontró el elemento.");
    	}
    	
    	return element;
	}
	
	
	/* Result skip */
	public void skipPreviousMethod() {
		throw new SkipException("\n" +
				" =============================================================== \n" +
				" Presentación del Error										  \n" +
				" ===============================================================");
	}
	
	public void skipScript(Exception e) {
		this.stop();
		throw new SkipException("\n" + 
				" =============================================================== \n" +
				" Mensaje de Error: contact the automation. \n" +
				"    * Exception: " + e.toString() + "\n" +
				"    * Message: " + e.getMessage() + "\n" +
				"    * Localized Message: " + e.getLocalizedMessage() + "\n" +
				"    * Cause: " + e.getCause() + "\n" +
				"    * fillInStackTrace: " + e.fillInStackTrace() + "\n" +
				"    * Clase: " + e.getClass().getName() +  "\n" +
				"    * Suppressed: " + e.getSuppressed().toString() + "\n" +
				" ==============================================================="
			);
	}
	
	/* Result fail */
	public void failMethod(List<String> errores){
		this.stop();
		String message = "\n" +
				" =============================================================== \n" +
				" Descripción del Error: El script falló por " + (errores.size() > 1 ? "los siguientes motivos: " : "el siguiente motivo: ") + " \n";
		for(int i=0;i<errores.size();i++) {
			message += " * " + errores.get(i) + " \n";
		}
		message += " =============================================================== \n";
		Assert.assertTrue(false, message);
	}
	
}
package controladores;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ControladorIconosFicheros {

	private final static String avi = "avi.png";
	private final static String css = "css.png";
	private final static String dat = "dat.png";
	private final static String dmg = "dmg.png";
	private final static String doc = "doc.png";
	private final static String docx = "docx.png";
	private final static String dwg = "dwg.png";
	private final static String exe = "exe.png";
	private final static String gif = "gif.png";
	private final static String html = "html.png";
	private final static String iso = "iso.png";
	private final static String java = "java.png";
	private final static String jpg = "jpg.png";
	private final static String m4v = "m4v.png";
	private final static String mid = "mid.png";
	private final static String mov = "mov.png";
	private final static String mp3 = "mp3.png";
	private final static String mp4 = "mp4.png";
	private final static String mpg = "mpg.png";
	private final static String odp = "odp.png";
	private final static String ods = "ods.png";
	private final static String odt = "odt.png";
	private final static String pdf = "pdf.png";
	private final static String php = "php.png";
	private final static String png = "png.png";
	private final static String py = "py.png";
	private final static String rar = "rar.png";
	private final static String tgz = "tgz.png";
	private final static String txt = "txt.png";
	private final static String wav = "wav.png";
	private final static String xml = "xml.png";
	private final static String zip = "zip.png";

	public static JLabel recuperarIcono(String extension) {
		// Cargar Imagen
		JLabel foto = new JLabel();
		String rutaImagen = null;
		if (extension.length() == 0) {
			rutaImagen = "iconos_archivos\\carpeta.png";
			foto.setSize(50, 50);
		} else {
			rutaImagen = rutaCorrespondiente(extension);
			foto.setSize(80, 80);
		}
		ImageIcon icon = new ImageIcon(rutaImagen);
		foto.setHorizontalAlignment(SwingConstants.CENTER);
		
		Icon icono = new ImageIcon(
				icon.getImage().getScaledInstance(foto.getWidth(), foto.getHeight(), Image.SCALE_DEFAULT));
		foto.setIcon(icono);
		return foto;
	}

	public static String rutaCorrespondiente(String extension) {
		String ruta = "iconos_archivos\\";

		if (extension.contains("avi")) {
			ruta += avi;
		} else if (extension.contains("css")) {
			ruta += css;
		} else if (extension.contains("dat")) {
			ruta += dat;
		} else if (extension.contains("dmg")) {
			ruta += dmg;
		} else if (extension.contains("doc")) {
			ruta += doc;
		} else if (extension.contains("docx")) {
			ruta += docx;
		} else if (extension.contains("dwg")) {
			ruta += dwg;
		} else if (extension.contains("exe")) {
			ruta += exe;
		} else if (extension.contains("gif")) {
			ruta += gif;
		} else if (extension.contains("html")) {
			ruta += html;
		} else if (extension.contains("iso")) {
			ruta += iso;
		} else if (extension.contains("java")) {
			ruta += java;
		} else if (extension.contains("jpg")) {
			ruta += jpg;
		} else if (extension.contains("m4v")) {
			ruta += m4v;
		} else if (extension.contains("mid")) {
			ruta += mid;
		} else if (extension.contains("mov")) {
			ruta += mov;
		} else if (extension.contains("mp3")) {
			ruta += mp3;
		} else if (extension.contains("mp4")) {
			ruta += mp4;
		} else if (extension.contains("mpg")) {
			ruta += mpg;
		} else if (extension.contains("odp")) {
			ruta += odp;
		} else if (extension.contains("ods")) {
			ruta += ods;
		} else if (extension.contains("odt")) {
			ruta += odt;
		} else if (extension.contains("pdf")) {
			ruta += pdf;
		} else if (extension.contains("php")) {
			ruta += php;
		} else if (extension.contains("png")) {
			ruta += png;
		} else if (extension.contains("py")) {
			ruta += py;
		} else if (extension.contains("rar")) {
			ruta += rar;
		} else if (extension.contains("tgz")) {
			ruta += tgz;
		} else if (extension.contains("txt")) {
			ruta += txt;
		} else if (extension.contains("wav")) {
			ruta += wav;
		} else if (extension.contains("xml")) {
			ruta += xml;
		} else if (extension.contains("zip")) {
			ruta += zip;
		} else {

		}

		return ruta;
	}
}

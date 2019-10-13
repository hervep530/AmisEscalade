package com.ocherve.jcm.form;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * Form provide generic attributes and methods 
 * @author herve_dev
 *
 */
public abstract class Form {
	
    protected static final Logger DLOG = LogManager.getLogger("development_file");
    protected static final Level DLOGLEVEL = Level.TRACE;
    public static final String UPLOAD_PATH = "/home/1072/3/.dev/Donnees/Projets/JavaEE/Eclipse/workspace/AmisEscalade/WebContent/medias";
    protected static final Integer UPLOAD_BUFFER_SIZE = 1024*1024;
    
	
	protected Map<String,String> errors;
	protected HttpServletRequest request;
	protected Boolean partMethod;
	protected String filepath = "";
	protected String filename = "";
	protected String tmpFilename = "";
	protected String image = "";
	protected boolean updatingName = false;
	protected boolean updatingFile = false;

	
	Form(){
		Configurator.setLevel(DLOG.getName(), DLOGLEVEL);
		this.errors = new HashMap<>();
		this.partMethod = false;
	}
	
	/*
	 * Specific form must have a constructor with request as argument and set request attribute from it
	 * In order to use partMethod do not forget :
	 * - to add enctype to "multipart-form/data" in jsp form
	 * - to configure multipart for the target servlet (annotation or web.xml)
	 * - to add a hidden field in order to test if partMethod is need or not, for no-file field (with this test field,
	 * 		if getParameter(...) return null just set partMethod to true in constructor)
	 */
	
	
	/**
	 * Generic method to get string field value using getParameter or getParts
	 * @param fieldName
	 * @return
	 */
	protected String getInputTextValue(String fieldName ) {
		String value = "";
		String fieldValue = "";
		try {
			if ( ! this.partMethod )
				fieldValue = (String) this.request.getParameter( fieldName );
			else
				fieldValue = getStringValue(this.request.getPart(fieldName));			
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, e.getMessage());							
		}
	    if ( fieldValue != null ) value = fieldValue ;
	    return value.trim();
	}

	/**
	 * Generic method to get integer field value using getParameter or getParts
	 * @param fieldName
	 * @return
	 */
	protected Integer getInputIntegerValue(String fieldName) {
		Integer value = 0;
		try {
			if ( ! this.partMethod ) {
			    value = Integer.valueOf(this.request.getParameter( fieldName ));
			} else {
				value = Integer.valueOf(getStringValue(this.request.getPart(fieldName)));
			}
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, e.getMessage());							
		}
	    return value;
	}
	
	/**
	 * Generic method to get integer field value using getParameter or getParts
	 * @param fieldName
	 * @return
	 */
	protected Map<String,String> getMultiSelectValues(String fieldName) {
		String[] stringValues = null;
		Map<String,String> selectedIds = new HashMap<>();
		try {
			if ( ! this.partMethod ) {
				stringValues = this.request.getParameterValues( fieldName );
			} else {
				// not implemented - return null with part
				stringValues = getStringArrayValues(this.request.getPart(fieldName));
			}
			//  If input is null, output is null
			if (stringValues == null) return null;
			for ( String idString : stringValues ) {
				selectedIds.put(idString, " selected");
			}
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, e.getMessage());							
		}
	    return selectedIds;
	}
	
	/**
	 * Get part as raw data - upload file
	 * 
	 * @param fieldName
	 * @return
	 */
	protected Part getRawPart(String fieldName) {
		Part part = null;
		try { 
			// for upload only part method is available - so no test needed on it
			part = this.request.getPart(fieldName);
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, e.getMessage());							
		}
	    return part;
		
	}

	/**
	 * Generic method to get checkbox (boolean) value using getParameter or getParts
	 * @param fieldName
	 * @return
	 */
	protected Boolean getCheckBoxValue(String fieldName ) {
		Boolean value = false;
		try {
			if ( ! this.partMethod ) {
			    value = Boolean.valueOf(this.request.getParameter( fieldName ));
			} else {
				value = Boolean.valueOf(getStringValue(this.request.getPart(fieldName)));
			}
		} catch (Exception e) { 
			DLOG.log(Level.DEBUG, e.getMessage());				
		}
	    return value;
	}
		
	/**
	 * Utility added in order to read InputStream contained in part and convert it in string.
	 */
	protected String getStringValue( Part part ) throws IOException {
	    BufferedReader reader = new BufferedReader( new InputStreamReader( part.getInputStream(), "UTF-8" ) );
	    StringBuilder stringValue = new StringBuilder();
	    char[] buffer = new char[1024];
	    int longueur = 0;
	    while ( ( longueur = reader.read( buffer ) ) > 0 ) {
	    	stringValue.append( buffer, 0, longueur );
	    }
	    return stringValue.toString();
	}

	/**
	 * Method to get array values - for example multiselect field.
	 */
	protected String[] getStringArrayValues( Part part ) throws IOException {
	    /*
		BufferedReader reader = new BufferedReader( new InputStreamReader( part.getInputStream(), "UTF-8" ) );
	    StringBuilder stringValue = new StringBuilder();
	    char[] buffer = new char[1024];
	    int longueur = 0;
	    while ( ( longueur = reader.read( buffer ) ) > 0 ) {
	    	stringValue.append( buffer, 0, longueur );
	    }
	    return stringValue.toString();
	    */
		return null;
	}
	
	/**
	 * Get filename from file upload field
	 * 
	 * @param part
	 * @return
	 * @throws IOException
	 */
	protected String getFileName( Part part )  throws IOException {
    	if ( part == null ) {
    		DLOG.log(Level.ERROR, "This part is null");
    		return "";
    	}
        for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
            if ( contentDisposition.trim().startsWith( "filename" ) ) {
            	// bug InternetExplorer
            	String fileName = contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 )
            										.trim()
            										.replace( "\"", "" );
            	fileName = fileName.substring(contentDisposition.lastIndexOf('/') + 1);
                return fileName;
            }
        }
        return "";
    }   

	/**
	 * Write uploaded file on filesystem using input stream
	 * 
	 * @param part
	 * @param uploadPath
	 * @param fileName
	 * @param bufferSize
	 * @throws IOException
	 */
	protected void writeUploadFile( Part part, String uploadPath, String fileName, int bufferSize) throws IOException {
        BufferedInputStream inFile = null;
        BufferedOutputStream outFile = null;
        try {
        	inFile = new BufferedInputStream(part.getInputStream(), bufferSize);
        	outFile = new BufferedOutputStream(new FileOutputStream(new File(uploadPath + "/" + fileName)), bufferSize);
        	DLOG.log(Level.DEBUG, "Upload done : " + uploadPath + "/" + fileName);
            byte[] tampon = new byte[bufferSize];
            int longueur;
            while ((longueur = inFile.read(tampon)) > 0) {
            	outFile.write(tampon, 0, longueur);
            }
        } catch (Exception e) { 
        	DLOG.log(Level.ERROR, e.getMessage());
        } finally {
            try {
            	outFile.close();
            } catch (IOException ignore) {
            }
            try {
            	inFile.close();
            } catch (IOException ignore) {
            }
        }
    }

	protected void updateImage() {
		if ( this.updatingFile ) {
			// Posting an image, we remove the old one and published the new one
			try {
				this.removeFile(this.image, false);
				this.publishFile(this.tmpFilename);
			} catch (FormException ignore) {
				DLOG.log(Level.ERROR, "Changing image and filename : Error on publishing image");
			}
		} else {
			if (this.updatingName ) try { 
				// No change on image, but filename changed
				this.publishFile(this.image); 
			} catch (FormException ignore) {
				DLOG.log(Level.ERROR, "No change on image, but filename changed : Error on re-publishing image");				
			}
		}		
	}
	
	protected void removeFile(String filename, boolean fallback) throws FormException {
		// this.tmpFilename
		try {
			File file = new File(this.filepath + "/" + filename);
			file.delete();
		} catch (Exception ignore) {
			DLOG.log(Level.ERROR, "Removing file : " + filename + "removing failed.");
		}
		if ( fallback ) throw new FormException("L'envoi du formulaire à échoué. Il faut ré-envoyer le fichier.");			
	}

	protected void publishFile(String filename) throws FormException {
		try {
			File file = new File(this.filepath + "/" + filename);
			file.renameTo(new File(this.filepath + "/" + this.filename));
		} catch (Exception ignore) {
			DLOG.log(Level.ERROR, "Publishing file : " + this.tmpFilename + "uploaded but publishing failed.");
		}
	}


    /**
     * @return errors
     */
    public Map<String,String> getErrors() {
    	return errors;
    }

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
    
    

}

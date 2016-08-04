/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package print_server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

/**
 *
 * @author Sudarshan
 */
public class DoPrint extends Thread {

    String file_name;
    File file;
    FileInputStream fileInputStream;
    public DoPrint(String path) {
        file_name = path;
    }

    @Override
    public void run() {
        try {
            //super.run(); //To change body of generated methods, choose Tools | Templates.
            //ServerFrame.updateMsgWindow("Info : Printing");
            fileInputStream = new FileInputStream(file_name);
            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            Doc mydoc = new SimpleDoc(fileInputStream, flavor, null);

            PrintService[] services = PrintServiceLookup.lookupPrintServices(flavor, aset);
            PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
            
            if(services != null){
                if(defaultService == null) {
                    ServerFrame.updateMsgWindow("ERROR: No printer found");
               } else {
                    DocPrintJob job = defaultService.createPrintJob();
                    job.print(mydoc, aset);
                    ServerFrame.updateMsgWindow("Info,: Printing the doc" + file_name);
               }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DoPrint.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PrintException ex) {
            Logger.getLogger(DoPrint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}

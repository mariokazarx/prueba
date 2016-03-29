/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.clases;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import com.tesis.managedbeans.mbvBoletinesAnteriores;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

/**
 *
 * @author Mario Jurado
 */
public class BackgroundF extends PdfPageEventHelper {
        
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        URL url;
        try {
            url = FacesContext.getCurrentInstance().getExternalContext().getResource("/escudo.png");
            PdfContentByte canvasP = writer.getDirectContentUnder();
            Image imageBackgroundP = Image.getInstance(url);
            imageBackgroundP.scaleAbsolute(400,400);
            imageBackgroundP.setAbsolutePosition((document.getPageSize().getWidth()/2)-200,(document.getPageSize().getHeight()/2)-200);
            canvasP.saveState();
            PdfGState stateP = new PdfGState();
            stateP.setFillOpacity(0.15f);
            canvasP.setGState(stateP);
            canvasP.addImage(imageBackgroundP);
            canvasP.restoreState();
            Phrase footer = new Phrase("¡Educamos para la paz y el Progreso!", FontFactory.getFont("arial",10,Font.ITALIC));
            ColumnText.showTextAligned(canvasP, Element.ALIGN_CENTER,
                footer,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.bottom() - 1, 0);
            Phrase footer2 = new Phrase("Sede Escuela Bloque 3 - Ricaurte Nariño Celular: 3218300831 Emll cericaurte@hotmail.com", FontFactory.getFont("arial",10,Font.ITALIC));
            ColumnText.showTextAligned(canvasP, Element.ALIGN_CENTER,
                footer2,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.bottom() - 10, 0);

        } catch (Exception ex) {
            Logger.getLogger(mbvBoletinesAnteriores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
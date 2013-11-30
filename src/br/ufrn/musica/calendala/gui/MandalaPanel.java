package br.ufrn.musica.calendala.gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.ufrn.musica.calendala.io.ResourceIO;
import br.ufrn.musica.calendala.mandala.Mandala;
import br.ufrn.musica.calendala.mandala.Ring;
import br.ufrn.musica.calendala.util.MathUtils;

/**
 * @author Felipe Cortez de Sá
 * @version 0.1
 * @since 0.1
 */

public class MandalaPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final int DOC_MARGIN = 50;
	private BasicStroke mandalaStroke = new BasicStroke(0.8f);
	private int selectedBoundsX, selectedBoundsY;
	private BufferedImage bi, overlay;
	private JTextField editField;
	private boolean showHelpOverlay = false;
	private boolean showSelection = true;
    				
    public MandalaPanel() {

    	//Tries to import the overlay image
        try {
        	overlay = ImageIO.read(ResourceIO.overlay);
        } catch(IOException e) {
        	System.out.println("Could not load file");
        }
        
        editField = new JTextField();
		editField.setHorizontalAlignment(JTextField.CENTER);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(MainFrame.WIDTH, MainFrame.WIDTH));
        setFocusable(true);
        setLayout(null); //Allows absolute positioning of components
        
        editField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String finalText = editField.getText();
				if(finalText.equals(""))
					finalText = " ";
				
				//Mandala.getInstance().getFirstSelectedSlice().setTitle(finalText);
				remove(editField);
				repaint();
			}
		});
        
    }
    
    public void actionPerformed(ActionEvent ae) {
    	repaint();
    }
    
    public void editField() {
		editField.setBounds(selectedBoundsX - 31, selectedBoundsY - 2, 70, 20);
		add(editField);
		editField.setText(editField.getText());
		editField.selectAll();
		editField.grabFocus();
    }

    public Path2D AnnularSector(float start, float extent, float r, float R) {
        Path2D path;
        //Set 0 degree to 12 o'clock
        start = 90 - start;
        path = new Path2D.Float();
        Arc2D.Float arc = new Arc2D.Float();
        arc.setArcByCenter(0, 0, r, start, -extent, Arc2D.OPEN);
        path.append(arc, true);
        arc.setArcByCenter(0, 0, R, start - extent, extent, Arc2D.OPEN);
        path.append(arc, true);
        path.closePath();
        
        return path;
    }
    
	public void drawMandala() {
		Graphics2D g2d = bi.createGraphics();

		Composite original = g2d.getComposite();
		Composite translucentDarker = 
				AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);	
		Composite translucentDark = 
				AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);	
		Composite translucentBright = 
				AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);	
		Composite translucentBrighter = 
				AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.04f);	
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        
        
        g2d.setColor(Color.black);
        g2d.setStroke(mandalaStroke);
        
        int width = getWidth();
        int ringsNum = Mandala.getInstance().getRings().size();
        int ringSize = (width - DOC_MARGIN) / ringsNum;

        /*
        
        for(int i = 0; i < ringsNum; i++) {
        	Ring currentRing = Mandala.getInstance().getRings().get(i);
    		double arcRadius = (ringSize * (ringsNum - i)) / 2;
			
	        fullArc.setArcByCenter(width / 2, width / 2, arcRadius, 90, 0, Arc2D.OPEN);
	        fullArc.start = 0;
	        fullArc.setAngleExtent(360);

    		ringArc.setArcByCenter(width / 2, width / 2, arcRadius, 90, 0, Arc2D.PIE);
    		

    		//Smallest slice angle
    		double divAngle = 360 / currentRing.getSubdivisions();

    		//Draws selected slices
			int initialSlice = Mandala.getInstance().getSelectionStart();
    		int selSlices = Mandala.getInstance().getSelectionRange();
    		int totalSlices = currentRing.getSubdivisions();

    		if(selSlices < 0) {
    			initialSlice = MathUtils.circularize(initialSlice + selSlices, totalSlices);
    			selSlices *= -1;
    		}

    		ringArc.start -= divAngle * initialSlice + 1;

    		while(totalSlices > 0) {
	    		ringArc.start -= divAngle;
        		ringArc.setAngleExtent(divAngle);

        		g2d.setComposite(original);
        		g2d.setColor(Color.white);
        		g2d.fill(ringArc);
        		
        		if(Mandala.getInstance().getSelectedRing() == currentRing && selSlices >= 0) {
        			g2d.setColor(Color.gray);
	    			g2d.setComposite(translucentDark);
	    			g2d.fill(ringArc);
	    			selSlices--;
	    		}
        		
        		g2d.setComposite(translucentDark);
        		g2d.setColor(Color.darkGray);
        		g2d.draw(ringArc);
    			totalSlices--;
    		}

    		g2d.setComposite(translucentDarker);
    		g2d.draw(fullArc);
        }
        
        // Renders the text
        for(int i = 0; i < ringsNum; i++) {
        	// See original code on GitHub
        }
        */

        //Sets center to (0, 0)
        g2d.translate(width / 2, width / 2);
        for(int i = 0; i < ringsNum; i++) {
        	Ring currentRing = Mandala.getInstance().getRings().get(i);
    		float arcRadius = (ringSize * (ringsNum - i)) / 2;
    		float divAngle = 360 / currentRing.getSubdivisions();

			int initialSlice = Mandala.getInstance().getSelectionStart();
    		int selSlices = Mandala.getInstance().getSelectionRange();
    		int totalSlices = currentRing.getSubdivisions();
        }


        Path2D path = AnnularSector(0, 45, 150, 200);
        g2d.setColor(Color.lightGray);
        g2d.fill(path);
        g2d.setColor(Color.darkGray);
        g2d.draw(path);

        path = AnnularSector(45, 45, 150, 250);
        g2d.setColor(Color.lightGray);
        g2d.fill(path);
        g2d.setColor(Color.darkGray);
        g2d.draw(path);

        path = AnnularSector(45, 45, 100, 150);
        g2d.setColor(Color.lightGray);
        g2d.fill(path);
        g2d.setColor(Color.darkGray);
        g2d.draw(path);

        path = AnnularSector(0, 360, 0, 100);
        g2d.setColor(Color.lightGray);
        g2d.fill(path);
        g2d.setColor(Color.darkGray);
        g2d.draw(path);

        path = AnnularSector(-90, 120, 200, 220);
        g2d.setColor(Color.lightGray);
        g2d.fill(path);
        g2d.setColor(Color.darkGray);
        g2d.draw(path);

        int strWidth = (int) g2d.getFontMetrics().getStringBounds("Text", g2d).getWidth() + 10;

        for(int i = 0; i < 12; i++) {
        g2d.rotate(30 * Math.PI / 180);
        g2d.drawString("Test", -strWidth / 2, -120);
        }

        // Draws the overlay
        if(showHelpOverlay) {
        	g2d.drawImage(overlay, 5, 5, null);
        }
        
        g2d.dispose();
    }
	
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        bi = new BufferedImage(MainFrame.WIDTH, MainFrame.WIDTH, 
        		BufferedImage.TYPE_INT_ARGB); 
        drawMandala();
        g.drawImage(bi, 0, 0, null);
        g.dispose();

    }

    public BufferedImage getBI() {
    	return bi;
    }
    
    public boolean getShowHelpOverlay() {
    	return showHelpOverlay;
    }
    
    public void toggleHelpOverlay() {
    	showHelpOverlay = !showHelpOverlay;
    	
    	if(showHelpOverlay) {
    		showSelection = false;
    	} else {
    		showSelection = true;
    	}
    }
    
    public void setShowSelection(boolean showSelection) {
    	this.showSelection = showSelection;
    }

}
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
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.ufrn.musica.calendala.io.ResourceIO;
import br.ufrn.musica.calendala.mandala.Mandala;
import br.ufrn.musica.calendala.mandala.Ring;
import br.ufrn.musica.calendala.mandala.Slice;

/**
 * @author Felipe Cortez de Sá
 * @version 0.1
 * @since 0.1
 */

public class MandalaPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private final int DOC_MARGIN = 70;
	private BasicStroke mandalaStroke = new BasicStroke(1.5f);
	private int selectedBoundsX, selectedBoundsY;
	private BufferedImage bi, overlay;
	private JTextField editField;
	private boolean showHelpOverlay = false;
	private boolean showSelection = true;
	
    public MandalaPanel() {
    	
    	// Import the overlay image
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
        setLayout(null); // Allows absolute positioning of components
        
        editField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String finalText = editField.getText();
				if(finalText.equals(""))
					finalText = " ";
				
				Mandala.getInstance().getRings().get(Mandala.getInstance().
						getSelectedRing()).getSlices().get(Mandala.getInstance().
								getSelectedSlice()).setTitle(finalText);
				remove(editField);
				repaint();
			}
		});
    }
    
    public void editField() {
		editField.setBounds(selectedBoundsX - 31, selectedBoundsY - 2, 70, 20);
		add(editField);
		editField.setText(editField.getText());
		editField.selectAll();
		editField.grabFocus();
    }
    
	public void drawMandala() {
    	// Gets g2d from the BufferedImage
		Graphics2D g2d = bi.createGraphics();
		Composite original = g2d.getComposite();
		Composite translucentDarker = 
				AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);	
		Composite translucentBrighter = 
				AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.04f);	
        
        // Sets anti-alias on
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        
        // Paints the background white
		//g2d.setBackground(Color.white);        
		//g2d.clearRect(0, 0, WIDTH, WIDTH);
        
        g2d.setColor(Color.black);
        g2d.setStroke(mandalaStroke);
        
        int width = getWidth();
        int ringsNum = Mandala.getInstance().getRings().size();
        // Calculates the distance between the rings' borders
        int ringSize = (width - DOC_MARGIN) / ringsNum;
        
        // Iterates through the mandala's rings
        for(int i = 0; i < ringsNum; i++) {
        	Ring currentRing = Mandala.getInstance().getRings().get(i);
        	double slicesNum = currentRing.getSlices().size();
        	double offset = 360 % slicesNum;
        	
    		double arcRadius = (ringSize * (ringsNum - i)) / 2;
    		/*
    		if(offset != 0) {
    			System.out.println("Erro de desenho: " + (360 % slicesNum));
    			System.out.println((double) offset / slicesNum);
    			System.out.println((double) offset);
    		}
    		*/
    		
        	// Iterates through the slices of the ring that has index i
        	for(int j = 0; j < slicesNum; j++) {
        		Slice currentSlice = currentRing.getSlices().get(j);
        		Arc2D.Float arc = new Arc2D.Float();
    			
        		double aux = slicesNum - j;
        		double sliceAngle = (360 / slicesNum);
        		arc.setArcByCenter(width / 2, width / 2, arcRadius, 0, 0, Arc2D.PIE);
    			arc.setAngleStart((sliceAngle * aux) + 90);
    			arc.setAngleExtent((sliceAngle + (offset / slicesNum)) * -1);
    			
    			g2d.setColor(currentSlice.getColor());
        		g2d.fill(arc);
    			
    			if(Mandala.getInstance().getSelectedRing() == i && showSelection) {
						g2d.setColor(Color.gray);
						g2d.setComposite(translucentBrighter);
		        		g2d.fill(arc);
					if(Mandala.getInstance().getSelectedSlices().contains(j)) {
						g2d.setComposite(translucentDarker);
		        		g2d.fill(arc);
	    			}
	        		g2d.setComposite(original);
    			}
        		
        		// Finally, draw the slice
        		
    			// Draws the outline
    			g2d.setColor(currentSlice.getColor().darker());
        		g2d.draw(arc);
        	}
        }
        
        // Renders the text
        for(int i = 0; i < ringsNum; i++) {
        	Ring currentRing = Mandala.getInstance().getRings().get(i);
        	double slicesNum = currentRing.getSlices().size();
    		double textRadius = ((ringSize * (ringsNum - i)) / 2) - (ringSize / 2) / 2;
    		
        	for(int j = 0; j < slicesNum; j++) {
        		Slice currentSlice = currentRing.getSlices().get(j);
        		double sliceAngle = (360 / slicesNum);
        		double textAngle = (((sliceAngle * j) + (sliceAngle * (j + 1))) / 2) - 90;
    			int strWidth = 
    					(int) g2d.getFontMetrics()
    					.getStringBounds(currentSlice.getTitle(), g2d).getWidth();
    			double textX = ((textRadius * Math.cos(Math.toRadians(textAngle)))) - (strWidth / 2);
    			double textY = ((textRadius * Math.sin(Math.toRadians(textAngle))));
    			
    			g2d.setColor(Color.black);
    			g2d.drawString(currentSlice.getTitle(), (int) ((width / 2) + textX), (int) ((width / 2) + textY));
    			
    			if( Mandala.getInstance().getSelectedRing() == i &&
    				Mandala.getInstance().getSelectedSlice() == j) {
    				editField.setText(currentSlice.getTitle());
    				selectedBoundsX = (int) (((width / 2) + textX - 3) + (strWidth / 2));
    				selectedBoundsY = (int) ((width / 2) + textY - 12);
    			}
        	}
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
        //Does creating a new BI for every iteration make the performance worse?
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
package br.ufrn.musica.calendala.gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
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
import br.ufrn.musica.calendala.mandala.Ring.Direction;

/**
 * @author Felipe Cortez de Sá
 * @version 0.1
 * @since 0.1
 */

public class MandalaPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final int DOC_MARGIN = 50;
	private BasicStroke mandalaStroke = new BasicStroke(1.2f);
	//private Font inputFont = new Font(arg0, arg1, arg2)
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
				
				Mandala.getInstance().getSelectedSlice().setTitle(finalText);
				remove(editField);
				repaint();
			}
		});
        
    }
    
    public void actionPerformed(ActionEvent ae) {
    	repaint();
    }
    
    public void editField() {
    	if(Mandala.getInstance().getSelectionRange() == 0) {
			editField.setBounds((MainFrame.WIDTH / 2) - 95, 550 - 2, 200, 40);
			add(editField);
			String sliceTitle = Mandala.getInstance().getSelectedSlice().getTitle();
			if(sliceTitle.equals(" ")) sliceTitle = "";
			editField.setText(sliceTitle);
			editField.selectAll();
			editField.grabFocus();
    	}
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
				AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);	
		Composite translucentLight = 
				AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.12f);	
		Composite translucentLighter = 
				AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.04f);	
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        
        
        g2d.setColor(Color.black);
        g2d.setStroke(mandalaStroke);
        
        int width = getWidth();
        int ringsNum = Mandala.getInstance().getRings().size();
        float ringSize = (width - DOC_MARGIN) / ringsNum;

        g2d.translate(width / 2, width / 2);
        
        for(int i = 0; i < ringsNum; i++) {
        	Ring currentRing = Mandala.getInstance().getRings().get(i);
        	float arcRadius = (ringSize * (ringsNum - i)) / 2;
    		float divAngle = 360f / currentRing.getSubdivisions();
    		
    		int pos, cellExtent, drawnSlices;
    		drawnSlices = 0;
    		pos = currentRing.getSlices().get(0).getStart();
    		cellExtent = currentRing.getSlices().get(0).getMergeSize();
    		while(drawnSlices < currentRing.getSubdivisions()) {
	    		pos = currentRing.getSlices().get(pos).getStart();
	    		cellExtent = currentRing.getSlices().get(pos).getMergeSize(); 
	    		drawnSlices += cellExtent;

	    		// Cell shape
		        Path2D shape = AnnularSector(
		        		pos * divAngle, 
						cellExtent * divAngle, 
			        	arcRadius - (ringSize / 2),
			        	arcRadius + 2);
		        // Fill cell
        		g2d.setComposite(original);
        		g2d.setColor(Color.white);
		        g2d.fill(shape);
		        // Fill selected ring
	    		if(currentRing == Mandala.getInstance().getSelectedRing()) {
					g2d.setColor(Color.gray);
					g2d.setComposite(translucentLighter);
					g2d.fill(shape);
	    		}
		        // Draw cell outline
        		g2d.setComposite(translucentDark);
        		g2d.setColor(Color.darkGray);
        		g2d.draw(shape);

        		// Prepares to draw next cell
	    		pos = (pos + cellExtent) % currentRing.getSubdivisions();
    		}
    		if(currentRing == Mandala.getInstance().getSelectedRing()) {
				// Draw selection
    			int cellStart = Mandala.getInstance().getSelectionStart();
    			Direction orientation = 
    					(Mandala.getInstance().getSelectionRange() >= 0) ? Direction.CW : Direction.CCW;
    			int j = Math.abs(Mandala.getInstance().getSelectionRange());
    			
    			do {
	    			cellExtent = currentRing.getSlices().get(cellStart).getMergeSize();
			        Path2D shape = AnnularSector(
			        		cellStart * divAngle,
							cellExtent * divAngle,
				        	arcRadius - (ringSize / 2),
				        	arcRadius + 2);
					g2d.setColor(Color.gray);
					g2d.setComposite(translucentLight);
					g2d.fill(shape);

					if(orientation == Direction.CW) {
						cellStart = (cellStart + currentRing.getSlices().get(cellStart).getMergeSize())
								% currentRing.getSubdivisions();
					} else {
						cellStart -= 1;
						if(cellStart < 0) cellStart += currentRing.getSubdivisions();
						cellStart = currentRing.getSlices().get(cellStart).getStart();
					}

					j--;
    			} while(j >= 0);
    		}
        }

        g2d.setColor(Color.black);
        g2d.setComposite(original);

        for(int i = 0; i < ringsNum; i++) {
        	Ring currentRing = Mandala.getInstance().getRings().get(i);
        	float arcRadius = (ringSize * (ringsNum - i)) / 2;
    		float divAngle = ((float) Math.PI / 180) * 360f / currentRing.getSubdivisions();
	        float textAngle;
    		
    		int pos, cellExtent, drawnSlices;
    		drawnSlices = 0;
    		pos = currentRing.getSlices().get(0).getStart();
    		cellExtent = currentRing.getSlices().get(0).getMergeSize();
    		while(drawnSlices < currentRing.getSubdivisions()) {
	    		pos = currentRing.getSlices().get(pos).getStart();
	    		cellExtent = currentRing.getSlices().get(pos).getMergeSize(); 
	    		drawnSlices += cellExtent;

		        String text = currentRing.getSlices().get(pos).getTitle(); 
		        int strWidth = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
		        textAngle = divAngle * pos;
		        textAngle += (cellExtent * divAngle) / 2;
		        g2d.rotate(textAngle);
		        float textRadius = (((arcRadius - (ringSize / 2)) + arcRadius) / 2) - (g2d.getFontMetrics().getHeight() / 4);
		        g2d.drawString(text, -strWidth / 2, -textRadius);
		        g2d.rotate(-textAngle);

        		// Prepares to draw next cell
	    		pos = (pos + cellExtent) % currentRing.getSubdivisions();

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
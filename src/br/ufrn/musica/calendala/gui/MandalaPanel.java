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

/**
 * @author Felipe Cortez de Sá
 * @version 0.1
 * @since 0.1
 */

public class MandalaPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final int DOC_MARGIN = 50;
	private BasicStroke mandalaStroke = new BasicStroke(1.0f);
	private int selectedBoundsX, selectedBoundsY;
	private BufferedImage bi, overlay;
	private JTextField editField;
	private boolean showHelpOverlay = false;
	private boolean showSelection = true;
	
    public MandalaPanel() {
        
    	//In case I need to go back to action maps this serves as a reference
    	/*
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "pKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_G, 
    			InputEvent.SHIFT_DOWN_MASK), "shiftGKey");
    			
        getActionMap().put("pKey", 
        		MainController.getInstance().colorSelectionAction);
		*/
    				
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
    
	public void drawMandala() {
		Graphics2D g2d = bi.createGraphics();
		/*
		Composite original = g2d.getComposite();
		Composite translucentDarker = 
				AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);	
		Composite translucentDark = 
				AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);	
		Composite translucentBrighter = 
				AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.04f);	
		*/
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        
        g2d.setColor(Color.gray);
        g2d.setStroke(mandalaStroke);
        
        int width = getWidth();
        int ringsNum = Mandala.getInstance().getRings().size();
        int ringSize = (width - DOC_MARGIN) / ringsNum;
        
        for(int i = 0; i < ringsNum; i++) {
        	Ring currentRing = Mandala.getInstance().getRings().get(i);
    		double arcRadius = (ringSize * (ringsNum - i)) / 2;
			
			Arc2D.Double ringArc = new Arc2D.Double();
    		ringArc.setArcByCenter(width / 2, width / 2, arcRadius, 90, 0, Arc2D.PIE);
    		double divAngle = 360 / currentRing.getSubdivisions();
    		
        	for(int j = 0; j < currentRing.getSubdivisions(); j++) {
        		ringArc.start -= divAngle;
        		ringArc.setAngleExtent(divAngle);
        		g2d.draw(ringArc);
    		}
        }
        
        // Renders the text
        for(int i = 0; i < ringsNum; i++) {
        	// See original code on GitHub
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
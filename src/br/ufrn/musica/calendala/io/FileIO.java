package br.ufrn.musica.calendala.io;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import br.ufrn.musica.calendala.gui.FacadeSwing;
import br.ufrn.musica.calendala.mandala.Mandala;
import br.ufrn.musica.calendala.mandala.Ring;
import br.ufrn.musica.calendala.mandala.Slice;

/**
 * @author Felipe Cortez de Sá
 * @version 0.1
 * @since 0.1
 */

public class FileIO {
	 
	public enum Extension {PNG, CDL}
	
	public static void openFile(File file) {
		if(file.isFile()) {
			try {
				/* References
				http://www.vogella.com/articles/JavaXML/article.html
				http://www.xml.com/pub/a/2003/09/17/stax.html?page=2
				*/
				
				XMLInputFactory inputFactory = XMLInputFactory.newInstance();
				InputStream in = new FileInputStream(file);
				XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
				
				Mandala mandala = new Mandala();
				int selectedRing = 0, selectedSlice = 0;
				String title = null;
				ArrayList<Ring> rings = new ArrayList<Ring>();
				Ring ring = null;
				Slice slice = null;
				
			    while (eventReader.hasNext()) {
			    	XMLEvent event = eventReader.nextEvent();
					if(event.isStartElement()) {
						StartElement startElement = event.asStartElement();
						
						if(startElement.getName().getLocalPart().equals("mandala")) {
							@SuppressWarnings("unchecked")
							Iterator<Attribute> attributes = startElement.getAttributes();
							while (attributes.hasNext()) {
								Attribute attribute = attributes.next();
								if(attribute.getName().toString().equals("title")) {
									title = attribute.getValue();
								} else if(attribute.getName().toString().equals("selRing")) {
									selectedRing = Integer.parseInt(attribute.getValue());
								} else if(attribute.getName().toString().equals("selSlice")) {
									selectedSlice = Integer.parseInt(attribute.getValue());
								}
							}
							continue;
						} else if(startElement.getName().getLocalPart().equals("ring")) {
							event = eventReader.nextEvent();
							ring = new Ring();
							continue;
						} else if(startElement.getName().getLocalPart().equals("cell")) {
							event = eventReader.nextEvent();
							//slice = new Slice();
							
							@SuppressWarnings("unchecked")
							Iterator<Attribute> attributes = startElement.getAttributes();
							while (attributes.hasNext()) {
								Attribute attribute = attributes.next();
								if(attribute.getName().toString().equals("color")) {
									String color = "#" + attribute.getValue();
									slice.setColor(Color.decode(color));
								}
							}
							
							slice.setTitle(event.asCharacters().getData());
							continue;
						}
					}
					
					if(event.isEndElement()) {
						EndElement endElement = event.asEndElement();
						
						if(endElement.getName().getLocalPart().equals("mandala")) {
							mandala.setRings(rings);
							mandala.setTitle(title);
							//mandala.setSelectedRing(selectedRing);
							//mandala.setSelectedSlice(selectedSlice);
							mandala.setMandala(mandala);
							continue;
						} else if(endElement.getName().getLocalPart().equals("ring")) {
							rings.add(ring);
							continue;
						} else if(endElement.getName().getLocalPart().equals("cell")) {
							//ring.getSlices().add(slice);
							continue;
						}
					}
				}
			    
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
		}
}
	 
	 public static void saveFile(File file, Extension ext) {
		 
		if(ext == Extension.CDL) {
			try {
				/* References
				http://www.vogella.com/articles/JavaXML/article.html
				http://www.xml.com/pub/a/2003/09/17/stax.html?page=2
				*/
			
				XMLOutputFactory factory = XMLOutputFactory.newInstance();
				OutputStream out = new FileOutputStream(file);
				XMLStreamWriter writer = factory.createXMLStreamWriter(out, "ISO-8859-1");
				
				writer.writeStartDocument("ISO-8859-1", "1.0");
				writer.writeCharacters("\n");
				writer.writeStartElement("mandala");
				writer.writeAttribute("title", Mandala.getInstance().getTitle());
				/*writer.writeAttribute("selRing", 
						Integer.toString(Mandala.getInstance().getSelectedRing()));
				writer.writeAttribute("selSlice", 
						Integer.toString(Mandala.getInstance().getSelectedSlice()));
				writer.writeCharacters("\n");
				*/
				
				for(Ring r : Mandala.getInstance().getRings()) {
					writer.writeStartElement("ring");
					writer.writeCharacters("\n");
					/*for(Slice s : r.getSlices()) {
						writer.writeStartElement("cell");
						String rgb = Integer.toHexString(s.getColor().getRGB());
						rgb = rgb.substring(2, rgb.length());
						writer.writeAttribute("color", rgb);
						writer.writeCharacters(s.getTitle());
						writer.writeEndElement();
						writer.writeCharacters("\n");
					}*/
					writer.writeEndElement();
					writer.writeCharacters("\n");
				}
				
				writer.writeEndElement();
				writer.writeEndDocument();
				
				writer.flush();
				writer.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
		} else if(ext == Extension.PNG) {
			try {
				BufferedImage bi = FacadeSwing.singleton().getMandalaPanel().getBI();
				ImageIO.write(bi, "png", file); 
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	 } 
}
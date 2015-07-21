package com.john.server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.JFrame;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

class PPTXToImage extends JFrame{	
	
	    public int ConvertToImage(String path) throws Exception {
	        FileInputStream is = new FileInputStream(path);
	        XMLSlideShow ppt = new XMLSlideShow(is);
	        is.close();

	        double zoom = 2; // magnify it by 2
	        AffineTransform at = new AffineTransform();
	        at.setToScale(zoom, zoom);

	        Dimension pgsize = ppt.getPageSize();

	        XSLFSlide[] slide = ppt.getSlides();
	        for (int i = 0; i < slide.length; i++) {
	            BufferedImage img = new BufferedImage((int)Math.ceil(pgsize.width*zoom), (int)Math.ceil(pgsize.height*zoom), BufferedImage.TYPE_INT_RGB);
	            Graphics2D graphics = img.createGraphics();
	            graphics.setTransform(at);

	            graphics.setPaint(Color.white);
	            graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
	            slide[i].draw(graphics);
	            FileOutputStream out = new FileOutputStream( "C:\\Data\\Image\\"  + (i + 1) + ".png");
	            javax.imageio.ImageIO.write(img, "png", out);
	            out.close();
	        }
	        
	        return slide.length;
	    }
}



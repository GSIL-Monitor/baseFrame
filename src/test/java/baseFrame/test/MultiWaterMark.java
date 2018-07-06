package baseFrame.test;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class MultiWaterMark {
	private static final String MARK_TEXT = "旺仔爱左左";
	private static final String FONT_NAME = "微软雅黑";
	private static final int FONT_STYLE = Font.BOLD;
	private static final int FONT_SIZE = 60;
	private static final Color FONT_COLOR = Color.BLUE;

	private static final int X = 10;// 水映横坐标
	private static final int Y = 10;// 水映纵坐标

	private static final float ALPHA = 0.3f;
	private static final int DISTANCE = 200;//每个文字水映间隔
	private static final int RADIANS = 30;//倾斜弧度
	//private static final String UPLOAD_PATH = "images";//每个文字水映间隔

	/**
	 * 给图片添加重复多行文字水映
	 * 
	 * @param image //图片文件
	 * @param fileName //图片文件名称
	 * @param uploadPath //上传相对路径
	 * @param realUploadPath //上传绝对物理路径
	 * @return //图片相对路径
	 * @throws Exception 
	 */
	@SuppressWarnings("restriction")
	public String watermark(File image, String fileName, String uploadPath, String realUploadPath) throws Exception {
		String logoFileName = "logo_" + fileName;
		OutputStream os = null;
		Image image2 = ImageIO.read(image);
		int width=image2.getWidth(null);
		int height = image2.getHeight(null);
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		g.drawImage(image2, 0, 0, width, height, null);
		g.setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
		g.setColor(FONT_COLOR);
		//获取文字水映的宽高
		int width1= FONT_SIZE * getTextLength(MARK_TEXT);
		int height1 = FONT_SIZE;
//		int widthDiff = width - width1;
//		int heightDiff = height - height1;
//		int x = X;
//		int y= Y;
//		if(x>widthDiff) x = widthDiff;
//		if(y>heightDiff) y=heightDiff;
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, ALPHA));
		g.rotate(Math.toRadians(RADIANS), bi.getWidth()/2, bi.getHeight()/2);
		
		int x = -width/2;
		int y= -height/2;
		while(x<width*1.5){
			y = -height/2;
			while(y<height*1.5){
				g.drawString(MARK_TEXT, x, y);
				y += height1 + DISTANCE;
			}
			x += width1 + DISTANCE;
		}
		g.dispose();
		os = new FileOutputStream(realUploadPath + "/" + logoFileName);
		JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
		en.encode(bi);
		return uploadPath + "/" + logoFileName;
	}

	private int getTextLength(String text) {
		int length = text.length();
		for (int i = 0; i < text.length(); i++) {
			String s = String.valueOf(text.charAt(i));
			if (s.getBytes().length > 1) {// 汉字
				length++;
			}
		}
		length = (length % 2 == 0) ? length / 2 : length / 2 + 1;
		return length;
	}
}

package com.zeno.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 生成验证码
 * 
 * @author Light
 *
 */
public class VerificationCode {
	/**
	 * 默认用于填充验证码背景的rgb值ֵ
	 */
	private static final int DEFAULT_COLOR_RED = 246, DEFAULT_COLOR_GREEN = 240, DEFAULT_COLOR_BLUE = 250,
			DEFAULT_COLOR_MAX = 220;
	/**
	 * 验证码元素
	 */
	final private char[] code = { '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
			'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	/**
	 * 验证码字体
	 */
	final private String[] fontNames = new String[] { "����", "����", "Courier", "Arial", "Verdana", "Times", "Tahoma",
			"Georgia" };
	/**
	 * 验证码字体的样式
	 */
	final private int[] fontStyles = new int[] { Font.BOLD, Font.ITALIC | Font.BOLD };
	/**
	 * 验证码长度
	 * 默认4个字符
	 */
	private int vcLength = 4;
	/**
	 * 验证码字号 默认17
	 */
	private int fontsize = 21;
	/**
	 * 验证码图片的宽度
	 */
	private int width = (fontsize + 1) * vcLength + 10;
	/**
	 * 验证码图片的高度
	 */
	private int height = fontsize + 12;
	/**
	 * 干绕线条数 默认3条
	 */
	private int disturbLine = 3;

	public VerificationCode() {
	}

	public VerificationCode(int vcLength) {
		this.vcLength = vcLength;
		this.width = (fontsize + 1) * vcLength + 10;
	}

	public BufferedImage generateVCode(String vcode, boolean drawline) {
		// 创建验证码图片
		BufferedImage vcImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics grapics = vcImage.getGraphics();
		grapics.fillRect(0, 0, width, height);
		if (drawline) {

		}

		Random random = new Random();

		for (int i = 0; i < vcode.length(); i++) {
			grapics.setFont(new Font(fontNames[random.nextInt(fontNames.length)],
					fontStyles[random.nextInt(fontStyles.length)], fontsize));
			grapics.setColor(getRandomColor());
			grapics.drawString(vcode.charAt(i) + "", i * fontsize + 10, fontsize + 5);
		}

		grapics.dispose();

		return vcImage;
	}

	/**
	 * 获得旋转字体的验证码图片
	 * 
	 * @param vcode
	 * @param drawline
	 *            是否画干扰线
	 * @return
	 */
	public BufferedImage generatorRotateVCodeImage(String vcode, boolean drawline) {
		// 创建验证码图片
		BufferedImage rotateVcodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = rotateVcodeImage.createGraphics();
		// 填充背景色
		g2d.setColor(new Color(DEFAULT_COLOR_RED, DEFAULT_COLOR_GREEN, DEFAULT_COLOR_BLUE));
		g2d.fillRect(0, 0, width, height);
		if (drawline) {
			drawDisturbLine(g2d);
		}
		// 在图片上画验证码
		for (int i = 0; i < vcode.length(); i++) {
			BufferedImage rotateImage = getRotateImage(vcode.charAt(i));
			g2d.drawImage(rotateImage, null, (int) (this.height * 0.7) * i, 0);
		}
		g2d.dispose();
		return rotateVcodeImage;
	}

	/**
	 * 生成验证码
	 * 
	 * @return
	 */
	public String generatorVCode() {
		int len = code.length;
		Random ran = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < vcLength; i++) {
			int index = ran.nextInt(len);
			sb.append(code[index]);
		}
		return sb.toString();
	}

	/**
	 * 为验证码图片画一些干扰线
	 * 
	 * @param g
	 */
	private void drawDisturbLine(Graphics g) {
		Random ran = new Random();
		for (int i = 0; i < disturbLine; i++) {
			int x1 = ran.nextInt(width);
			int y1 = ran.nextInt(height);
			int x2 = ran.nextInt(width);
			int y2 = ran.nextInt(height);
			g.setColor(getRandomColor());
			// ��������
			g.drawLine(x1, y1, x2, y2);
		}
	}

	/**
	 * 获取一张旋转的图片
	 * 
	 * @param c
	 *            要画的字符
	 * @return
	 */
	private BufferedImage getRotateImage(char c) {
		BufferedImage rotateImage = new BufferedImage(height, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = rotateImage.createGraphics();
		// 设置透明度为0
		g2d.setColor(new Color(255, 255, 255, 0));
		g2d.fillRect(0, 0, height, height);
		Random ran = new Random();
		g2d.setFont(new Font(fontNames[ran.nextInt(fontNames.length)], fontStyles[ran.nextInt(fontStyles.length)],
				fontsize));
		g2d.setColor(getRandomColor());
		double theta = getTheta();
		// 旋转图片
		g2d.rotate(theta, height / 2, height / 2);
		g2d.drawString(Character.toString(c), (height - fontsize) / 2, fontsize + 5);
		g2d.dispose();

		return rotateImage;
	}

	/**
	 * @return 返回一个随机颜色
	 */
	private Color getRandomColor() {
		Random ran = new Random();
		return new Color(ran.nextInt(DEFAULT_COLOR_MAX), ran.nextInt(DEFAULT_COLOR_MAX),
				ran.nextInt(DEFAULT_COLOR_MAX));
	}

	/**
	 * @return 角度
	 */
	private double getTheta() {
		return ((int) (Math.random() * 1000) % 2 == 0 ? -1 : 1) * Math.random();
	}

	public int getVcLength() {
		return vcLength;
	}

	public void setVcLength(int vcLength) {
		this.vcLength = vcLength;
	}

	public int getFontsize() {
		return fontsize;
	}

	public void setFontsize(int fontsize) {
		this.fontsize = fontsize;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getDisturbLine() {
		return disturbLine;
	}

	public void setDisturbLine(int disturbLine) {
		this.disturbLine = disturbLine;
	}

	public char[] getCode() {
		return code;
	}

	public String[] getFontNames() {
		return fontNames;
	}

	public int[] getFontStyles() {
		return fontStyles;
	}

}

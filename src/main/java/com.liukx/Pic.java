package com.liukx;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by liukx on 2018-12-13.
 */
public class Pic {

    public static void main(String[] args) throws IOException{
        String srcPath ="E:\\idCard识别\\2018041617202975502360.jpg";
//        String srcPath ="E:\\idCard识别\\png11 21.1- 副本.png";
//        String srcPath ="E:\\idCard识别\\BMP  11.3.bmp";

        File file = new File("E:\\idCard识别\\");
        File descFile = new File(file,"test-"+ UUID.randomUUID().toString()+".jpg");
        String descPath =descFile.getAbsolutePath();

        int width = getPixel(srcPath);
        //使用相同分辨率,生成的图片从感觉上,清晰度是一样的,但是图片要小很多,具体原因不清楚,以后有空再研究
        //不过用来压缩图片不错
        resizeImage(srcPath,descPath,(int) ((float)width*1));
        int width1 = getPixel(descPath);
    }



    /**
     * 功能：获取图片像素
     * * @param filePath 图片路径
     */
    public static int getPixel(String filePath){
        File file = new File(filePath);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int width = bi.getWidth(); // 像素
        int height = bi.getHeight(); // 像素
        System.out.println("width=" + width + ",height=" + height + ".");
        return width;
    }

    /**
     * @param srcPath  源图片路径
     * @param desPath  修改大小后图片路径
     * @param scaleSize 图片的修改比例，目标宽度
     */
    public static void resizeImage(String srcPath, String desPath,int scaleSize) throws IOException {

        File srcFile = new File(srcPath);
        Image srcImg = ImageIO.read(srcFile);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(srcFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        float width = bi.getWidth(); // 像素
        float height = bi.getHeight(); // 像素
        float scale=width/scaleSize;
        BufferedImage buffImg = null;
        buffImg = new BufferedImage(scaleSize, (int)(height/scale), BufferedImage.TYPE_INT_RGB);
        //使用TYPE_INT_RGB修改的图片会变色
        buffImg.getGraphics().drawImage(
                srcImg.getScaledInstance(scaleSize, (int)(height/scale), Image.SCALE_SMOOTH), 0,
                0, null);

        ImageIO.write(buffImg, "JPEG", new File(desPath));
    }

    /**
     * @param srcPath  源图片文件夹路径
     * @param desPath  修改大小后图片文件夹路径
     * @param scaleSize 图片的修改比例，目标宽度
     */
    public static void getFiles(String path,String modPath,int scaleSize) throws IOException {
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();
        //循环读取目录下图片
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                System.out.println("文件：" + tempList[i].getName()+"-"+tempList[i].getAbsolutePath().replaceAll("\\\\","/"));
                String[] imagePath=tempList[i].getAbsolutePath().replaceAll("\\\\","/").split("/");
                String imageNumber=null;
                resizeImage(tempList[i].getAbsolutePath().replaceAll("\\\\","/"),"目标文件路径",scaleSize);
                files.add(tempList[i].toString());
            }
            if (tempList[i].isDirectory()) {
//                  System.out.println("文件夹：" + tempList[i]);
            }
        }
        System.out.println(path+"下文件数量："+files.size());
    }




}

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.io.File;

class Main {
  public static void main(String[] args) {
    int W = 200;
    int H = 200;
    double[][] image = new double[W][H];

    double fov = Math.PI/3.;
    double max_d = 100;

    double[] a = new double[]{ 1, 0, 0 };
    double[] b = new double[]{ 0, 0, 0 };

    double[] lightSrc = new double[]{ 0, 0, 0 };

    double[] O = new double[]{ 20, 0, 0 };
    double[] ox = new double[]{ 0, 1, 0 };
    double[] oy = new double[]{ 0, 0, 1 };
    double R = 8;
    double r = 2;
    Torus torus = new Torus(O, ox, oy, R, r);

    double total = 0;

    for(int t=0; t<100; t++) {
      double[][] rot = rotationMatrix(Math.PI/5, 2.*t/9.*Math.PI);
      torus.ox = dot(rot, ox);
      torus.oy = dot(rot, oy);

      long startTime = System.currentTimeMillis();

      for(int x=0; x<W; x++)
        for(int y=0; y<H; y++) {
          double[] lookingAt = dot(rotationMatrix(-fov/2+y*fov/H, -fov/2+x*fov/W), a);
          double[] p1 = new double[]{ b[0], b[1], b[2] };
          double d = 0;
          for(int i=0; i<100; i++) {
            double d1 = torus.dist(p1);
            d += d1;
            if(d >= max_d) {
              d = max_d;
              break;
            } else if(d1 < max_d*.001) {
              break;
            }
            p1[0] += lookingAt[0] * d1;
            p1[1] += lookingAt[1] * d1;
            p1[2] += lookingAt[2] * d1;
          }
          if(d < max_d) {
            image[x][y] = torus.getLightLevel(p1, lightSrc) - d/max_d;
            if(image[x][y] < 0)
              image[x][y] = 0;
          } else {
            image[x][y] = 1.-d/max_d;
          }
        }

      long endTime = System.currentTimeMillis();
      System.out.println(endTime-startTime);
      total += endTime-startTime;
      saveGrayImage(image, "outputs/"+t+".png");
    }
    System.out.println(total / 100);
  }
  private static double[][] rotationMatrix(double alpha, double beta) {
    double cosa = Math.cos(alpha);
    double sina = Math.sin(alpha);
    double cosb = Math.cos(beta);
    double sinb = Math.sin(beta);
    return new double[][]{
      { cosa*cosb, -sina*cosb, -sinb },
      { sina, cosa, 0 },
      { cosa*sinb, -sina*sinb, cosb }
    };
  }
  private static double[] dot(double[][] a, double[] b) {
    double[] b1 = new double[a[0].length];
    for(int i=0; i<a[0].length; i++)
      for(int x=0; x<a.length; x++)
        b1[i] += a[x][i] * b[x];
    return b1;
  }
  public static void saveGrayImage(double[][] a, String filename) {
    BufferedImage image = new BufferedImage(a.length, a[0].length, BufferedImage.TYPE_INT_RGB);
    for (int x=0; x<a.length; x++) {
      for (int y=0; y<a[0].length; y++) {
        int r = (int) (a[x][y]*255);
        int g = (int) (a[x][y]*255);
        int b = (int) (a[x][y]*255);
        image.setRGB(x, y, (r << 16) + (g << 8) + b );
      }
    }

    File f = new File(filename);
    try {
      ImageIO.write(image, "png", f);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

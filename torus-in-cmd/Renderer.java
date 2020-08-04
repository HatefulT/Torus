class Renderer {
  int W;
  int H;

  Torus torus;

  String std;
  String spacer;

  double fov = Math.PI/3.;
  double max_d = 100;

  double[] b = new double[]{ 0, 0, 0 };

  Renderer(int _w, int _h) {
    std = " ";
    spacer = "";
    for(int i=0; i<30; i++)
      spacer += "\n";
    W = _w;
    H = _h;
  }
  void clearConsole() {
    System.out.println(spacer);
  }
  void render(Torus torus, double[] lightSrc, double[] lookingAt) {
    clearConsole();
    String frame = "";
    for(int x=0; x<W+2; x++)
      frame += std;
    frame += "\n";

    for(int y=0; y<H; y++) {
      for(int x=0; x<W; x++) {
        double[] a = dot(rotationMatrix(-fov/2+y*fov/H, -fov/2+x*fov/W), lookingAt);
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
          p1[0] += a[0] * d1;
          p1[1] += a[1] * d1;
          p1[2] += a[2] * d1;
        }
        if(d < max_d) {
          double l = torus.getLightLevel(p1, lightSrc) - d/max_d;
          String lightSyms = ".,-~:;=!*#$@";
          if(l < 0)
            frame += std;
          else
            frame += lightSyms.charAt((int)Math.floor(l * lightSyms.length()));
        } else
          frame += std;
      }
      frame += "\n";
    }
    System.out.println(frame);
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
}

class Main {
  private static Torus torus = new Torus(new double[]{ 20, 0, 0 }, new double[]{ 0, 1, 0 }, new double[]{ 0, 0, 1 }, 8, 3);

  public static void main(String[] args) throws InterruptedException {
    Renderer r = new Renderer(50, 25);

    double[] ox = new double[]{ 0, 1, 0 };
    double[] oy = new double[]{ 0, 0, 1 };

    double t = 0;

    double[] lightSrc = new double[]{ 0, 0, 0 };
    double[] lookingAt = new double[]{ 1, 0, 0 };
    while(true) {
      double[][] rot = rotationMatrix(Math.PI/5, 2.*t/9.*Math.PI);
      torus.ox = dot(rot, ox);
      torus.oy = dot(rot, oy);

      double al = Math.PI/2; // +Math.sin(t)/4
      lookingAt = new double[]{ Math.sin(al), 0, Math.cos(al) };
      // lightSrc = new double[]{ -1, 0, Math.sin(t)*10 };

      r.render(torus, lightSrc, lookingAt);
      Thread.sleep(100);
      t += 0.2;
    }
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

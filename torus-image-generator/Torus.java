class Torus {
  double[] O;
  double[] ox;
  double[] oy;
  double R;
  double r;

  Torus(double[] o1, double[] ox1, double[] oy1, double R1, double r1) {
    O = o1;
    ox = ox1;
    oy = oy1;
    R = R1;
    r = r1;
  }
  public double dist(double[] P) {
    double POx = O[0] - P[0];
    double POy = O[1] - P[1];
    double POz = O[2] - P[2];
    double POmagsq = POx*POx+POy*POy+POz*POz;

    double POprojx = POx*ox[0] + POy*ox[1] + POz*ox[2];
    double POprojy = POx*oy[0] + POy*oy[1] + POz*oy[2];
    double t = POprojx*POprojx + POprojy*POprojy;
    POz = POmagsq - t;

    double D = Math.sqrt(t) - R;
    double D1 = Math.sqrt(D*D + POz) - r;

    return D1;
  }

  public double getLightLevel(double[] P, double[] lightSrc) {
    double POx = O[0] - P[0];
    double POy = O[1] - P[1];
    double POz = O[2] - P[2];

    double tx = POx*ox[0] + POy*ox[1] + POz*ox[2];
    double ty = POx*oy[0] + POy*oy[1] + POz*oy[2];
    double tm = Math.sqrt(tx*tx+ty*ty);

    tx = tx*(-R)/tm;
    ty = ty*(-R)/tm;

    double pax = POx + ox[0]*tx+oy[0]*ty;
    double pay = POy + ox[1]*tx+oy[1]*ty;
    double paz = POz + ox[2]*tx+oy[2]*ty;
    double pamag = Math.sqrt(pax*pax + pay*pay + paz*paz);
    pax = pax/pamag;
    pay = pay/pamag;
    paz = paz/pamag;

    POx = P[0] - lightSrc[0];
    POy = P[1] - lightSrc[1];
    POz = P[2] - lightSrc[2];
    tm = Math.sqrt(POx*POx + POy*POy + POz*POz);
    POx = POx / tm;
    POy = POy / tm;
    POz = POz / tm;

    double l = POx*pax + POy*pay + POz*paz;
    if( l < 0 )
      return 0;
    return l;
  }
}

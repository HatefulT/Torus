# Torus
Finally, I made ray tracing torus.
I made two versions of torus: image generator and command-line version.
In both directories I made start.bat to launch.

# Ray tracing
In 3D computer graphics, ray tracing is a rendering technique for generating an image by tracing the path of light as pixels in an image plane and simulating the effects of its encounters with virtual objects.
[Wikipedia](https://en.wikipedia.org/wiki/Ray_tracing_(graphics))

# Distance from point to torus
I don't know if this is fastest way to compute distance to torus.

First, I defined torus as it's origin point (vector 3d),
two normalized vectors ox and oy that create torus plane
and two numbers R and r denotes radii.

Check out dist-to-torus.png file probably you will understand what does it mean (I don't have enough time to explain how I made distance to torus algorithm).

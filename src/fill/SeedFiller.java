package fill;

import rasterize.Raster;

public class SeedFiller implements Filler {

    private Raster raster;
    private int x, y;
    private int fillColor, backgroundColor;

    public SeedFiller(Raster raster, int x, int y, int fillColor, int backgroundColor) {
        this.raster = raster;
        this.x = x;
        this.y = y;
        this.fillColor = fillColor;
        this.backgroundColor = backgroundColor;
    }

    @Override
    public void fill() {
        seedFill(x, y);
    }

    private void seedFill(int x, int y) {
        // 1. načtu barvu z pozice x, y
        int pixelColor = raster.getPixel(x, y);

        // 2. porovnáme načtenou barvu s barvou pozadí.
        // Pokud se barvy rovnají, tak pokračuji dál
        if(pixelColor != backgroundColor)
            return;

        // 3. obarvím pixel na pozici x, y
        raster.setPixel(x, y, fillColor);

        // 4. zavolám seedFill pro 4 sousedy
        seedFill(x + 1, y);
        seedFill(x - 1, y);
        seedFill(x, y - 1);
        seedFill(x, y + 1);

    }
}

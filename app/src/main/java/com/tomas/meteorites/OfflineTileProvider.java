package com.tomas.meteorites;

import android.content.res.AssetManager;

import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;

/**
 * Created by Tomas on 8. 9. 2017.
 */

public class OfflineTileProvider implements TileProvider {
    private static final int TILE_WIDTH = 256;
    private static final int TILE_HEIGHT = 256;
    private static final int BUFFER_SIZE = 16 * 1024;

    private AssetManager assets;

    public OfflineTileProvider(AssetManager assets){
        this.assets = assets;
    }
    @Override
    public Tile getTile(int x, int y, int zoom) {
        return null;
    }
}

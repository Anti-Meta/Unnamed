package nl.antimeta.unnamed.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;

public class CameraUtil {
    public static PerspectiveCamera createPlayerCamera(float fieldOfView){
        PerspectiveCamera camera = new PerspectiveCamera(fieldOfView, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(10f, 10f, 10f);
        camera.lookAt(0,0,0);
        camera.near = 1f;
        camera.far = 300f;
        camera.update();
        return camera;
    }
}

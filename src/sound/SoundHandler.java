package sound;

import engine.MainGameLoop;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.io.InputStream;
import java.util.HashMap;

public class SoundHandler {

    public static HashMap<String, Sound> sounds = new HashMap<>();

    public static void playSound(String soundName) {
        InputStream s = MainGameLoop.class.getResourceAsStream(soundName);
        try {
            Sound as;
            if (sounds.containsKey(soundName)) {
                as = sounds.get(soundName);
            } else {
                as = new Sound(s, soundName);
                sounds.put(soundName, as);
            }
            as.play();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static void loopSound(String soundName) {
        InputStream s = MainGameLoop.class.getResourceAsStream(soundName);
        try {
            Sound as;
            if (sounds.containsKey(soundName)) {
                as = sounds.get(soundName);
            } else {
                as = new Sound(s, soundName);
                sounds.put(soundName, as);
            }
            as.loop();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static void stopSound(String soundName) {
        InputStream s = MainGameLoop.class.getResourceAsStream(soundName);
        try {
            Sound as;
            if (sounds.containsKey(soundName)) {
                as = sounds.get(soundName);
            } else {
                as = new Sound(s, soundName);
                sounds.put(soundName, as);
            }
            as.stop();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

}

package sound;

import engine.MainGameLoop;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import java.io.InputStream;
import java.util.HashMap;

public class MusicHandler {

    public static HashMap<String, Music> music = new HashMap<>();

    public static void play(String track) {
        try {
            if (music.containsKey(track)) {
                music.get(track).play();
            } else {
                Music music = new Music(MainGameLoop.class.getResourceAsStream(track), track);
                music.play();
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static void loop(String track) {
        try {
            if (music.containsKey(track)) {
                music.get(track).loop();
            } else {
                Music music = new Music(MainGameLoop.class.getResourceAsStream(track), track);
                music.loop();
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static void stopMusic(String musicName) {
        InputStream s = MainGameLoop.class.getResourceAsStream(musicName);
        try {
            Music as;
            if (music.containsKey(musicName)) {
                as = music.get(musicName);
            } else {
                as = new Music(s, musicName);
                music.put(musicName, as);
            }
            as.stop();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}

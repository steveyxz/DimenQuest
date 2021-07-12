package classes;

import java.util.List;

public class ListLooper<T> {

    private List<T> list;
    private int on = 0;

    public ListLooper(List<T> list) {
        this.list = list;
    }

    public T next() {
        int get = on;
        on++;
        if (on > list.size() - 1) {
            on = 0;
        }
        return list.get(get);
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getOn() {
        return on;
    }

    public void setOn(int on) {
        this.on = on;
    }
}

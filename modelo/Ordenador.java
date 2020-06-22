package ejercicio2.modelo;

public class Ordenador {
    private int id;
    private int ram;
    private int ssd;
    private int pantalla;

    public Ordenador(int id, int ram, int ssd, int pantalla) {
        this.id = id;
        this.ram = ram;
        this.ssd = ssd;
        this.pantalla = pantalla;
    }

    public Ordenador(int ram, int ssd, int pantalla) {
        this.ram = ram;
        this.ssd = ssd;
        this.pantalla = pantalla;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getSsd() {
        return ssd;
    }

    public void setSsd(int ssd) {
        this.ssd = ssd;
    }

    public int getPantalla() {
        return pantalla;
    }

    public void setPantalla(int pantalla) {
        this.pantalla = pantalla;
    }

    @Override
    public String toString() {
        return String.format("id: %3d   ram: %2d    ssd: %5d     pantalla: %3d", id, ram, ssd, pantalla);
    }
}

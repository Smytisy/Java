package recognition;

public class Neuron {
    private int[] a = new int[10];
    private int[] w = new int[10];

    /**
     * Устанавливает значение узла
     * @param i Индекс узла
     * @param value Значение, которое будет хранить узел
     */
    public void set_A(int i, int value) { // Устанавливает значение @param
        this.a[i] = value;
    }

    /**
     * Устанавливает значение ребра
     * @param i Индекс узла
     * @param value Значение, которое будет хранить ребро
     */
    public void set_W(int i, int value) {
        this.w[i] = value;
    }

    /**
     * Устанавливает значение сразу для девяти усзлов
     */
    public void set_A(int v0, int v1, int v2, int v3, int v4, int v5, int v6, int v7, int v8) {
        this.a[0] = v0;
        this.a[1] = v1;
        this.a[2] = v2;
        this.a[3] = v3;
        this.a[4] = v4;
        this.a[5] = v5;
        this.a[6] = v6;
        this.a[7] = v7;
        this.a[8] = v8;

    }

    /**
     * Определяет какое число нарисовано в сетке 3х3
     * @return Возвращает 0 или 1
     */
    public int findNumber_1_or_0() {
        for (int i = 0; i < 9; i++) {
            a[9] += a[i] * w[i];
            //System.out.println(a[9]);
        }
        a[9] += w[9];
        if (a[9] <= 0) {
            return 1;
        }
        return 0;
    }

    /**
     * Автоматически устанавливает вес для ребер для
     * распознования единицы или нуля в сетке 3х3
     */
    public void setWeightNeuron() {
        this.set_W(0, 2);
        this.set_W(1, 1);
        this.set_W(2, 2);
        this.set_W(3, 4);
        this.set_W(4, -4);
        this.set_W(5, 4);
        this.set_W(6, 2);
        this.set_W(7, -1);
        this.set_W(8, 2);

        this.set_W(9, -5);
    }
}
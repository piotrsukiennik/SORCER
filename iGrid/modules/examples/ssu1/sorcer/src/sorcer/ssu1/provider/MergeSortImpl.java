package sorcer.ssu1.provider;

public class MergeSortImpl implements Sort {

    private int[] values;
    private int[] tmp;

    public int[] sort(int[] base)  throws SortException {
        this.values = base;
        this.tmp = new int[base.length];
        mergeSort(0, base.length - 1);
        return values;
    }

    private void mergeSort(int low, int high) {
        if (low < high) {
            int middle = low + (high - low) / 2;
            mergeSort(low, middle);
            mergeSort(middle + 1, high);
            merge(low, middle, high);
        }
    }

    private void merge(int low, int middle, int high) {
        for (int i = low; i <= high; i++) {
            tmp[i] = values[i];
        }

        int i = low;
        int j = middle + 1;
        int k = low;

        while (i <= middle && j <= high) {
            if (tmp[i] <= tmp[j]) {
                values[k] = tmp[i];
                i++;
            } else {
                values[k] = tmp[j];
                j++;
            }
            k++;
        }

        while (i <= middle) {
            values[k] = tmp[i];
            k++;
            i++;
        }

    }
}

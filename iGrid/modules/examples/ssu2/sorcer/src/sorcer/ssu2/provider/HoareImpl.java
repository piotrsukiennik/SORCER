package sorcer.ssu2.provider;

public class HoareImpl implements Hoare {

    public int search(int[] base, int k) throws HoareException{
        int i = 0, j = base.length - 1, partitionResult;

        if (k > base.length) {
            throw new HoareException("Searched array has fewer elements");
        }

        while (i != j) {
            partitionResult = partition(base, i, j);
            partitionResult = partitionResult - i + 1;
            if (partitionResult >= k) j = i + partitionResult - 1;
            if (partitionResult < k) {
                k -= partitionResult;
                i += partitionResult;
            }
        }

        return base[i];
    }

    private int partition(int base[], int left, int right) {
        int first = base[left], tmp;
        while (left < right) {
            while ((left < right) && (base[right] >= first)) {
                right--;
            }

            while ((left < right) && (base[left] < first)) {
                left++;
            }

            if (left < right) {
                tmp = base[left];
                base[left] = base[right];
                base[right] = tmp;
            }
        }
        return left;
    }
}

package sorcer.ssu3.provider;

public class FirstPrimeImpl implements FirstPrime {

    public int search(int search) throws FirstPrimeException{

        if (search < 0) {
            return 0;
        }

        if (search == 0){
            return 1;
        }

        int n = search + search;
        boolean[] numbersTable = new boolean[n + 1];
        for (int i = 2; i * i <= n; i++) {
            if (numbersTable[i])
                continue;
            for (int j = 2 * i; j <= n; j += i)
                numbersTable[j] = true;

        }
        for (int i = 2; i <= n; i++)
            if (i > search && !numbersTable[i]) {
                return i;
            }

        throw new FirstPrimeException("Bug in the implementation, erroneous assumption");
    }
}

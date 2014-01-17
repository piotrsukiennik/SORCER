package sorcer.ssu3.provider.ui.mvc;

import java.util.Observable;

public class FirstPrimeModel extends Observable {

    private int k;
	private int result;

    final static String SEARCH = "Search";
    final static String INPUT = "Input";
    final static String RESULT = "Result";

    public FirstPrimeModel() {
    }


    public int getK() {
        return k;
    }

    public void setK( int k ) {
        this.k = k;
        setChanged();
        notifyObservers(INPUT);
    }


    public int getResult() {
		return result;
	}


	public void setResult(int result) {
		this.result = result;
		setChanged();
		notifyObservers(RESULT);
	}

}

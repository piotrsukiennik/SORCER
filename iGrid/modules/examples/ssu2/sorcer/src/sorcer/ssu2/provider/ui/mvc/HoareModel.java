package sorcer.ssu2.provider.ui.mvc;

import java.util.Observable;

public class HoareModel extends Observable {

	private int[] input;
    private int k;
	private int result;

    final static String SEARCH = "Search";
    final static String INPUT = "Input";
    final static String RESULT = "Result";

    public HoareModel() {
    }
	public HoareModel( int[] input ) {
		this.input = input;
	}

	public void setInput(int[] input) {
		this.input = input;
		setChanged();
		notifyObservers(INPUT);
	}

    public int getK() {
        return k;
    }

    public void setK( int k ) {
        this.k = k;
        setChanged();
        notifyObservers(INPUT);
    }

    public int[] getInput() {
        return input;
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

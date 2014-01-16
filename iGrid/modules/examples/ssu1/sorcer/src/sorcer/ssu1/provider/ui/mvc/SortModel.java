package sorcer.ssu1.provider.ui.mvc;

import sorcer.account.provider.Money;

import java.util.Observable;

public class SortModel extends Observable {

	private int[] input;

	private int[] result;

    final static String SORT = "Sort";
    final static String INPUT = "Input";
    final static String RESULT = "Result";

    public SortModel(  ) {
    }
	public SortModel( int[] input ) {
		this.input = input;
	}

	public void setInput(int[] input) {
		this.input = input;
		setChanged();
		notifyObservers(RESULT);
	}

    public int[] getInput() {
        return input;
    }

    public int[] getResult() {
		return result;
	}


	public void setResult(int[] result) {
		this.result = result;
		setChanged();
		notifyObservers(RESULT);
	}

}

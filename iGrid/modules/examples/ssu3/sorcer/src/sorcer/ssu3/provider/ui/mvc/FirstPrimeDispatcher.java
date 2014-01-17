package sorcer.ssu3.provider.ui.mvc;

import sorcer.ssu3.provider.FirstPrime;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class FirstPrimeDispatcher implements ActionListener {

	private final static Logger logger = Logger
			.getLogger("sorcer.provider.ssu3.ui.mvc");

	private FirstPrimeModel model;

	private FirstPrimeView view;

	private FirstPrime firstPrime;

	public FirstPrimeDispatcher( FirstPrimeModel model, FirstPrimeView view, FirstPrime firstPrime ) {
		this.model = model;
		this.view = view;
		this.firstPrime = firstPrime;
	}

	private void search() {
		try {
            model.setK( view.getK() );
            model.setResult( firstPrime.search(  model.getK() ) );
		} catch (Exception exception) {
			System.out.println("Couldn't talk to account. Error was \n "
					+ exception);
			exception.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		logger.info("actionPerformed>>action: " + action);
		if ( FirstPrimeModel.INPUT.equals( action )){
        } else if ( FirstPrimeModel.RESULT.equals( action )){
            view.displayResult();
        } else if ( FirstPrimeModel.SEARCH.equals( action )){
            search();
        }

	}
}

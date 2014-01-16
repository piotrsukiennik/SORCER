package sorcer.ssu1.provider.ui.mvc;

import sorcer.account.provider.Account;
import sorcer.ssu1.provider.Sort;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.logging.Logger;

public class SortDispatcher implements ActionListener {

	private final static Logger logger = Logger
			.getLogger("sorcer.provider.ssu1.ui.mvc");

	private SortModel model;

	private SortView view;

	private Sort account;

	public SortDispatcher( SortModel model, SortView view, Sort account ) {
		this.model = model;
		this.view = view;
		this.account = account;
	}



	private void sort() {
		try {
			model.setInput( view.getInput() );
            model.setResult( account.sort( model.getInput() ) );
		} catch (Exception exception) {
			System.out.println("Couldn't talk to account. Error was \n "
					+ exception);
			exception.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		logger.info("actionPerformed>>action: " + action);
		if (SortModel.INPUT.equals( action )){

        } else if (SortModel.RESULT.equals( action )){
            view.displayResult();
        } else if (SortModel.SORT.equals( action )){
            sort();
        }

	}
}

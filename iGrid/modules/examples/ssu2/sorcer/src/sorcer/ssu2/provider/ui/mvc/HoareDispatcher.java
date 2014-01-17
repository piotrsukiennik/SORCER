package sorcer.ssu2.provider.ui.mvc;

import sorcer.ssu2.provider.Hoare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class HoareDispatcher implements ActionListener {

	private final static Logger logger = Logger
			.getLogger("sorcer.provider.ssu1.ui.mvc");

	private HoareModel model;

	private HoareView view;

	private Hoare hoare;

	public HoareDispatcher( HoareModel model, HoareView view, Hoare hoare ) {
		this.model = model;
		this.view = view;
		this.hoare = hoare;
	}

	private void search() {
		try {
			model.setInput( view.getInput() );
            model.setK( view.getK() );
            model.setResult( hoare.search( model.getInput(), model.getK() ) );
		} catch (Exception exception) {
			System.out.println("Couldn't talk to account. Error was \n "
					+ exception);
			exception.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		logger.info("actionPerformed>>action: " + action);
		if ( HoareModel.INPUT.equals( action )){

        } else if ( HoareModel.RESULT.equals( action )){
            view.displayResult();
        } else if ( HoareModel.SEARCH.equals( action )){
            search();
        }

	}
}

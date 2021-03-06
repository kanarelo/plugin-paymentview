package org.creditsms.plugins.paymentview.ui.handler.taboutgoingpayments.dialogs;

import net.frontlinesms.ui.ThinletUiEventHandler;
import net.frontlinesms.ui.UiGeneratorController;

public class SchedulePaymentAuthDialogHandler implements ThinletUiEventHandler {

	private static final String XML_SEND_PAYMENTAUTH_DIALOG = "/ui/plugins/paymentview/outgoingpayments/dialogs/schedulePaymentAuthDialog.xml";
	private Object dialog;
	private UiGeneratorController ui;

	public SchedulePaymentAuthDialogHandler(final UiGeneratorController ui) {
		this.ui = ui;
		init();
	}

	public Object getDialog() {
		return dialog;
	}

	private void init() {
		dialog = ui.loadComponentFromFile(XML_SEND_PAYMENTAUTH_DIALOG, this);
	}

	public void refresh() {
	}

	public void removeDialog(Object dialog) {
		ui.removeDialog(dialog);
	}

	public void sendPayment() {

	}

	public void showDateSelecter(Object textField) {
		ui.showDateSelecter(textField);
	}
}

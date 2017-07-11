package br.com.pilovieira.gt06.comm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.pilovieira.gt06.R;
import br.com.pilovieira.gt06.business.MessageIdentifier;
import br.com.pilovieira.gt06.log.ServerLogManager;
import br.com.pilovieira.gt06.persist.Prefs;
import br.com.pilovieira.gt06.utils.NotificationBuilder;
import br.com.pilovieira.gt06.view.MainActivity;

public class SMSReceiver extends BroadcastReceiver {

	private Prefs preferences;

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			Bundle extras = intent.getExtras();
			if (extras == null)
				return;
			
			SmsMessage sms = SmsMessage.createFromPdu((byte[]) ((Object[]) extras.get("pdus"))[0]);
			
			onReceive(context, sms.getOriginatingAddress(), sms.getMessageBody());
		} catch (Exception e) {
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
		}
	}

	public void onReceive(Context context, String sender, String message) {
		this.preferences = new Prefs(context);

		if (!isTrackerMessage(sender))
			return;

		process(context, message);
	}

	private boolean isTrackerMessage(String sender) {
		String primeNumber = preferences.getTrackerNumber();
		return !primeNumber.isEmpty() && sender.contains(primeNumber);
	}

	private void process(Context context, String message) {
		String title = new MessageIdentifier(context).identify(message);
		new ServerLogManager(context).log(title, message);

		NotificationBuilder builder = new NotificationBuilder(context);
		builder.setId(R.id.nav_info);
		builder.setTitle(context.getString(R.string.requisition_resolver_info_title));

		builder.setResult(new Intent(context, MainActivity.class));

		builder.build();
	}

}

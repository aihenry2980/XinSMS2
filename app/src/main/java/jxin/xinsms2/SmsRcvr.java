package jxin.xinsms2;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.content.Context;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by jxin on 2016-12-24.
 */

/*public class SmsRcvr extends BroadcastReceiver
{

	@Override
	public void onReceive(Context ctxt, Intent itnt)
	{
		Bundle intentextras = itnt.getExtras();

	}
}*/

public class SmsRcvr extends BroadcastReceiver
{
	final SmsManager sms = SmsManager.getDefault();






	@Override
	public void onReceive(Context context, Intent intent)
	{
		// Retrieves a map of extended data from the intent.
		final Bundle bundle = intent.getExtras();
		try
		{
			if (bundle != null)
			{
				final Object[] pdusObj = (Object[]) bundle.get("pdus");

				for (int i = 0; i < pdusObj.length; i++) {

					SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
					String phoneNumber = currentMessage.getDisplayOriginatingAddress();

					String senderNum = phoneNumber;
					String message = currentMessage.getDisplayMessageBody();

					Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);


					// Show Alert
				//	int duration = Toast.LENGTH_LONG;
				//	Toast toast = Toast.makeText(context,
				//			"senderNum: "+ senderNum + ", Xinmessage: " + message, duration);
				//	toast.show();

					//notification  2017.01.21
					NotificationCompat.Builder mBuilder =
							(NotificationCompat.Builder) new NotificationCompat.Builder(context)
									.setSmallIcon(R.drawable.ic_looks_one_black_24dp)
									.setContentTitle(senderNum)
									.addAction(R.drawable.ic_backspace_black_24dp, "Delete", null)
									.setContentText(message);

									//.setLargeIcon(R.drawable.ic_stat_name);
					// Sets an ID for the notification
					int mNotificationId = 001;
// Gets an instance of the NotificationManager service
					NotificationManager mNotifyMgr =
							(NotificationManager)  (context.getSystemService(NOTIFICATION_SERVICE));
// Builds the notification and issues it.
					mNotifyMgr.notify(mNotificationId, mBuilder.build());




				} // end for loop
			} // bundle is null

		} catch (Exception e) {
			Log.e("SmsReceiver", "Exception smsReceiver" +e);

		}
	}
}

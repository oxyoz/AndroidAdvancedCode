package oz.basscode;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.bmob.push.PushConstants;
import oz.ozlibrary.OzLog;

public class PushMessageReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {

//        OzLog.log(context, intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING));

        NotificationManager notificationmg = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new Notification();

        notification.icon = R.mipmap.ic_launcher;

        notification.tickerText = "Bmob push notification";

        notification.when = System.currentTimeMillis();

        notification.defaults |= Notification.DEFAULT_VIBRATE;

        long[] vibrate = {0,100,200,300};

        notification.vibrate = vibrate;

        notification.defaults |= Notification.DEFAULT_LIGHTS;

        notification.ledARGB = 0xff00ff00;

        notification.ledOnMS = 300;

        notification.ledOffMS = 1000;

        notification.flags |= Notification.FLAG_SHOW_LIGHTS;

        final int ID_NOTIFICATION = 1;

//        Intent intent = new Intent(context, NotifyActivity.class);
//
//        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);

        notification.setLatestEventInfo(context, "Bmob消息推送", intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING), null);

        notificationmg.notify(ID_NOTIFICATION, notification);


    }

}

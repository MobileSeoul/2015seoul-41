package com.cdk.helpme.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.cdk.helpme.R;
import com.cdk.helpme.activity.SosActivity;

public class HelpmeWidget extends AppWidgetProvider {
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		for (int i = 0; i < appWidgetIds.length; i++) {
			int widgetId = appWidgetIds[i];

			Intent intent = new Intent(context, SosActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

			RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.helpme_widget);
			remoteView.setOnClickPendingIntent(R.id.help_me_widget_text, pendingIntent);

			appWidgetManager.updateAppWidget(widgetId, remoteView);
		}
	}
}

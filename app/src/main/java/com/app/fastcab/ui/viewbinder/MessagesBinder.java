package com.app.fastcab.ui.viewbinder;

import android.app.Activity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.fastcab.R;
import com.app.fastcab.entities.DriverMsgesEnt;
import com.app.fastcab.entities.MessagesEnt;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.helpers.BasePreferenceHelper;
import com.app.fastcab.ui.viewbinders.abstracts.ViewBinder;
import com.app.fastcab.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by saeedhyder on 6/22/2017.
 */

public class MessagesBinder extends ViewBinder<DriverMsgesEnt> {

    BasePreferenceHelper preferenceHelper;

    public MessagesBinder(BasePreferenceHelper preferenceHelper) {
        super(R.layout.messages_item);
        this.preferenceHelper=preferenceHelper;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final DriverMsgesEnt entity, int position, int grpPosition, View view, Activity activity) {

        MessagesBinder.ViewHolder viewHolder = (MessagesBinder.ViewHolder) view.getTag();

        viewHolder.txtMessages.setText(entity.getTitle());

        viewHolder.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS(preferenceHelper.getDriver().getPhoneNo(),entity.getTitle());
            }
        });

    }

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txt_messages)
        AnyTextView txtMessages;
        @BindView(R.id.btn_send)
        Button btnSend;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

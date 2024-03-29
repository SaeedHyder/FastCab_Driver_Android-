package com.app.fastcabdriver.ui.viewbinder;

import android.app.Activity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.entities.DriverMsgesEnt;
import com.app.fastcabdriver.helpers.BasePreferenceHelper;
import com.app.fastcabdriver.ui.viewbinders.abstracts.ViewBinder;
import com.app.fastcabdriver.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by saeedhyder on 6/22/2017.
 */

public class MessagesBinder extends ViewBinder<DriverMsgesEnt> {

    BasePreferenceHelper preferenceHelper;
    String mobileNo;

    public MessagesBinder(BasePreferenceHelper preferenceHelper, String mobileNo) {
        super(R.layout.messages_item);
        this.preferenceHelper=preferenceHelper;
        this.mobileNo=mobileNo;
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
                sendSMS(mobileNo,entity.getTitle());
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

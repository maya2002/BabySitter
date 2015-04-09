package tw.tasker.babysitter.view.fragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tw.tasker.babysitter.Config;
import tw.tasker.babysitter.R;
import tw.tasker.babysitter.SmsReceiver;
import tw.tasker.babysitter.view.activity.SignUpListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class VerifyCodeFragment extends Fragment implements OnClickListener {

	private static SignUpListener mListener;

	public static Fragment newInstance(SignUpListener listener) {
		Fragment fragment = new VerifyCodeFragment();
		mListener = listener;
		return fragment;
	}

	public Button mConfirm;
	private TextView mChangePhone;
	public EditText mVerifyCode;
	private SmsReceiver mSmsreceiver;
	private IntentFilter mSmsFilter;
	private TextView mError;
	private Button mSend;
	private TextView mPhone;

	public VerifyCodeFragment() {
		// Required empty public constructor
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mSmsreceiver = new SmsReceiver();
		mSmsFilter = new IntentFilter();
		mSmsFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
		mSmsFilter.addAction("BabyCare.SMS_RECEIVED.TEST");
	}
	
	@Override
	public void onResume() {
		super.onResume();
        getActivity().registerReceiver(mSmsreceiver, mSmsFilter); 
	}
	
	@Override
	public void onPause() {
		super.onPause();
        getActivity().registerReceiver(mSmsreceiver, mSmsFilter); 
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_code_verify, container, false);
		mConfirm = (Button)rootView.findViewById(R.id.confirm);
		mConfirm.setOnClickListener(this);
		mConfirm.setVisibility(View.INVISIBLE);
		
		mChangePhone = (TextView) rootView.findViewById(R.id.change_phone);
		mChangePhone.setOnClickListener(this);
		SpannableString content = new SpannableString("不是這隻號碼?");
		content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		mChangePhone.setText(content);
		
		mVerifyCode = (EditText) rootView.findViewById(R.id.verify_code);
		mSmsreceiver.setFragment(this);
		
		mError = (TextView) rootView.findViewById(R.id.error);
		mError.setVisibility(View.INVISIBLE);
		
		mSend = (Button) rootView.findViewById(R.id.send);
		mSend.setOnClickListener(this);
		
		mPhone = (TextView) rootView.findViewById(R.id.phone);
		String phone = Config.sitterInfo.getTel();
        Pattern p = Pattern.compile("\\d{10}");
        Matcher m = p.matcher(phone);
        if(m.find()) {
        	mPhone.setText(m.group());
        }
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}

	@Override
	public void onClick(View v) {
		
		int id = v.getId();
		switch (id) {
		case R.id.confirm:
			mListener.onSwitchToNextFragment(0);
			
			break;

		case R.id.change_phone:
			mListener.onSwitchToNextFragment(1);
			
			break;
			
		case R.id.send:
			
			sendVerifyCodeToSms();
			sendVerifyCodeToServer();
			
			break;
			
		default:
			break;
		}
		
	}

	private void sendVerifyCodeToSms() {
		String OLA_BROATCAST_STRING = "android.provider.Telephony.SMS_RECEIVED";
		Intent i = new Intent(OLA_BROATCAST_STRING);
		//i.putExtra("STR_PARAM1", "廣播訊息");
		getActivity().sendBroadcast(i);
	}

	private void sendVerifyCodeToServer() {
		
	}



}

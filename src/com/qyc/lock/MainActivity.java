package com.qyc.lock;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

	private DevicePolicyManager mDevicePolicyManager;
	private ComponentName mComponentName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); // ��ȡ�豸������
		mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		mComponentName = new ComponentName(this, LockReceiver.class); // �жϸ�����Ƿ���ϵͳ����Ա��Ȩ��
		if (mDevicePolicyManager.isAdminActive(mComponentName)) {
			mDevicePolicyManager.lockNow(); // ����
		} else {
			activeManager(); // ����Ȩ��
		}
		// ��������
		int pid = android.os.Process.myPid();
		android.os.Process.killProcess(pid);
	}

	/** �����豸��������ȡȨ�� */
	protected void activeManager() {
		Toast.makeText(this, "ע�⣺\n���������޷�ж�ظ�Ӧ�á�\n���Ҫж�أ����ȵ�����-��ȫ-�豸��������ȡ������.", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Lock Device");
		startActivity(intent);
	}
}
